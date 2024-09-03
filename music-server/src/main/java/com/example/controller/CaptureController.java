package com.example.controller;

import com.example.common.CaptureConfig;
import com.sun.xml.bind.util.Which;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Case;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping
public class CaptureController {

    @RequestMapping("/captcha")
    public void captcha(@RequestParam String key, HttpServletRequest request, HttpServletResponse response) throws Exception {

//        // 指定验证码的长宽以及字符的个数
//        SpecCaptcha captcha = new SpecCaptcha(135, 33, 5);
//        captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
//        // 首先把验证码在后台保存一份，但是不能保存在session，可以存在redis，也可以存在后台的某个Map里面
//        CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
//        CaptchaUtil.out(captcha, request, response);

        // 算术类型
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(135, 33);
//        captcha.setLen(4);  // 几位数运算，默认是两位
//        captcha.getArithmeticString();  // 获取运算的公式：3+2=?
//        captcha.text();  // 获取运算的结果：5
//        CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
//        CaptchaUtil.out(captcha, request, response);


//        1到5随机数
        int i = 1 + (int) (Math.random() * 5);
        SpecCaptcha captcha = new SpecCaptcha(135, 33, 5);

        ArithmeticCaptcha captcha1 = new ArithmeticCaptcha(135, 33);

        switch (i) {
            case 1:
                captcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
                CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
                CaptchaUtil.out(captcha, request, response);
                break;
            case 2:
                captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
                CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
                CaptchaUtil.out(captcha, request, response);
                break;
            case 3:
                captcha.setCharType(Captcha.TYPE_ONLY_UPPER);
                CaptureConfig.CAPTURE_MAP.put(key, captcha1.text().toLowerCase());
                CaptchaUtil.out(captcha1, request, response);
                break;
            case 4:
                captcha1.setLen(4);  // 几位数运算，默认是两位
                captcha1.getArithmeticString();  // 获取运算的公式：3+2=?
                CaptureConfig.CAPTURE_MAP.put(key, captcha1.text().toLowerCase());
                CaptchaUtil.out(captcha1, request, response);
                break;
            case 5:
                captcha1.setLen(6);  // 几位数运算，默认是两位
                captcha1.getArithmeticString();  // 获取运算的公式：3+2=?
                CaptureConfig.CAPTURE_MAP.put(key, captcha.text().toLowerCase());
                CaptchaUtil.out(captcha, request, response);
                break;
            default:
                break;
        }


    }

}