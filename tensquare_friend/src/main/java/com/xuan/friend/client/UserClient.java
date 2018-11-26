package com.xuan.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * <p>Description: 调用用户微服务 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/26 0026
 * @Time: 15:10
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新好友的粉丝数和用户关注数
     * @param userId 用户id
     * @param friendId 好友id
     * @param x 粉丝数和关注数的增加量
     */
    @PutMapping(value = "/user/{userId}/{friendId}/{x}")
    public void updateFanscountAndFollowcount(@PathVariable("userId") String userId,
                                              @PathVariable("friendId") String friendId,
                                              @PathVariable("x") int x);
}
