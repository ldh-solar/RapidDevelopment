package com.gddlkj.example.web.rest.admin;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.dto.R;
import com.gddlkj.example.model.vo.PermissionTreeVo;
import com.gddlkj.example.service.IPermissionService;
import com.gddlkj.example.web.rest.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@RestController
@Slf4j
@Api(tags = "权限")
@RequestMapping("admin/permission")
public class PermissionController extends BaseController {

  @Resource
  private IPermissionService permissionService;

  @GetMapping("/{id}")
  @ApiOperation(value="根据ID获取权限表")
  public R<Permission> selectById(@PathVariable Long id){
        return success(permissionService.getById(id));
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value="根据ID删除权限表")
  public R delete(@PathVariable Long id){
        Permission permission = permissionService.getById(id);
        if(permission==null)
          return error(ResponseCodeEnum.NOT_FOUND);
        if(permissionService.count(Wrappers.<Permission>lambdaQuery().eq(Permission::getPid,id))!=0)
          return error(ResponseCodeEnum.EXIST_CHILDREN);
        return success(permissionService.removeById(id));
  }

  @PutMapping()
  @ApiOperation(value="更新一条权限表记录")
  public R update(@RequestBody Permission permission){
        return success(permissionService.updateById(permission));
  }

  @PostMapping()
  @ApiOperation(value="新增一条权限表记录")
  public R save(@RequestBody Permission permission){
        return success(permissionService.save(permission));
  }

  @GetMapping("/tree")
  @ApiOperation(value="查询权限树")
  public R<List<PermissionTreeVo>> listTree(){
    return success(permissionService.listTree());
  }

}

