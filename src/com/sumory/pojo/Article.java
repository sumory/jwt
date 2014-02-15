package com.sumory.pojo;

import com.sumory.orm.annotation.Ignore;
import com.sumory.orm.annotation.Table;

@Table(name="x_article")
public class Article {
	
    private Integer id;

    private String title;

    private String content;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    
    //============================非数据库字段=======================================
    @Ignore
    private User user;

    @Ignore
	private String userName;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    
    
    
}