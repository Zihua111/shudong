package com.jumbox.demo.service;

import com.jumbox.demo.common.utils.PageResult;
import com.jumbox.demo.mbg.mapper.ArticleMapper;
import com.jumbox.demo.mbg.pojo.Article;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean create(Article article){
        article.setId(Sid.next());
        article.setCreateTime(new Date());
        articleMapper.insert(article);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Boolean addComment(String id,String comment){
        Article article = new Article();
        article.setId(id);
        article.setComment(comment);
        articleMapper.updateByPrimaryKeySelective(article);
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageResult getList(Integer page, Integer pageSize) {

        // 从controller中获取page和pageSize (经实验，PageHelper 只拦截下一次查询)
        PageHelper.startPage(page, pageSize);

        Example articleExample = new Example(Article.class);
        articleExample.setOrderByClause("create_time desc");
        List<Article> list = articleMapper.selectByExample(articleExample);

        PageInfo<Article> pageList = new PageInfo<>(list);

        PageResult pagedResult = new PageResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

}
