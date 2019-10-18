package com.easyexam.apps.entity;

import lombok.Data;

import java.util.List;

@Data
public class MenuInfo {

    private Integer menuId;
    private String menuName;
    private String url;

    //子菜单列表
    private List<MenuInfo> childMenus;

}
