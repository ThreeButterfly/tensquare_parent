package com.xuan.spit.controller;

import com.xuan.spit.pojo.Spit;
import com.xuan.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/15 0015
 * @Time: 13:57
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询全部数据
     *
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param spitId ID
     * @return
     */
    @GetMapping(value = "/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 增加
     *
     * @param spit
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param spit
     */
    @PutMapping(value = "/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param spitId
     */
    @DeleteMapping(value = "/{spitId}")
    public Result deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级ID查询吐槽数据（分页）
     *
     * @param parentId 上级id
     * @param page     当前页面
     * @param size     每页数量
     * @return
     */
    @GetMapping(value = "/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentId(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 吐槽点赞
     *
     * @param spitId
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId) {

        //模拟用户id
        String userId = "123";
        //存入redis里的键，用来判断是否重复点赞
        String redisKey = "thumbup_" + spitId + "_" + userId;

        if (redisTemplate.hasKey(redisKey)) {
            return new Result(false, StatusCode.REPERROR, "不能重复点赞");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set(redisKey, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }
}
