package com.sumory.orm.wrapper;

import com.sumory.orm.BeanRowWrapper;
import com.sumory.pojo.Article;
import com.sumory.pojo.User;

public class ReflectWrapper {

    public final static BeanRowWrapper<User> USER = new BeanRowWrapper<User>(User.class);
    
    public final static BeanRowWrapper<Article> ARTICLE = new BeanRowWrapper<Article>(Article.class);
    
}
