package com.gddlkj.example.web.rest.admin;


import com.gddlkj.example.license.util.LicenseAuth;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author blank
 * @since 2019-09-05
 */
@RestController
@Slf4j
@Api(tags = "授权")
@RequestMapping("admin/license")
public class LicenseController extends BaseController {

    @Resource
    private LicenseAuth licenseAuth;


    @PostMapping("/upload")
    @ApiOperation(value = "上传license")
    public R updateRolePermission(MultipartFile file) throws IOException {
        File licenseFolder = new File("data/");
        if (!licenseFolder.exists())
            licenseFolder.mkdirs();
        File license = new File("data/license.lrc");
        IOUtils.copy(file.getInputStream(), new FileOutputStream(license));
        licenseAuth.init();
        return success();
    }

    @GetMapping
    @ApiOperation(value = "获取授权信息")
    public R getAuth() {
        return success(licenseAuth.auth());
    }

}

