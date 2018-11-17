package com.xuan.search.service;

import com.xuan.search.dao.ArticleDao;
import com.xuan.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.PageUtil;

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
     *
     * @return void
     * @author Yat-Xuan
     * @params [article]
     * @Date: 2018/11/16 0016 15:01
     * @Modified By:
     */
    public void save(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 查询
     * @param key
     * @param currNo
     * @param size
     * @return
     */
    public Page<Article> findByKey(String key, int currNo, int size) {

        PageUtil pageUtil = new PageUtil(currNo, size);

        Pageable pageable = PageRequest.of(pageUtil.getCurrNo() - 1, pageUtil.getSize());
        return articleDao.findByTitleOrContentLike(key, key, pageable);
    }
}
