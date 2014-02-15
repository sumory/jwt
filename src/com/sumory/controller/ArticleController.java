package com.sumory.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.sumory.controller.util.ControllerHelper;
import com.sumory.pojo.Article;
import com.sumory.service.ArticleService;

/**
 * 主要是查看   级联对象的两种获取方式
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getArticle(@PathVariable Integer id,Model model){
		 model.addAttribute(articleService.getArticle(id));
		 return "/article/view";
	}
	

	@RequestMapping(value = "/list/{pageNo}", method = RequestMethod.GET)
	public String listArticles(@PathVariable Integer pageNo,Model model){
		 model.addAttribute(articleService.getArticles(pageNo, 5));
		 return "/article/list";
	}
	
	
	@RequestMapping(value = "/list2/{pageNo}", method = RequestMethod.GET)
	public String listArticle2(@PathVariable Integer pageNo,Model model){
		Assert.isTrue(pageNo == 0, " error page no " );
		model.addAttribute("list",articleService.getArticles2(pageNo, 5));
		return "/article/list2";
	}
	
	
	@RequestMapping(value = "/list3/{pageNo}", method = RequestMethod.GET)
	public void listArticle3(@PathVariable Integer pageNo,HttpServletResponse response) throws IOException{
		List list = articleService.getArticles2(pageNo, 5);
		JSONObject result  = new JSONObject();
		result.put("success", true);
		result.put("list",list);
		ControllerHelper.sendJson(response, result.toString());
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String goNewArticle(Article article){
		return "/article/new";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newArticle(Article article){
		articleService.newArticle(article);
		return "redirect:/article/list/1";
	}
	
}
