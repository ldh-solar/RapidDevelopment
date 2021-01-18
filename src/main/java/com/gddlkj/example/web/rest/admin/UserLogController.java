package com.gddlkj.example.web.rest.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gddlkj.example.model.domain.UserLog;
import com.gddlkj.example.model.dto.MyPage;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.service.IUserLogService;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * <p>
 * 用户日志表 前端控制器
 * </p>
 *
 * @author blank
 * @since 2020-08-10
 */
@RestController
@Slf4j
@Api(tags = "用户日志表")
@RequestMapping("admin/userLog")
public class UserLogController extends BaseController {

    @Resource
    private IUserLogService userLogService;

    @GetMapping()
    @ApiOperation(value = "分页查询用户日志表")
    public R<IPage<UserLog>> listByPage(MyPage<UserLog> myPage, String keyWord) {
        LambdaQueryWrapper<UserLog> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotEmpty(keyWord)) {
            wrapper.like(UserLog::getLogDesc, keyWord).or()
                    .like(UserLog::getMethodName, keyWord).or()
                    .like(UserLog::getArgs, keyWord).or()
                    .like(UserLog::getLoginIp, keyWord);
        }

        return success(userLogService.page(myPage.convertToPage(), wrapper));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取用户日志表")
    public R<UserLog> selectById(@PathVariable Long id) {
        return success(userLogService.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户日志表")
    public R delete(@PathVariable Long id) {
        return success(userLogService.removeById(id));
    }

    @PutMapping()
    @ApiOperation(value = "更新一条用户日志表记录")
    public R update(@Valid @RequestBody UserLog userLog) {
        return success(userLogService.updateById(userLog));
    }

    @PostMapping()
    @ApiOperation(value = "新增一条用户日志表记录")
    public R save(@Valid @RequestBody UserLog userLog) {
        return success(userLogService.save(userLog));
    }

}

