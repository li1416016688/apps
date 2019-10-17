package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {

    private Integer pid;
    private String permName;
    private String permDesc;
    private String type;
    private String url;
    private Integer parentId;
}
