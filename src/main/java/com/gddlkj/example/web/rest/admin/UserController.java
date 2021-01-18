package com.gddlkj.example.web.rest.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gddlkj.example.annotation.OperationLog;
import com.gddlkj.example.exception.BaseBusinessException;
import com.gddlkj.example.license.util.LicenseAuth;
import com.gddlkj.example.model.constants.OperationType;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.constants.TokenTypeEnum;
import com.gddlkj.example.model.domain.*;
import com.gddlkj.example.model.dto.*;
import com.gddlkj.example.model.mapper.UserConverter;
import com.gddlkj.example.model.vo.UserVo;
import com.gddlkj.example.model.vo.VerifyCodeVo;
import com.gddlkj.example.security.TokenAuthenticationService;
import com.gddlkj.example.service.IPermissionService;
import com.gddlkj.example.service.IRoleService;
import com.gddlkj.example.service.IUserService;
import com.gddlkj.example.util.EncryptUtil;
import com.gddlkj.example.util.MD5Util;
import com.gddlkj.example.web.rest.common.BaseController;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@RestController
@Slf4j
@Api(tags = "用户")
@RequestMapping("admin/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;
    @Resource
    private IPermissionService permissionService;
    @Resource
    private IRoleService roleService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private TokenAuthenticationService tokenAuthenticationService;
    @Resource
    private UserConverter userConverter;
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private LicenseAuth licenseAuth;

    @GetMapping()
    @ApiOperation(value = "分页查询用户")
    public R<IPage<UserVo>> listByPage(MyPage<User> myPage, String keyWord) {
        IPage<User> users = userService.page(myPage.convertToPage(), Wrappers.<User>lambdaQuery()
                .like(StringUtils.isNotEmpty(keyWord), User::getUsername, keyWord));
        Page<UserVo> userVos = new Page<>();
        BeanUtils.copyProperties(users, userVos);
        userVos.setRecords(userConverter.usersToUsersVo(users.getRecords()));
        return success(userVos);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取用户")
    public R<User> selectById(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setPassword("");
        return success(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户")
    public R delete(@PathVariable Long id) {
        return success(userService.removeById(id));
    }

    @PutMapping()
    @ApiOperation(value = "更新一条用户表记录")
    public R update(@Valid @RequestBody User user) {
        User tempUser = userService.getById(user.getId());
        // 先判断用户是否存在
        if (null == tempUser) {
            return error(ResponseCodeEnum.USER_NOT_EXITS);
        }
        tempUser.setUsername(user.getUsername());
        tempUser.setNickname(user.getNickname());
        tempUser.setPassword(MD5Util.generate(user.getPassword()));
        return success(userService.updateById(tempUser));
    }

    @PostMapping()
    @ApiOperation(value = "新增一条用户表记录")
    public R save(@Valid @RequestBody User user) {
        int count = userService.lambdaQuery().eq(User::getUsername, user.getUsername()).count();
        // 先判断帐号是否重复
        if (count != 0)
            return error(ResponseCodeEnum.USER_EXITS);
        LcInfo lcInfo = licenseAuth.auth().getData();
        int userCount = userService.lambdaQuery().count();
        if (userCount >= lcInfo.getUserCount())
            return error(ResponseCodeEnum.USER_COUNT_ERROR);
        user.setPassword(MD5Util.generate(user.getPassword()));
        return success(userService.save(user));
    }

    @ApiOperation(value = "获取用户权限数据和用户信息")
    @GetMapping("/currentUser")
    public R<UserInfoDTO> currentUser() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        User user = userService.getById(id);
        user.setPassword(null);
        // 返回用户信息和权限信息
        userInfoDTO.setUser(user);
        userInfoDTO.setPermissions(permissionService.findByUserId(id));
        return success(userInfoDTO);
    }

    @GetMapping("/{id}/roles")
    @ApiOperation(value = "根据用户ID列出角色")
    public R<List<Long>> listRoleByUser(@PathVariable Long id) {
        List<Role> roles = roleService.listRoleByUser(id);
        return success(roles.stream().map(Role::getId).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/roles")
    @ApiOperation(value = "更新用户角色")
    public R updateUserRole(@PathVariable Long id, @RequestBody List<Long> roles) {
        roleService.updateUserRole(id, roles);
        return success();
    }

    @PostMapping("/modifyPassword")
    @ApiOperation(value = "修改密码")
    public R updateUserRole(@RequestBody ResetPasswordDto resetPasswordDto) {
        User user = userService.getById(SecurityContextHolder.getContext().getAuthentication().getName());

        resetPasswordDto.setConfirmPassword(EncryptUtil.decryptAES(resetPasswordDto.getConfirmPassword()));
        resetPasswordDto.setOldPassword(EncryptUtil.decryptAES(resetPasswordDto.getOldPassword()));
        resetPasswordDto.setPassword(EncryptUtil.decryptAES(resetPasswordDto.getPassword()));

        if (!MD5Util.verify(resetPasswordDto.getOldPassword(), user.getPassword())) {
            return error(ResponseCodeEnum.INCORRECT_PASSWORD);
        }
        if (!resetPasswordDto.getConfirmPassword().equals(resetPasswordDto.getPassword())) {
            return error(ResponseCodeEnum.PASSWORD_NOT_SAME);
        }
        user.setPassword(MD5Util.generate(resetPasswordDto.getPassword()));
        return success(userService.updateById(user));
    }

    @OperationLog(operationType = OperationType.LOGIN, detail = "后台用户登陆 用户名: {{username}}")
    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public R login(@ApiParam("用户名") @RequestParam String username,
                   @ApiParam("密码") @RequestParam String password,
                   @ApiParam("验证码key") @RequestParam String key,
                   @ApiParam("验证码") @RequestParam String code) {
        // 检查验证码
        String verifyCode = (String) redisTemplate.opsForValue().get(key);
        if (verifyCode == null || !verifyCode.equals(code))
            return error(ResponseCodeEnum.VERIFY_CODE_ERROR);

        //查询当前用户
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));

        // 检查用户是否存在
        if (user == null) {
            return error(ResponseCodeEnum.USER_NOT_EXITS);
        }

        // 认证逻辑
        if (MD5Util.verify(EncryptUtil.decryptAES(password), user.getPassword())) {
            // 这里设置权限和角色
            List<Permission> authorities;
            authorities = permissionService.findByUserId(user.getId());


            List<SecurityPermission> securityPermissionList = new ArrayList<>();
            for (Permission permission : authorities) {
                SecurityPermission securityPermission = new SecurityPermission();
                securityPermission.setAuthority(permission.getAuthority());
                securityPermissionList.add(securityPermission);
            }

            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), password, securityPermissionList);
            String jwt = tokenAuthenticationService.addAuthentication(auth.getName(), (Collection<SecurityPermission>) auth.getAuthorities(), TokenTypeEnum.ADMIN);
            // 用户信息放到redis
            redisTemplate.delete("system:user:" + user.getId());
            redisTemplate.opsForValue().set("system:user:" + user.getId(), user);
            // 删除验证码
            redisTemplate.delete(key);
            return success(jwt + "|" + user.getId());

        } else {
            throw new BaseBusinessException(ResponseCodeEnum.INCORRECT_PASSWORD);
        }
    }

    @PostMapping("/verifyCode")
    public R<VerifyCodeVo> getVerifyCode() {
        ByteArrayOutputStream outputStream;
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        String key = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(key, text, 5, TimeUnit.MINUTES);
        outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(outputStream.toByteArray());
        final VerifyCodeVo verifyCodeDto = new VerifyCodeVo(key, "data:image/jpeg;base64," + base64.replaceAll("\r\n", ""));
        return success(verifyCodeDto);
    }


}

