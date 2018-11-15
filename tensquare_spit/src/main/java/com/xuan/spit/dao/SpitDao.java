package com.xuan.spit.dao;

import com.xuan.spit.pojo.Spit;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p>Description: 吐槽数据访问层 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/15 0015
 * @Time: 11:59
 */
public interface SpitDao extends MongoRepository<Spit, String> {

}
