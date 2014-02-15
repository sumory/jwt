package com.sumory.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sumory.orm.BasicDao;
import com.sumory.orm.Page;
import com.sumory.orm.wrapper.ArticleWrapper;
import com.sumory.orm.wrapper.ReflectWrapper;
import com.sumory.pojo.Article;


@Repository
public class ArticleDao extends BasicDao {
	
	@Autowired
	private ArticleWrapper wrapper;
	
	public Article getArticle(int id ){
		final String sql  = "select a.*,u.* from x_article a left join x_user u on a.user_id = u.id  where a.id = ?";
		return queryForObject(sql, wrapper, id);
	}
	
	
	public Page<Article> getArticles(int pageNo,int pageSize){
		String sql = "select a.*,u.name as user_name from x_article a left join x_user u on a.user_id = u.id order by a.id desc ";
		return queryForPage(sql, ReflectWrapper.ARTICLE,pageNo,pageSize);
	}
	
	public List<Map<String,Object>> getArticles2(int pageNo,int pageSize){
		String sql = "select a.*,u.name as user_name from x_article a left join x_user u on a.user_id = u.id order by a.id desc ";
		return queryForList(getPageSQL(sql, (pageNo-1)*pageSize, pageSize));
	}
	
	
}
