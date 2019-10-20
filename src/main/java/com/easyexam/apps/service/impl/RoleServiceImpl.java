package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.RoleDao;
import com.easyexam.apps.entity.Role;
import com.easyexam.apps.entity.RoleTree;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private CodeMsg codeMsg;

    @Override
    public List<Role> findAllRole() {
        List<Role> list = roleDao.findAllRole();
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }

    @Override
    public JsonResult addRole(Role role,List<Integer>pid) {
        roleDao.addRole(role);
        Integer rid = role.getRid();
        int i = roleDao.addRolePermission(rid, pid);
        if (i <= 0){
            throw new MyException(ErrorCode.EXCEPTION_INSERT,codeMsg.getInsertException());
        }
        return new JsonResult(1,"添加成功");
    }

    @Override
    public JsonResult deleteRole(Integer rid) {
        int i1 = roleDao.deleteRole(rid);
        int i2 = roleDao.deleteRoleUser(rid);
        int i3 = roleDao.deleteUser(rid);
        if (i1 <= 0 || i2 <=0 || i3 <= 0){
            throw new MyException(ErrorCode.EXCEPTION_DELETE,codeMsg.getDeleteException());
        }
        return new JsonResult(1,"删除成功");
    }

    @Override
    public JsonResult updateRole(Role role){
        int i = roleDao.updateRole(role);
        if (i <= 0){
            throw new MyException(ErrorCode.EXCEPTION_UPDATE,codeMsg.getUpdateException());
        }
        return new JsonResult(1,"修改成功");
    }

    @Override
    public List<RoleTree> findRoleTree() {
        List<RoleTree> list = roleDao.findRoleTree();
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }
}
