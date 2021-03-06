package com.xuan.user.controller;

import com.xuan.user.pojo.User;
import com.xuan.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private JwtUtil jwtUtil;

    /**
     * 更新好友的粉丝数和用户关注数
     * @param userId 用户id
     * @param friendId 好友id
     * @param x 粉丝数和关注数的增加量
     */
    @PutMapping(value = "/{userId}/{friendId}/{x}")
    public void updateFanscountAndFollowcount(@PathVariable("userId") String userId,
                                              @PathVariable("friendId") String friendId,
                                              @PathVariable("x") int x) {
        userService.updateFanscountAndFollowcount(userId,friendId,x);
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        user = userService.login(user);
        if (user == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }

        String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");

        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);
        map.put("roles", "user");

        return new Result(true, StatusCode.OK, "登录成功", map);
    }

    /**
     * 发送手机短信
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendSms(@PathVariable String mobile) {
        userService.sendSms(mobile);
        return new Result(true, StatusCode.OK, "发送成功");
    }

    /**
     * 用户注册
     *
     * @param code 手机验证码
     * @param user 用户数据
     * @return
     */
    @PostMapping("/register/{code}")
    public Result regist(@PathVariable String code, @RequestBody User user) {
        //获取redis缓存中的手机验证码
        String checkCodeRedis = (String) redisTemplate.opsForValue().get("checkCode_" + user.getMobile());

        if (checkCodeRedis.isEmpty()) {
            return new Result(false, StatusCode.VERIFICATION, "验证码已过期");
        }

        if (!checkCodeRedis.equals(code)) {
            return new Result(false, StatusCode.VERIFICATION, "验证码错误");
        }

        userService.add(user);
        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
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
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
