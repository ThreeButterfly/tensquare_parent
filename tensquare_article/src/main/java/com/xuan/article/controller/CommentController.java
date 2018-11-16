package com.xuan.article.controller;

import com.xuan.article.pojo.Comment;
import com.xuan.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 9:54
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result save(@RequestBody Comment comment) {
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功 ");
    }

    @GetMapping(value = "/article/{articleId}")
    public Result findByArticleId(@PathVariable String articleId) {
        return new Result(true, StatusCode.OK, "查询成功",
                commentService.findByArticleid(articleId));
    }

    @DeleteMapping(value = "/{articleId}")
    public Result deleteById(@PathVariable String articleId){
        commentService.deleteById(articleId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}

