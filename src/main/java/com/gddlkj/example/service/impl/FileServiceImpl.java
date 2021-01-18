package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.exception.BaseBusinessException;
import com.gddlkj.example.mapper.FileMapper;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.File;
import com.gddlkj.example.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * <p>
 * 本地文件
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
@Service
@Slf4j
@Transactional
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Override
    public File upload(MultipartFile file) {
        if (null == file)
            throw new BaseBusinessException(ResponseCodeEnum.FILE_IS_NULL);
        File tempFile = new File();
        String filePath = "data/upload/";
        java.io.File localFile = new java.io.File(filePath);
        if (!localFile.exists())
            localFile.mkdirs();
        localFile = new java.io.File(filePath + new Date().getTime() + file.getOriginalFilename());
        BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(
                    new FileOutputStream(localFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
            tempFile.setFilePath(localFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.setSize(file.getSize());
        tempFile.setFilename(file.getOriginalFilename());
        baseMapper.insert(tempFile);
        tempFile.setUrl("/admin/file/download/" + tempFile.getId());
        baseMapper.updateById(tempFile);
        return tempFile;
    }

    @Override
    public InputStream download(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
