package com.xuan.friend.dao;

import com.xuan.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/26 0026
 * @Time: 9:55
 */
public interface FriendDao extends JpaRepository<Friend, String> {

    Friend findByUseridAndFriendid(String userid, String friendid);

    /**
     *
     * @param isLike 是否喜欢
     * @param userid 用户id
     * @param friendid 好友id
     */
    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? where userid=? AND friendid=? ", nativeQuery = true)
    void updateIsLike(String isLike, String userid, String friendid);
}
