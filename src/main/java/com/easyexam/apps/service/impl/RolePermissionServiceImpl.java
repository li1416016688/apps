package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.RolePermissionDao;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public JsonResult updateRolePermission(Integer rid, List<Integer> pid) {
        int i1 = rolePermissionDao.deleteRolePermission(rid);
        int i2 = rolePermissionDao.addRolePermission(rid, pid);
        if (i1 <= 0 || i2 <= 0){
            throw new MyException(ErrorCode.EXCEPTION_UPDATE,codeMsg.getUpdateException());
        }
        return new JsonResult(1,"修改成功");
    }

    @Override
    public List<Integer> findById(Integer rid) {
        List<Integer> list = rolePermissionDao.findById(rid);
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }
}
