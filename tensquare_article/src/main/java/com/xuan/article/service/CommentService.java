package com.xuan.article.service;

import com.xuan.article.dao.CommentDao;
import com.xuan.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/16 0016
 * @Time: 9:53
 */
@Service
public class CommentService {

    @Resource
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        comment.set_id(idWorker.nextId() + "");
        commentDao.save(comment);
    }

    /**
     * 根据文章ID查询评论列表
     *
     * @param articleid
     * @return
     */
    public List<Comment> findByArticleid(String articleid) {
        return commentDao.findByArticleid(articleid);
    }

    /**
     * 删除评论
     * @param articleId
     */
    public void deleteById(String articleId) {
        commentDao.deleteById(articleId);
    }
}
