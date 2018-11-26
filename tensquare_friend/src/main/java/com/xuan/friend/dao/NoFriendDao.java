package com.xuan.friend.dao;

import com.xuan.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/26 0026
 * @Time: 9:55
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String> {

    NoFriend findByUseridAndFriendid(String userid, String friendid);
}
