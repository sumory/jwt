package com.sumory.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sumory.pojo.User;


@Service
public class UserProxy {

	@Autowired
	private RestTemplate template;
	
	
	public List<User> getUsers(int pageNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNo",pageNo);
		String str = template.getForObject("http://www.test.com/xiamenair2/user/getUsers?pageNo="+pageNo, String.class, params);
		JSONArray list = JSONObject.parseObject(str).getJSONObject("page").getJSONArray("list");
		List<User> users = JSON.parseArray(list.toJSONString(), User.class);
		System.out.println(users.get(0).getAddress());
		
		return users;
	}
	
}
