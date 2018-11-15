package com.xuan.spit.service;

import com.xuan.spit.dao.SpitDao;
import com.xuan.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.PageUtil;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/15 0015
 * @Time: 12:34
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据ID查询吐槽
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        Spit spit = spitDao.findById(id).get();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.inc("visits", 1);

        mongoTemplate.updateFirst(query, update, "spit");
        spit.setVisits(spit.getVisits() == null ? 1 : spit.getVisits() + 1);

        return spit;
    }

    /**
     * <p>Description: 发布吐槽或吐槽评论 </p>
     *
     * @return void
     * @author Yat-Xuan
     * @params [spit]
     * @Date: 2018/11/15 0015 16:15
     * @Modified By:
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");

        // 发布日期
        spit.setPublishtime(new Date());

        // 初始化浏览量
        spit.setVisits(0);

        // 初始化分享数
        spit.setShare(0);

        // 初始化点赞数
        spit.setThumbup(0);

        // 初始化回复数
        spit.setComment(0);

        // 初始化状态
        spit.setState("1");

        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment", 1);

            mongoTemplate.updateFirst(query, update, "spit");
        }

        spitDao.save(spit);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId, int page, int size) {
        return spitDao.findByParentid(parentId, getPageRequest(page, size));
    }

    /**
     * 分页构建
     *
     * @param page
     * @param size
     * @return
     */
    private PageRequest getPageRequest(int page, int size) {

        PageUtil pageUtil = new PageUtil(page, size);
        PageRequest pageRequest = PageRequest.of(pageUtil.getCurrNo() - 1, pageUtil.getSize());
        return pageRequest;
    }

    /**
     * 吐槽点赞
     *
     * @param spitId
     */
    public void thumbup(String spitId) {

        /*
         * 方式二：使用原生的MongoDB命令，操作一次数据来实现自增
         *  db.spit.update({"_id":"1"},{$inc:{"thumbup":NumberInt(1)}})
         */
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));

        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");

        /*
            方式一：效率差 操作两次数据库
            Spit spit = spitDao.findById(spitId).get();
            spit.setThumbup(spit.getThumbup() == null ? 1 : spit.getThumbup() + 1);
            spitDao.save(spit);
        */
    }
}
