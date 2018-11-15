package com.xuan.spit.controller;

import com.xuan.spit.pojo.Spit;
import com.xuan.spit.service.SpitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    /**
     * 查询全部数据
     * @return
     */
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    /**
     * 根据ID查询
     * @param spitId ID
     * @return
     */
    @GetMapping(value="/{spitId}")
    public Result findById(@PathVariable String spitId){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(spitId));
    }
    /**
     * 增加
     * @param spit
     */
    @PostMapping
    public Result add(@RequestBody Spit spit ){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param spit
     */
    @PutMapping(value="/{spitId}")
    public Result update(@RequestBody Spit spit,@PathVariable String spitId )
    {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param spitId
     */
    @DeleteMapping(value="/{spitId}")
    public Result deleteById(@PathVariable String spitId ){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
