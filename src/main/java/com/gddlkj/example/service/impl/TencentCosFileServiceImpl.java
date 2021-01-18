package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.exception.BaseBusinessException;
import com.gddlkj.example.mapper.FileMapper;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.File;
import com.gddlkj.example.service.IFileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
//@Service
@Slf4j
@Transactional
public class TencentCosFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Value("${cos.secretId}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    @Value("${cos.bucketName}")
    private String bucketName;


    private COSClient cosClient;

    @PostConstruct
    private void init() {
        String secretId = this.secretId;
        String secretKey = this.secretKey;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-chengdu");
        ClientConfig clientConfig = new ClientConfig(region);
        this.cosClient = new COSClient(cred, clientConfig);
    }


    @Override
    public File upload(MultipartFile file) {
        if (null == file)
            throw new BaseBusinessException(ResponseCodeEnum.FILE_IS_NULL);
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 指定要上传到 COS 上对象键
        String key = "blank" + UUID.randomUUID() + suffix;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());//文件的大小
        objectMetadata.setContentType(file.getContentType());//文件的类型
        File tempFile = new File();
        tempFile.setFilePath(key);
        tempFile.setFilename(file.getOriginalFilename());
        tempFile.setSize(file.getSize());
        try {
            cosClient.putObject(bucketName, key, file.getInputStream(), objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        baseMapper.insert(tempFile);
        tempFile.setUrl("/admin/file/download/" + tempFile.getId());
        baseMapper.updateById(tempFile);
        return tempFile;
    }

    @Override
    public InputStream download(String path) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, path);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        return cosObject.getObjectContent();
    }
}
