package com.sumory.dao;

import org.springframework.stereotype.Repository;

import com.sumory.orm.BasicDao;
import com.sumory.orm.Page;
import com.sumory.orm.wrapper.ReflectWrapper;
import com.sumory.pojo.User;


@Repository
public class UserDao extends BasicDao {
	
	public User getUser(int id ){
		final String sql  = "select * from x_user where id = ?";
		return queryForObject(sql, ReflectWrapper.USER, id);
	}
	
	
	
	public Page<User> getUsers(int pageNo,int pageSize){
		String sql = "select * from x_user order by id desc";
		return queryForPage(sql, ReflectWrapper.USER,pageNo,pageSize);
	}
	
	
	public int newUser(User user){
		String sql = "insert into x_user (name,address) values (?,?)";
		return update(sql, user.getName(),user.getAddress());
	}

}
