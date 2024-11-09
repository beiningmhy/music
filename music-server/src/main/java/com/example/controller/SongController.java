package com.example.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Singer;
import com.example.entity.Song;
import com.example.exception.CustomException;
import com.example.service.SingerService;
import com.example.service.SongService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/song")
@Api(tags = "歌曲管理")
public class SongController {
    private static final Logger log = LoggerFactory.getLogger(SongController.class);

    @Resource
    private SongService songService;
    @Resource
    private SingerService singerService;


    @PostMapping
    @AutoLog("添加或修改歌曲")
    public Result save(@RequestBody Song song, Integer cont) {
        if (song.getId() == null) {
            songService.add(song, cont);
        } else {
            songService.update(song);
        }
        return Result.success();
    }

    @GetMapping
    public Result findAll(Params params) {
        List<Song> list = songService.findAll(params);
        return Result.success(list);
    }

    @GetMapping("/search")
    @AutoLog("搜索歌曲")
    public Result findBySearch(Params params) {
//        log.info("拦截器已放行");
        PageInfo<Song> info = songService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    @AutoLog("删除歌曲")
    public Result delete(@PathVariable Integer id) {
        songService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    @AutoLog("批量删除歌曲")
    public Result delBatch(@RequestBody List<Song> list) {
        for (Song song : list) {
            songService.delete(song.getId());
        }
        return Result.success();
    }

    @GetMapping("/export/{ids}")
    @AutoLog("批量导出歌曲")
    public Result export(@PathVariable List<Integer> ids, HttpServletResponse response) throws IOException {
//        System.out.println(ids);
        if (ids == null || ids.equals("")) {
            throw new CustomException("请勾选要导出的数据");
        }

        List<Integer> idsList = JSONUtil.toList(JSONUtil.parseArray(ids), Integer.class);
        List<Song> all = new ArrayList<>();
        for (Integer id : idsList) {
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
            row.put("歌曲介绍", song.getIntroduction());
            row.put("歌曲创建时间", song.getCreateTime());
            row.put("歌曲更新时间", song.getUpdateTime());
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
    @AutoLog("批量导入歌曲")
    public Result upload(MultipartFile file) throws IOException {
        List<Song> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Song.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Song song : infoList) {
                song.setId(null);
                Singer singer = singerService.findByName(song.getSingerName());
                Integer id;
                if (singer != null) {
                    id = singer.getId();
                } else {
                    throw new CustomException("请检查歌手名称或先添加歌手");
                }
                song.setSingerId(id);
//                System.out.println(song.toString());
                try {
                    songService.add(song, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }

    @GetMapping("/songCount")
    public Result songCount() {
        List<Song> songs = songService.findAll(new Params());
        Map<String, Long> collect = songs.stream().
                collect(Collectors.groupingBy(
                        song -> Optional.ofNullable(song.getSingerName()).orElse("未知"),
                        // 使用counting()收集器来计算每个分组的数量
                        Collectors.counting()));
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (String key : collect.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            map.put("value", collect.get(key));
            mapList.add(map);
        }
        return Result.success(mapList);
    }

    @GetMapping("/lyric/{id}")
    public Result findLyric(@PathVariable Integer id) {
        Song song = songService.findByById(id);
        String lyric = song.getLyric();
        if (lyric == null) {
            return Result.error("暂无歌词");
        }
        List<Map<String, String>> lyricList = parseLyric(lyric);
//        for (Map<String, String> map : lyricList) {
//            System.out.println("Time: " + map.get("time") + ", Lyric: " + map.get("lyric"));
//        }
        return Result.success(lyricList);
    }

    //    public static List<Map<String, String>> parseLyric(String lyric) {
//        List<Map<String, String>> result = new ArrayList<>();
//        Pattern pattern = Pattern.compile(""\\[(\\d{2}:\\d{2}\\.\\d{2})\\](.*)"");
//        Matcher matcher = pattern.matcher(lyric);
//
//        while (matcher.find()) {
//            Map<String, String> map = new HashMap<>();
//            map.put("time", matcher.group(1));
//            map.put("lyric", matcher.group(2).trim());
//            result.add(map);
//        }
//
//        return result;
//    }
    public static List<Map<String, String>> parseLyric(String lyric) {
        List<Map<String, String>> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        String[] lines = lyric.split("\n");

        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String time = matcher.group(1);
                String lyricContent = line.substring(matcher.end()).trim();
                Map<String, String> map = new HashMap<>();
                map.put("time", time);
                map.put("lyric", lyricContent);
                result.add(map);
            }
        }

        return result;
    }
    @GetMapping("/top/{num}")
    public Result top(@PathVariable String num) {
        return Result.success();
    }
}
