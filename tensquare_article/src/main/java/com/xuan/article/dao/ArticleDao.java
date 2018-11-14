package com.xuan.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xuan.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    /**
     * 审核文章
     * SQL语句中，问号后的数字，代表是方法中的第几个参数
     *
     * @param id 文章id
     */
    @Modifying
    @Query(value = "UPDATE tb_article SET state=1 where id=?1",
            nativeQuery = true)
    void updateState(String id);

    /**
     * 文章点赞
     *
     * @param id 文章id
     */
    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup=thumbup+1 where id=?",
            nativeQuery = true)
    void addThumbup(String id);
}
