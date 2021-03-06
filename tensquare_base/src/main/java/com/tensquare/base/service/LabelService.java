package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.PageUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 描述 </p>
 *
 * @Created with IDEA
 * @author: Yi-Xuan
 * @Date: 2018/11/13 0013
 * @Time: 14:46
 */
@Service("labelService")
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * <p>Description: 查询全部标签 </p>
     *
     * @author Yat-Xuan
     * @params: []
     * @return: java.util.List<com.tensquare.base.pojo.Label>
     * @Date: 2018/11/13 0013 14:49
     * @Modified By:
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * <p>Description: 根据ID查询标签 </p>
     *
     * @author Yat-Xuan
     * @params: [id]
     * @return: com.tensquare.base.pojo.Label
     * @Date: 2018/11/13 0013 14:49
     * @Modified By:
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * <p>Description:增加标签 </p>
     *
     * @author Yat-Xuan
     * @params: [label]
     * @return: void
     * @Date: 2018/11/13 0013 14:50
     * @Modified By:
     */
    public void save(Label label) {
        /**
         * 设置ID
         */
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(getPredicate(label));
    }

    public Page<Label> pageQuery(Label label, int page, int size) {

        PageUtil pageUtil = new PageUtil();
        pageUtil.setCurrNo(page);
        pageUtil.setSize(size);

        Pageable pageable = PageRequest.of(pageUtil.getCurrNo() - 1, pageUtil.getSize());
        return labelDao.findAll(getPredicate(label), pageable);
    }

    public Specification<Label> getPredicate(Label label) {
        return new Specification<Label>() {
            /**
             *
             * @param root 根对象，也就是要把条件封装到那个对象中去。类似：where 类名=label.getId
             * @param criteriaQuery 封装的都是查询的关键字 比如：order by, group by等
             * @param criteriaBuilder 用来封装条件对象，如果直接返回null 表示不需要任何查询条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                //new 一个集合存放所有的条件
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //下面相当于 where like "%小明%"
                    Predicate predicate =
                            criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }

                if (label.getState() != null && !"".equals(label.getState())) {
                    Predicate predicate =
                            criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }

                if (label.getRecommend() != null && !"".equals(label.getRecommend())) {
                    Predicate predicate =
                            criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(predicate);
                }

                if (label.getCount() != null && !"".equals(label.getCount())) {
                    Predicate predicate =
                            criteriaBuilder.equal(root.get("count").as(String.class), label.getCount());
                    list.add(predicate);
                }

                if (label.getFans() != null && !"".equals(label.getFans())) {
                    Predicate predicate =
                            criteriaBuilder.equal(root.get("fans").as(String.class), label.getFans());
                    list.add(predicate);
                }

                //new 一个数组作为最终返回值的条件
                Predicate[] predicates = new Predicate[list.size()];

                //把list转成数组
                predicates = list.toArray(predicates);

                return criteriaBuilder.and(predicates);
            }
        };
    }
}
