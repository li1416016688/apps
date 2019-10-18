package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.UserRoleDao;
import com.easyexam.apps.entity.UserRole;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private CodeMsg codeMsg;



    @Override
    public JsonResult updateUserRole(List<Integer> rids,Integer uid) {
        int i1 = userRoleDao.deleteUserRole(uid);
        int i2 = userRoleDao.addUserRole(rids, uid);
        if (i1 <= 0 || i2 <= 0){
            throw new MyException(ErrorCode.EXCEPTION_UPDATE,codeMsg.getUpdateException());
        }
        return new JsonResult(1,"修改成功");
    }

    @Override
    public List<UserRole> findById(Integer uid) {   //如果解析不出来，把list放在map集合，在
        List<UserRole> list = userRoleDao.findById(uid);
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }
}
