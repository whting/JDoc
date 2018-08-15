package com.wht.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wht.hello.bean.User;
import com.wht.hello.common.MessageResp;

/**
 * 用户模块
 * 
 * @author haoting.wang
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用于添加用户功能
     * 
     * @title 新增用户
     * @param username 用户名 String 必填
     * @param password 密码 String 必填
     * @respBody {"code":"100000","data":"","message":"新增成功"}
     */
    @RequestMapping("/add")
    @ResponseBody
    public MessageResp<String> add() {
        MessageResp<String> resp = new MessageResp<String>();
        resp.setData("");
        resp.setCode("100000");
        resp.setMessage("新增成功");
        return resp;
    }

    /**
     * 用于删除用户功能
     * 
     * @title 删除用户
     * @param id 用户id Intger 必填
     * @respBody {"code":"100000","data":"","message":"删除成功"}
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public MessageResp<String> delete() {
        MessageResp<String> resp = new MessageResp<String>();
        resp.setData("");
        resp.setCode("100000");
        resp.setMessage("删除成功");
        return resp;
    }

    /**
     * 通过用户id查询用户功能
     *
     * @title 查询ID查用户
     * @respParam username 用户名 String 必填
     * @respParam password 密码 String 必填
     * @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
     */
    @RequestMapping("/getUserById")
    public MessageResp<User> getUserById() {
        MessageResp<User> resp = new MessageResp<User>();
        User user = new User();
        user.setUsername("13811111111");
        user.setPassword("123456");
        resp.setData(user);
        resp.setCode("100000");
        resp.setMessage("获取成功");
        return resp;
    }

    /**
     * 通过用户id查询用户功能
     * 
     * @title 查询ID查用户22
     * @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
     * @respParamBean MessageResp
     * @respSubParamBean User
     * @see com.wht.hello.common.MessageResp
     * @see com.wht.hello.bean.User
     */
    @RequestMapping("/getUserById")
    public MessageResp<User> getUserById(String userId) {
        MessageResp<User> resp = new MessageResp<User>();
        User user = new User();
        user.setUsername("1381333311");
        user.setPassword("123456");
        resp.setData(user);
        resp.setCode("100000");
        resp.setMessage("获取成功");
        return resp;
    }

    /**
     * 通过用户id查询用户功能. 测试paramBean
     *
     * @title 查询ID查用户
     * @paramBean User
     * @see com.wht.hello.bean.User
     * @respParam username 用户名 String 必填
     * @respParam password 密码 String 必填
     * @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
     */
    @RequestMapping("/getUserById")
    public MessageResp<User> getUserById(User user) {
        MessageResp<User> resp = new MessageResp<User>();
        resp.setData(user);
        resp.setCode("100000");
        resp.setMessage("获取成功");
        return resp;
    }

    /**
     * 通过用户id查询用户功能. 测试respSubParamBean
     *
     * @title 查询ID查用户
     * @param userId 用户id
     * @respParamBean MessageResp
     * @respSubParamBean User
     * @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
     * @see com.wht.hello.bean.User
     * @see com.wht.hello.common.MessageResp
     */
    @RequestMapping("/getUserById")
    public MessageResp<User> getUser(String userId) {
        MessageResp<User> resp = new MessageResp<User>();
        resp.setData(new User());
        resp.setCode("100000");
        resp.setMessage("获取成功");
        return resp;
    }
}
