package com.gddlkj.example.web.rest.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.domain.Role;
import com.gddlkj.example.model.dto.MyPage;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.service.IPermissionService;
import com.gddlkj.example.service.IRoleService;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@RestController
@Slf4j
@Api(tags = "角色表控制器")
@RequestMapping("admin/role")
public class RoleController extends BaseController {

  @Resource
  private IRoleService roleService;
  @Resource
  private IPermissionService permissionService;

  @GetMapping()
  @ApiOperation(value="分页查询角色")
  public R<IPage<Role>> listByPage(MyPage<Role> myPage, String keyWord){
        return success(roleService.page(myPage.convertToPage(), Wrappers.<Role>lambdaQuery()
                .like(StringUtils.isNotEmpty(keyWord),Role::getName,keyWord)));
  }

  @GetMapping("/{id}")
  @ApiOperation(value="根据ID获取角色")
  public R<Role> selectById(@PathVariable Long id){
        return success(roleService.getById(id));
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value="根据ID删除角色")
  public R delete(@PathVariable Long id){
        return success(roleService.removeById(id));
  }

  @PutMapping()
  @ApiOperation(value="更新一条角色记录")
  public R update(@Valid @RequestBody Role role){
        return success(roleService.updateById(role));
  }

  @PostMapping()
  @ApiOperation(value="新增一条角色记录")
  public R save(@Valid @RequestBody  Role role){
        return success(roleService.save(role));
  }

  @GetMapping("/{id}/permission")
  @ApiOperation(value="查询角色权限")
  public R findByRoleId(@PathVariable Long id){
    return success(permissionService.findByRoleId(id).stream().map(Permission::getId).collect(Collectors.toList()));
  }

  @PostMapping("/{id}/permission")
  @ApiOperation(value="设置角色权限")
  public R updateRolePermission(@PathVariable Long id, @RequestBody List<Permission> permissionList){
    permissionService.updateRolePermission(id, permissionList);
    return success();
  }

}

