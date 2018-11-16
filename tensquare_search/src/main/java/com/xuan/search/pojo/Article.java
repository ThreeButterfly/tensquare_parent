package com.xuan.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import sun.font.TextRecord;

import java.io.Serializable;

/**
 * <p>Description: 索引库中的文章表(文章文档) </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 14:08
 */
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

    @Id
    @Field(index = false)
    private String id;
    /**
     * 标题
     */
    @Field(analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")
    private String title;
    /**
     * 内容
     */
    @Field(analyzer = "ik_max_word",
            searchAnalyzer = "ik_max_word")
    private String content;
    /**
     * 审核状态
     */
    @Field(index = false)
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
