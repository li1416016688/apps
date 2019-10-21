package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.UserDao;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CodeMsg codeMsg;
    @Override
    public List<User> findAll(Integer page, Integer limit, String name) {
        PageHelper.startPage(page,limit);
        List<User> list = userDao.findAllUser(name);
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }

    @Override
    public JsonResult deleteOneUser(Integer uid) {
        if (uid == 1){
            return new JsonResult(0,"最高权限不可删除");
        }
        int i = userDao.deleteUser(uid);
        int i1 = userDao.deleteUserRole(uid);
        if (i <= 0 || i1 <= 0){
            throw new MyException(ErrorCode.EXCEPTION_DELETE,codeMsg.getDeleteException());
        }
        return new JsonResult(1,"删除成功");
    }

    @Override
    public JsonResult updateUser(User user) {
        int i = userDao.updateUser(user);
        if (i <= 0){
            throw new MyException(ErrorCode.EXCEPTION_UPDATE,codeMsg.getUpdateException());
        }
        return new JsonResult(1,"修改成功");
    }

    @Override
    public User findUser(Integer uid) {
        User user = userDao.findUser(uid);
        if (user == null || "".equals(user)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return user;
    }

    @Override
    public List<Integer> findUserPermission(Integer uid) {
        List<Integer> list = userDao.findUserPermission(uid);
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }

    @Override
    public List<Integer> findUserRole(Integer uid) {
        List<Integer> list = userDao.findUserRole(uid);
        if (list == null || "".equals(list)){
            throw new MyException(ErrorCode.EXCEPTION_NOPOINT,codeMsg.getNoPointException());
        }
        return list;
    }


}
