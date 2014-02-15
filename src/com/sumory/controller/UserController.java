package com.sumory.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sumory.controller.util.ControllerHelper;
import com.sumory.orm.Page;
import com.sumory.pojo.User;
import com.sumory.proxy.UserProxy;
import com.sumory.service.UserService;

/**
 * 基本操作
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserProxy userProxy;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable Integer id,Model model){
		 model.addAttribute("user1", userService.getUser(1));
		 model.addAttribute("user2", userService.getUser(2));
		 return "/user/view";
	}
	

	@RequestMapping(value = "/list/{pageNo}", method = RequestMethod.GET)
	public String listUsers(@PathVariable Integer pageNo,Model model){
		 model.addAttribute(userService.getUsers(pageNo, 5));
		 return "/user/list";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String goNewUser(User user){
		return "/user/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newUser(User user){
		userService.newUser(user);
		return "redirect:/user/list/1";
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public void getUserJosn(@RequestParam int pageNo, HttpServletRequest request, HttpServletResponse response) throws IOException{
		Page<User> page = userService.getUsers(pageNo, 5);
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("page", page);
		ControllerHelper.sendJson(response, result.toString());
	}
	
	
	@RequestMapping(value = "/proxy", method = RequestMethod.GET)
	public void invokeProxy(HttpServletRequest request, HttpServletResponse response) throws IOException{
		ControllerHelper.sendJson(response,JSON.toJSONString(userProxy.getUsers(1)));
	}

}
