package com.sumory.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumory.dao.ArticleDao;
import com.sumory.orm.Page;
import com.sumory.pojo.Article;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public Article getArticle(int id ){
		return articleDao.getArticle(id);
	}
	
	public Page<Article> getArticles(int pageNo,int pageSize){
		return articleDao.getArticles(pageNo, pageSize);
	}
	
	public List<Map<String,Object>> getArticles2(int pageNo,int pageSize){
		return articleDao.getArticles2(pageNo, pageSize);
	}
	
	public int newArticle(Article article){
		return articleDao.save(article);
	}
	
	
}
