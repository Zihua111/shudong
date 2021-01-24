package com.jumbox.demo.service;

import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.pojo.Article;

public interface ArticleService {

    public Boolean create(Article article);

    public Boolean addComment(String id,String comment);

    public PageResult getList(Integer page, Integer pageSize);

}
