package com.cyj.dao;

import com.cyj.model.OrderModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */
public interface OrderDao {
    int insertOrder(OrderModel model);
    List<OrderModel> getAllOrderByState(int state);
    List<OrderModel> getOnePageOrderByState(@Param("state") int state, @Param("offset")int offset, @Param("pageSize")int pageSize);
    OrderModel findModelById(int id);
    int updateAcceptNumber(@Param("acceptNum")int acceptNumber,@Param("id")int id);
    int updatePayedMoney(@Param("payedMoney")double payedMoney, @Param("id")int id);
    int updateState(@Param("state")int state, @Param("id")int id);
    OrderModel findModelByApplicationId(int applicationId);
}
