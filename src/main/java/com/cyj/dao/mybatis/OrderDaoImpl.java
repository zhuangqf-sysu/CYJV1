package com.cyj.dao.mybatis;

import com.cyj.dao.OrderDao;
import com.cyj.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
@Repository
public class OrderDaoImpl implements OrderDao{
    @Resource
    private SqlSession sqlSession;
    public int insertOrder(OrderModel model) {
        return sqlSession.getMapper(OrderDao.class).insertOrder(model);
    }

    public List<OrderModel> getAllOrderByState(int state) {
        return sqlSession.getMapper(OrderDao.class).getAllOrderByState(0);
    }

    public List<OrderModel> getOnePageOrderByState(@Param("state") int state, @Param("offset") int offset, @Param("pageSize") int pageSize) {
        return sqlSession.getMapper(OrderDao.class).getOnePageOrderByState(state, offset, pageSize);
    }

    public OrderModel findModelById(int id) {
        return sqlSession.getMapper(OrderDao.class).findModelById(id);
    }

    public int updateAcceptNumber(int acceptNumber,int id) {
        return sqlSession.getMapper(OrderDao.class).updateAcceptNumber(acceptNumber,id);
    }

    public int updatePayedMoney(@Param("payedMoney") double payedMoney, @Param("id") int id) {
        return sqlSession.getMapper(OrderDao.class).updatePayedMoney(payedMoney, id);
    }

    public int updateState(@Param("state") int state, @Param("id") int id) {
        return sqlSession.getMapper(OrderDao.class).updateState(state, id);
    }

    public OrderModel findModelByApplicationId(int applicationId) {
        return sqlSession.getMapper(OrderDao.class).findModelByApplicationId(applicationId);
    }
}
