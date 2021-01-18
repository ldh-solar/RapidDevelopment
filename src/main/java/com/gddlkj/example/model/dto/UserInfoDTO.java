package com.gddlkj.example.model.dto;

import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO {

    private User user;

    private List<Permission> permissions;
}
