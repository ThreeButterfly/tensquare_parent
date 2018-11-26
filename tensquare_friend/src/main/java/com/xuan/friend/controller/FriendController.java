package com.xuan.friend.controller;

import com.xuan.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/26 0026
 * @Time: 9:16
 */
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private FriendService friendService;

    /**
     * 删除好友
     *
     * @param friendId 好友id
     * @return
     */
    @DeleteMapping(value = "/{friendId}")
    public Result deleteFriend(@PathVariable("friendId") String friendId) {

        //验证是否登录，并且拿到当前用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false, StatusCode.ERROR, "请登录");
        }

        //用户id
        String userId = claims.getId();

        friendService.deleteFriend(userId, friendId);

        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 添加好友或者添加非好友
     *
     * @param friendId 好友id
     * @param type     用户判断是喜欢还是不喜欢
     * @return
     */
    @PutMapping(value = "/like/{friendId}/{type}")
    public Result addFriend(@PathVariable("friendId") String friendId,
                            @PathVariable("type") String type) {

        //验证是否登录，并且拿到当前用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false, StatusCode.ERROR, "请登录");
        }

        //用户id
        String userId = claims.getId();

        //判断是添加好友还是非好友
        if (type != null) {
            if (type.equals("1")) {
                //添加好友
                int flag = friendService.addFriend(userId, friendId);

                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                } else if (flag == 2) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                } else {
                    return new Result(false, StatusCode.ERROR, "添加失败");
                }

            } else if (type.equals("2")) {
                //添加非好友
                int flag = friendService.addNoFriend(userId, friendId);

                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                } else if (flag == 2) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
                } else {
                    return new Result(false, StatusCode.ERROR, "添加失败");
                }

            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }

    }
}
