package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.example.common.AutoLog;
import com.example.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/files")
public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @AutoLog("文件上传")
    public Result upload(MultipartFile file,String fileDir) {

        synchronized (FileController.class) {
//            获取当前年月
            String yearMonth = YearMonth.now().toString();
//            获取当前时间戳
            String flag = System.currentTimeMillis() + "";
//            获取原始文件名
            String fileName = file.getOriginalFilename();

            try {
//                判断有没有文件夹，没有则创建
                if (!FileUtil.isDirectory(filePath)) {
                    FileUtil.mkdir(filePath);
                }
                if(fileDir != null&& !fileDir.equals("")){
                    FileUtil.writeBytes(file.getBytes(), filePath + fileDir + "/" + flag + "-" + fileName);
                    System.out.println(fileName + "--上传成功");
                    Thread.sleep(1L);
                }else{
                    fileDir=yearMonth;
                    FileUtil.writeBytes(file.getBytes(), filePath + yearMonth + "/" + flag + "-" + fileName);
                    System.out.println(fileName + "--上传成功");
                    Thread.sleep(1L);
                }
                // 文件存储形式：时间戳-文件名

            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }
            return Result.success(fileDir + "|" + flag + "-" + fileName);
        }
    }


    /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        if(!flag.contains("|")){
            return ;
        }
        String[] split = flag.split("\\|");
        split[0] = split[0] + "/";
//        System.out.println(flag);

//        判断split是否为null
        if (flag.equals("null") || flag == null || flag.equals("")) {
            return ;
        }

        if (!FileUtil.isDirectory(filePath + split[0])) {
            FileUtil.mkdir(filePath + split[0]);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath + split[0]);
        String avatar = fileNames.stream().filter(name -> name.contains(split[1])).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(avatar)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + split[0] + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
//            System.out.println("文件下载失败");
        }
    }

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
            FileUtil.writeBytes(file.getBytes(), filePath + yearMonth + "/" + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        Map<String, Object> resMap = new HashMap<>();
        // wangEditor上传图片成功后， 需要返回的参数
        resMap.put("errno", 0);
        resMap.put("data", CollUtil.newArrayList(
                Dict.create()
                        .set("url", "http://localhost:8080/api/files/" + yearMonth + "|" + flag + "-" + fileName)
                        .set("href", "http://localhost:8080/api/files/" + yearMonth + "|" + flag + "-" + fileName)
                        .set("alt",  yearMonth + "|" + flag + "-" + fileName)
        ));
        return resMap;
    }

}