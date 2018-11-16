package com.xuan.search.service;

import com.xuan.search.dao.ArticleDao;
import com.xuan.search.pojo.Article;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.annotation.Resource;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 14:59
 */
@Service
public class ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Resource
    private IdWorker idWorker;

    /**
     * <p>Description: 新增文章(索引库) </p>
     * @author Yat-Xuan
     * @params [article]
     * @return void
     * @Date: 2018/11/16 0016 15:01
     * @Modified By:
    */
    public void save(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }
}
