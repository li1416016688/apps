package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.PermissionDao;
import com.easyexam.apps.entity.Permission;
import com.easyexam.apps.entity.PermissionTree;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.PermissionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private CodeMsg codeMsg;
    @Override
    public List<Permission> findAll(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Permission> list = permissionDao.findAllPermission();
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }

    @Override
    public JsonResult addPermission(Permission permission) {
        int i = permissionDao.addPermission(permission);
        if (i <= 0){
            throw new MyException(ErrorCode.EXCEPTION_INSERT,codeMsg.getInsertException());
        }
        return new JsonResult(1,"添加成功");
    }

    @Override
    public JsonResult deleteOnePermission(Integer pid) {
        int i1 = permissionDao.deletePermission(pid);
        int i2 = permissionDao.deletePermissionRole(pid);
        if (i1 <= 0 || i2 <=0){
            throw new MyException(ErrorCode.EXCEPTION_DELETE,codeMsg.getDeleteException());
        }
        return new JsonResult(1,"删除成功");
    }

    @Override
    public JsonResult updatePermission(Permission permission) {
        int i = permissionDao.updatePermission(permission);
        if (i <= 0){
            throw new MyException(ErrorCode.EXCEPTION_UPDATE,codeMsg.getUpdateException());
        }
        return new JsonResult(1,"修改成功");
    }

    @Override
    public List<PermissionTree> findZtr() {
        List<PermissionTree> list = permissionDao.findZtrPa();
        for (PermissionTree p : list){
            List<PermissionTree> children = permissionDao.findZtrCh(p.getPid());
            p.setChildren(children);
        }
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }
}
