package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Product;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.ProductService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@CrossOrigin
@RestController
@RequestMapping("/product")
@Api(tags = "商品管理")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService productService;

    @PostMapping
    @AutoLog("添加或修改商品")
    public Result save(@RequestBody Product product) {
        if (product.getId() == null) {
            productService.add(product);
        } else {
            productService.update(product);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Product> list = productService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索商品")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Product> info = productService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除商品")
    public Result delete(@PathVariable Integer id) {
        productService.delete(id);
        return Result.success();
    }
    private static final String filePath = System.getProperty("user.dir") + "/file/";
    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        String yearMonth = YearMonth.now().toString();
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();
        try {
            // 文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), filePath +yearMonth+"/"+ flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        Map<String, Object> resMap = new HashMap<>();
        // wangEditor上传图片成功后， 需要返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", "http://localhost:8080/api/files/" + yearMonth+"|"+flag+"-"+fileName)));
        return resMap;
    }



}
