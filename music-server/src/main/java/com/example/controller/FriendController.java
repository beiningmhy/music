package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Consumer;
import com.example.entity.Friend;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.ConsumerService;
import com.example.service.FriendService;
import com.example.service.ImsingleService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/friend")
@Api(tags = "好友管理")
public class FriendController {
    private static final Logger log = LoggerFactory.getLogger(FriendController.class);

    @Resource
    private FriendService friendService;
    @Resource
    private ConsumerService consumerService;

    @Resource
    private ImsingleService imsingleService;

    @PostMapping
    @AutoLog("添加或修改管理员")
    public Result save(@RequestBody Friend friend) {
        if (friend.getId() == null) {
            friendService.add(friend);
        } else {
            friendService.update(friend);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Friend> list = friendService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索管理员")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Friend> info = friendService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除管理员")
    public Result delete(@PathVariable Integer id) {
        friendService.delete(id);
        return Result.success();
    }

    @GetMapping("/friends/{id}")
    public Result findByFriends(@PathVariable String id) {
        List<Friend> list = friendService.findByFriend1orFriend2(id);
        List<Consumer> consumerList = new ArrayList<>();
        for (Friend friend : list) {
            Integer t = null;
            if (friend.getFriend1().equals(id)) {
                t = Integer.parseInt(friend.getFriend2());
            } else if (friend.getFriend2().equals(id)) {
                t = Integer.parseInt(friend.getFriend1());
            }
            Consumer byById = consumerService.findByById(t);
            Integer unReadNums = imsingleService.findUnReadNums( String.valueOf(byById.getId()),id);
            byById.setNum(unReadNums);
            consumerList.add(byById);
        }
        return Result.success(consumerList);

    }

}
