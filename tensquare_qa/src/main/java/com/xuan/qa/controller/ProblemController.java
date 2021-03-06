package com.xuan.qa.controller;

import com.xuan.qa.client.BaseClient;
import com.xuan.qa.pojo.Problem;
import com.xuan.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
@RefreshScope
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private BaseClient baseClient;

    @GetMapping("/label/{labelId}")
    public Result findByLabelId(@PathVariable("labelId") String labelId) {
        Result result = baseClient.findById(labelId);
        return result;
    }


    /**
     * 最新回复
     *
     * @param labelId 标签id
     * @param page    当前页面
     * @param size    当前页数量
     * @return
     */
    @GetMapping("/newlist/{labelId}/{page}/{size}")
    public Result newlist(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.newlist(labelId, page, size);

        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 最热回复
     *
     * @param labelId 标签id
     * @param page    当前页面
     * @param size    当前页数量
     * @return
     */
    @GetMapping("/hotlist/{labelId}/{page}/{size}")
    public Result hotlist(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.hotlist(labelId, page, size);

        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 等待回复
     *
     * @param labelId 标签id
     * @param page    当前页面
     * @param size    当前页数量
     * @return
     */
    @GetMapping("/waitlist/{labelId}/{page}/{size}")
    public Result waitlist(@PathVariable String labelId, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageData = problemService.waitlist(labelId, page, size);

        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {

        String token = (String) request.getAttribute("claims_user");
        if (token == null || "".equals(token)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }

        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
