package com.zhht.aliyun.mq.util;

import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Override
	public String getName(String name) {
		return name;
	}

}
