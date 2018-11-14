package com.xuan.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xuan.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface RecruitDao extends JpaRepository<Recruit, String>, JpaSpecificationExecutor<Recruit> {

    /**
     * <p>Description: 查询推荐职位 </p>
     * @author Yat-Xuan
     * @param state
     * @return java.util.List<com.xuan.recruit.pojo.Recruit>
     * @Date: 2018/11/14 0014 11:31
     * @Modified By:
    */
    List<Recruit> findByStateOrderByCreatetimeDesc(String state);

    /**
     * <p>Description: 描述 </p>
     * @author Yat-Xuan
     * @param state
     * @return java.util.List<com.xuan.recruit.pojo.Recruit>
     * @Date: 2018/11/14 0014 12:42
     * @Modified By:
    */
    List<Recruit> findTop9ByStateNotOrderByCreatetimeDesc(String state);

}
