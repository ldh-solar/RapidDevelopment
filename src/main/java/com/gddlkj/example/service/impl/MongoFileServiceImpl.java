package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.exception.BaseBusinessException;
import com.gddlkj.example.mapper.FileMapper;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.File;
import com.gddlkj.example.service.IFileService;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * MongoDB 文件服务器
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
//@Service
@Slf4j
@Transactional
public class MongoFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Resource
    GridFsTemplate gridFsTemplate;
    @Resource
    private MongoDbFactory mongoDbFactory;

    @Bean
    public GridFSBucket getGridFSBuckets() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }

    @Resource
    private GridFSBucket gridFSBucket;

    @Override
    public File upload(MultipartFile file) {
        if (null == file)
            throw new BaseBusinessException(ResponseCodeEnum.FILE_IS_NULL);
        File tempFile = null;
        ObjectId id;
        try {
            // 先保存到mongodb
            id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename());
            tempFile = new File();
            tempFile.setFilePath(id.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert tempFile != null;
        tempFile.setSize(file.getSize());
        tempFile.setFilename(file.getOriginalFilename());
        baseMapper.insert(tempFile);
        tempFile.setUrl("/admin/file/download/" + tempFile.getId());
        baseMapper.updateById(tempFile);
        return tempFile;
    }

    @Override
    public InputStream download(String path) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(path)));
        if (gridFSFile == null)
            return null;
        return gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
    }
}
