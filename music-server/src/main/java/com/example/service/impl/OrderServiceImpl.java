package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Consumer;
import com.example.entity.Params;
import com.example.entity.Order;
import com.example.entity.Product;
import com.example.exception.CustomException;
import com.example.mapper.ConsumerMapper;
import com.example.mapper.OrderMapper;
import com.example.mapper.ProductMapper;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ConsumerMapper consumerMapper;

    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }

    @Override
    public PageInfo<Order> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Order> list = orderMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Order order) {
        String time = DateUtil.now();

        if (order.getPrice() == null) {
            throw new CustomException("商品价格不能为空");
        } else if (order.getPrice() <= 0) {
            throw new CustomException("商品价格必须大于0");
        }
        if (order.getNum() == null) {
            throw new CustomException("商品数量不能为空");
        } else if (order.getNum() <= 0) {
            throw new CustomException("商品数量必须大于0");
        }
        Product product = productMapper.selectByPrimaryKey(order.getProductId());
//        System.out.println("-----------------------------------------------------------------------");
//        System.out.println( DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss"));
        if(DateUtil.parse(product.getSellTime(), "yyyy-MM-dd HH:mm:ss").after(DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss"))){
            throw new CustomException("商品还未到购买时间");
        }
        if(product.getSellStatus().equals("1")){
            throw new CustomException("商品不可售");
        }
        if ( order.getNum() > product.getAmount()) {
            throw new CustomException("商品库存不足");
        }
        Consumer consumer = consumerMapper.selectByPrimaryKey(order.getUserId());
        if(consumer.getPoint()<order.getPrice()*order.getNum()){
            throw new CustomException("用户积分不足");
        }
        List<Order> list = orderMapper.findByUserIdAndProductIdList(order.getUserId(), order.getProductId());
        if (list.size() > 0) {
            Integer totalNum = 0;
            for (Order o : list) {
                totalNum += o.getNum();
            }
            if(product.getMax()<totalNum+order.getNum()){
                throw new CustomException("选购数量超过当前商品的最大购买数量！当前商品剩余购买数量为："+(product.getMax()-totalNum));
            }

        }



        order.setCreateTime(time);
        if (order.getStatus() == null) {
            order.setStatus("0");
        }


        orderMapper.insertSelective(order);
        product.setAmount(product.getAmount()-order.getNum());
        productMapper.updateByPrimaryKeySelective(product);

        consumer.setPoint((int) (consumer.getPoint()-order.getPrice()*order.getNum()));
        consumerMapper.updateByPrimaryKeySelective(consumer);

    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void delete(Integer id) {
        orderMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Order findByById(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public Order findByUserIdAndProductId(Integer userId, Integer productId) {
        return orderMapper.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Order> findByUserId(Integer id) {
        return orderMapper.findByUserId(id);
    }

    @Override
    public List<Order> findByProductId(Integer id) {
        return orderMapper.findByProductId(id);
    }


}
