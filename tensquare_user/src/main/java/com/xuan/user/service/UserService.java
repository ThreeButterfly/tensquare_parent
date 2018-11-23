package com.xuan.user.service;

import com.xuan.user.dao.UserDao;
import com.xuan.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import util.IdWorker;
import util.JwtUtil;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private BCryptPasswordEncoder encoder;

    @Resource
    private HttpServletRequest request;

    @Resource
    private JwtUtil jwtUtil;


    public User login(User user) {
        User userLogin = userDao.findByMobile(user.getMobile());

        if (userLogin != null && encoder.matches(user.getPassword(), userLogin.getPassword())) {
            return userLogin;
        }
        return null;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        //密码加密
        user.setPassword(encoder.encode(user.getPassword()));
        // 关注数
        user.setFollowcount(0);
        //粉丝数
        user.setFanscount(0);
        //在线时长
        user.setOnline(0L);
        //注册日期
        user.setRegdate(new Date());
        //更新日期
        user.setUpdatedate(new Date());
        //最后登陆日期
        user.setLastdate(new Date());
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {

        //获取请求头中的信息
        String header = request.getHeader("Authorization");

        if (header == null || "".equals(header)) {
            throw new RuntimeException("请先登录");
        }

        if (!header.startsWith("Bearer ")) {
            throw new RuntimeException("权限不足");
        }

        //提取token
        String token = header.substring(7);
        //解析token
        Claims claims = null;

        try {
            claims = jwtUtil.parseJWT(token);

            //获取角色
            String role = (String) claims.get("roles");

            //有删除操作的角色身份，这里先暂时设置为默认
            String deleteRole = "admin";

            if (role == null || !deleteRole.equals(role)) {
                throw new RuntimeException("权限不足");
            }
        } catch (Exception e) {
            throw new RuntimeException("权限不足");
        }

        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 手机号
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        //生成六位随机数
        String checkCode = RandomStringUtils.randomNumeric(6);

        //控制台输出【用于测试】
        System.out.println("验证码为：" + checkCode);

        //把验证码存入redis中，并设置有效时间为两个小时
        redisTemplate.opsForValue().set("checkCode_" + mobile, checkCode, 2, TimeUnit.HOURS);

        //把验证码放入到消息队列中，给用户发送
        Map<String, String> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("checkCode", checkCode);

        rabbitTemplate.convertAndSend("sms", map);
    }
}
