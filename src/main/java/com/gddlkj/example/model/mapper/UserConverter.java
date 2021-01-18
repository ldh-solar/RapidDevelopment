package com.gddlkj.example.model.mapper;

import com.gddlkj.example.model.domain.User;
import com.gddlkj.example.model.vo.UserVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserConverter  {

    UserVo userToUserVo(User user);

    List<UserVo> usersToUsersVo(List<User> po);
}
