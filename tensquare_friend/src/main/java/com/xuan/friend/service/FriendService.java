package com.xuan.friend.service;

import com.xuan.friend.client.UserClient;
import com.xuan.friend.dao.FriendDao;
import com.xuan.friend.dao.NoFriendDao;
import com.xuan.friend.pojo.Friend;
import com.xuan.friend.pojo.NoFriend;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/26 0026
 * @Time: 9:33
 */
@Service("friendService")
@Transactional
public class FriendService {

    @Resource
    private FriendDao friendDao;

    @Resource
    private NoFriendDao noFriendDao;

    @Resource
    private UserClient userClient;

    /**
     * 添加好友
     *
     * @param userId   用户id
     * @param friendId 好友id
     * @return 0-添加失败 1-添加成功 2-重复添加
     */
    public int addFriend(String userId, String friendId) {

        //先判断从userId到friendId是否有数据，若有，直接返回2
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            return 2;
        }
        //直接添加好友，让好友表中从userId到friendId的type直接为0
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");

        friendDao.save(friend);
        //判断从friendId到userId是否有数据，若有，则把双方都修改为1
        friend = friendDao.findByUseridAndFriendid(friendId, userId);
        if (friend != null) {
            friendDao.updateIsLike("1", userId, friendId);
            friendDao.updateIsLike("1", friendId, userId);
        }

        //判断该用户以前是否不喜欢过该好友，若有则删除这条记录
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            noFriendDao.delete(noFriend);
        }

        userClient.updateFanscountAndFollowcount(userId, friendId, 1);

        return 1;
    }

    public int addNoFriend(String userId, String friendId) {

        //先判断是否已经是非好友
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            return 2;
        }

        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);

        noFriendDao.save(noFriend);

        //判断以前是否喜欢过该好友，若有则删除这条记录
        Friend friend = friendDao.findByUseridAndFriendid(userId, friendId);
        if (friend != null) {
            friendDao.delete(friend);

            friendDao.updateIsLike("0", friendId, userId);

            userClient.updateFanscountAndFollowcount(userId, friendId, -1);
        }

        return 1;
    }

    public void deleteFriend(String userId, String friendId) {

        //删除表中从userId到friendId的记录
        friendDao.deleteFriend(userId, friendId);

        //判断是否为双向喜欢，若有则修改0
        friendDao.updateIsLike("0", friendId, userId);

        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);

        noFriendDao.save(noFriend);

        userClient.updateFanscountAndFollowcount(userId, friendId, -1);


    }
}
