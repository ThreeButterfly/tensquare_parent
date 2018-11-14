package com.xuan.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xuan.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface EnterpriseDao extends JpaRepository<Enterprise, String>, JpaSpecificationExecutor<Enterprise> {

    /**
     * <p>Description: 查询热门企业列表 </p>
     * @author Yat-Xuan
     * @params: [isHot]
     * @return: java.util.List<com.xuan.recruit.pojo.Enterprise>
     * @Date: 2018/11/14 0014 11:08
     * @Modified By:
    */
    List<Enterprise> findByIshot(String isHot);
}
