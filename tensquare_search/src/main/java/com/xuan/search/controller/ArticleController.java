package com.xuan.search.controller;

import com.xuan.search.pojo.Article;
import com.xuan.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 15:02
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "操作成功");
    }

    @GetMapping(value = "/{key}/{currNo}/{size}")
    public Result findByKey(@PathVariable String key, @PathVariable int currNo, @PathVariable int size) {
        Page<Article> pageData = articleService.findByKey(key, currNo, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Article>(pageData.getTotalElements(), pageData.getContent()));
    }
}
