package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Song;
import com.example.exception.CustomException;
import com.example.service.SingerService;
import com.example.service.SongService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/song")
public class SongController {
    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    @Resource
    private SongService songService;
    @Resource
    private SingerService singerService;



    @PostMapping
//    @AutoLog("添加或修改用户")
    public Result save(@RequestBody Song song,  Integer cont) {
        if (song.getId() == null) {
            songService.add(song,cont);
        } else {
            songService.update(song);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Song> list = songService.findAll();
        return Result.success(list);
    }

    @GetMapping("/search")
//    @AutoLog("搜索用户")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Song> info = songService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
//    @AutoLog("删除用户")
    public Result delete(@PathVariable Integer id) {
        songService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Song> list) {
        for (Song song : list) {
            songService.delete(song.getId());
        }
        return Result.success();
    }
    @GetMapping("/export/{ids}")
    public Result export(@PathVariable List<Integer> ids, HttpServletResponse response) throws IOException {
//        System.out.println(ids);
        if (ids == null || ids.equals("")) {
            throw new CustomException("请勾选要导出的数据");
        }

        List<Integer> idsList = JSONUtil.toList(JSONUtil.parseArray(ids), Integer.class);
        List<Song> all =new ArrayList<>();
        for(Integer id : idsList){
//            System.out.println(id);
            all.add(songService.findByById(id));
//            System.out.println("id:"+id);
        }
        // 思考：
        // 要一行一行的组装数据，塞到一个list里面
        // 每一行数据，其实就对应数据库表中的一行数据，也就是对应Java的一个实体类Type
        // 我们怎么知道它某一列就是对应某个表头呢？？ 需要映射数据，我们需要一个Map<key,value>，把这个map塞到list里

        // 干！
        // 1. 从数据库中查询出所有数据
//        List<Type> all = typeService.findAll();
//
        if (CollectionUtil.isEmpty(all)) {
            throw new CustomException("未找到数据");
        }

        // 2. 定义一个 List，存储处理之后的数据，用于塞到 list 里
        List<Map<String, Object>> list = new ArrayList<>(all.size());

        // 3. 定义Map<key,value> 出来，遍历每一条数据，然后封装到 Map<key,value> 里，把这个 map 塞到 list 里
        for (Song song : all) {
            // 优化使用LinkedHashMap可以保持字段顺序
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("歌曲ID", song.getId());
            row.put("歌手名称", song.getSingerName());
            row.put("歌曲名称", song.getName());
            row.put("歌曲介绍",song.getIntroduction());
            row.put("歌曲创建时间",song.getCreateTime());
            row.put("歌曲更新时间",song.getUpdateTime());
            row.put("歌曲图片", song.getPic());
            row.put("歌曲歌词", song.getLyric());
            row.put("歌曲地址", song.getUrl());

            list.add(row);
        }

        // 4. 创建一个 ExcelWriter，把 list 数据用这个writer写出来（生成出来）
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(list, true);

        // 5. 把这个 excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=song.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        wr.close();
        IoUtil.close(System.out);


        return Result.success();
    }
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Song> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Song.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Song song : infoList) {
                song.setId(null);
                Integer id = singerService.findByName(song.getSingerName()).getId();
                song.setSingerId(id);
//                System.out.println(song.toString());
                try {
                    songService.add(song,1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }
}
