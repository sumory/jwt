package com.sumory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sumory.dao.UserDao;
import com.sumory.orm.Page;
import com.sumory.pojo.User;


@Service
public class UserService {

	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(int id){
		return userDao.getUser(id);
	}
	
	
	/**
	 * 获取用户
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<User> getUsers(int pageNo,int pageSize){
		return userDao.getUsers(pageNo, pageSize);
	}
	
	/**
	 * 生成用户
	 * @param user
	 * @return
	 */
	@Transactional
	public int newUser(User user){
		Assert.isTrue(user.getName() != null, " name is null");
		return userDao.save(user);
	}

	
}
