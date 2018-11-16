package com.xuan.search.dao;

import com.xuan.search.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 14:59
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {
}
