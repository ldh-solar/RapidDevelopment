package com.gddlkj.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gddlkj.example.model.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
public interface IFileService extends IService<File> {

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 返回文件对象
     */
    File upload(MultipartFile file);

    /**
     * 文件下载
     *
     * @param path 路径
     */
    InputStream download(String path);

}
