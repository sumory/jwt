package com.sumory.orm.wrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.sumory.orm.RowWrapper;
import com.sumory.pojo.Article;
import com.sumory.pojo.User;

/**
 * 
 * @author sumory.wu
 * @date 2013-3-19 下午2:20:02
 */
@Component
public class ArticleWrapper implements RowWrapper<Article> {

    @Override
    public Article phase(ResultSet rs, HashSet<String> labels) throws SQLException {
        Article article = new Article();
        if (labels.contains("ID"))
            article.setId(rs.getInt("ID"));
        if (labels.contains("TITLE"))
        	article.setTitle(rs.getString("TITLE"));
        if (labels.contains("CONTENT"))
        	article.setContent(rs.getString("CONTENT"));
        if (labels.contains("USER_ID"))
        	article.setUserId(rs.getInt("USER_ID"));
        
        User user = new User();
        if (labels.contains("ID1"))
        	user.setId(rs.getInt("ID1"));
        if (labels.contains("NAME"))
        	user.setName(rs.getString("NAME"));
        if (labels.contains("ADDRESS"))
        	user.setAddress(rs.getString("ADDRESS"));
        
        article.setUser(user);
        
        return article;
    }

}