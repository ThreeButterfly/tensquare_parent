package com.xuan.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xuan.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /**
     * 最新回复
     * @param labelId 标签id
     * @param pageable 分页
     * @return
     */
    @Query(value = "SELECT * from tb_problem,tb_pl where id=problemid AND labelid=? ORDER BY replytime DESC", nativeQuery = true)
    Page<Problem> newlist(String labelId, Pageable pageable);

    /**
     * 最热回复
     * @param labelId 标签id
     * @param pageable 分页
     * @return
     */
    @Query(value = "SELECT * from tb_problem,tb_pl where id=problemid AND labelid=? ORDER BY reply DESC", nativeQuery = true)
    Page<Problem> hotlist(String labelId, Pageable pageable);

    /**
     * 等待回复
     * @param labelId 标签id
     * @param pageable 分页
     * @return
     */
    @Query(value = "SELECT * from tb_problem,tb_pl where id=problemid AND labelid=? AND reply=0 ORDER BY createtime DESC", nativeQuery = true)
    Page<Problem> waitlist(String labelId, Pageable pageable);
}
