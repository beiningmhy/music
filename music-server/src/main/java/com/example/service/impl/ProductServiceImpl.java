package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.common.JwtTokenUtils;
import com.example.entity.Product;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.ProductMapper;
import com.example.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productMapper.selectAll();
    }

    @Override
    public PageInfo<Product> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Product> list = productMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Product product) {
        if (product.getName() == null || product.getName().equals("")) {
            throw new CustomException("商品名称不能为空");
        }
        if (product.getContent() == null || product.getContent().equals("")) {
            throw new CustomException("商品内容不能为空");
        }
        if (product.getPrice() == null) {
            throw new CustomException("商品价格不能为空");
        }else if(product.getPrice() <= 0){
            throw new CustomException("商品价格必须大于0");
        }
        if (product.getAmount() == null) {
            throw new CustomException("商品数量不能为空");
        } else if (product.getAmount() <= 0) {
            throw new CustomException("商品数量必须大于0");
        }
        if (product.getMax() == null) {
            throw new CustomException("商品最大购买数量不能为空");
        } else if (product.getMax() <= 0) {
            throw new CustomException("商品最大购买数量必须大于0");
        }


        String time = DateUtil.now();

        if (product.getSellTime() == null || product.getSellTime().equals("")) {
            product.setSellTime(time);
        }


        product.setCreateTime(time);
        if (product.getStatus() == null) {
            product.setStatus("1");
        }
        if (product.getSellStatus() == null) {
            product.setSellStatus("1");
        }


        productMapper.insertSelective(product);
    }

    @Override
    public void update(Product product) {

        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void delete(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Product findByById(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }


}
