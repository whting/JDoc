package com.wht.hello.service;

import com.wht.hello.bean.User;
import com.wht.hello.common.MessageResp;

import java.util.List;

/**
 * 用户模块
 *
 * @author haoting.wang
 * @Date
 */
public interface UserService {

	/**
	 *
	 * 查询用户1
	 *
	 * @title 查询用户
	 * @paramBean User
	 * @respParamBean MessageResp
	 * @respSubParamBean String
	 * @see com.wht.hello.common.MessageResp
	 * @see com.wht.hello.bean.User
	 * @return 用户
	 */
	public MessageResp<String> getUserList(User user);
}
