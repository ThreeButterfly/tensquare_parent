package com.xuan.search.controller;

import com.xuan.search.pojo.Article;
import com.xuan.search.service.ArticleService;
import entity.Result;
import entity.StatusCode;
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
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "操作成功");
    }
}
