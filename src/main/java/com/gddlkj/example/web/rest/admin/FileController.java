package com.gddlkj.example.web.rest.admin;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.File;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.service.IFileService;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
@RestController
@Slf4j
@Api(tags = "文件")
@RequestMapping("admin/file")
public class FileController extends BaseController {

    @Resource
    private IFileService fileService;

    // 允许上传的类型
    private final Set<String> suffixSet = new HashSet<String>() {{
        add("jpg");
        add("gif");
        add("png");
        add("jpeg");
        add("xls");
        add("xlsx");
        add("doc");
        add("docx");
        add("pdf");
    }};


    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    public R<File> upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1).trim().toLowerCase() : "";
        if (!suffixSet.contains(suffix))
            return error(ResponseCodeEnum.FILE_TYPE_ERROR);
        return success(fileService.upload(file));
    }


    @ApiOperation(value = "下载文件")
    @GetMapping(value = "download/{id}")
    public void download(@PathVariable String id, HttpServletResponse res) throws Exception {
        File file = fileService.getOne(Wrappers.<File>lambdaQuery().eq(File::getId, id));
        InputStream inputStream;
        if (null == file || (inputStream = fileService.download(file.getFilePath())) == null) { // 判断文件是否存在
            res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("404.jpg", "UTF-8"));
            IOUtils.copy(new FileInputStream(new java.io.File(System.getProperty("user.dir") + "/src/main/resources/404.jpg")), res.getOutputStream());
            return;
        }
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getFilename(), "UTF-8"));
        String suffix = file.getFilename().substring(file.getFilename().lastIndexOf(".") + 1);
        // 如果是图片进行压缩
        if (suffix.toLowerCase().equals("jpg") || suffix.toLowerCase().equals("png") || suffix.toLowerCase().equals("jpeg")) {
            res.setContentType("image/jpeg");
            Thumbnails.of(inputStream)
                    .scale(1f)
                    .outputQuality(0.85)
                    .outputFormat("jpeg")
                    .toOutputStream(res.getOutputStream());
        } else
            IOUtils.copy(inputStream, res.getOutputStream());
    }
}

