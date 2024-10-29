package com.example.controller;

import com.example.common.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

@CrossOrigin
@RestController
@RequestMapping("/system")
@Api(tags = "系统管理")
public class SystemController {
    @GetMapping("/memory")
    public Result getMemory() {
        // 获取系统信息
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        // 获取全局内存信息
        GlobalMemory memory = hardware.getMemory();

        // 获取总内存和已用内存
        long totalMemory = memory.getTotal();
        long usedMemory = memory.getTotal() - memory.getAvailable();

        // 计算内存使用百分比
        double memoryUsagePercentage = (double) usedMemory / totalMemory * 100;

        // 输出结果
//        System.out.printf("Total Memory: %.2f GB\n", totalMemory / (1024.0 * 1024.0 * 1024.0));
//        System.out.printf("Used Memory: %.2f GB\n", usedMemory / (1024.0 * 1024.0 * 1024.0));
//        System.out.printf("Memory Usage: %.2f%%\n", memoryUsagePercentage);
        return Result.success(memoryUsagePercentage);
    }

    @GetMapping("/cpu")
    public Result getCpu() throws InterruptedException {

        // 获取系统信息
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        // 获取CPU信息
        CentralProcessor processor = hardware.getProcessor();

        // 第一次获取CPU时间
        long[] prevCpuTime = processor.getSystemCpuLoadTicks();
        Thread.sleep(1000); // 等待一段时间以获取准确的CPU使用率

        // 第二次获取CPU时间
        long[] cpuTime = processor.getSystemCpuLoadTicks();

        // 计算总的CPU时间和空闲时间
        long idle = cpuTime[CentralProcessor.TickType.IDLE.getIndex()] - prevCpuTime[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = 0;
        for (int i = 0; i < cpuTime.length; i++) {
            totalCpu += cpuTime[i] - prevCpuTime[i];
        }

        // 计算CPU使用百分比
        double cpuUsagePercentage = (double) (totalCpu - idle) / totalCpu * 100;

        // 输出CPU使用情况
//        System.out.printf("CPU Usage: %.2f%%\n", cpuUsagePercentage);
        return Result.success(cpuUsagePercentage);
    }

    @GetMapping("/disk")
    public Result getDisk() {
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();

        for (OSFileStore fs : fileSystem.getFileStores()) {
            long usableSpace = fs.getUsableSpace();
            long totalSpace = fs.getTotalSpace();
            long usedSpace = totalSpace - usableSpace;
            double usage = ((double) usedSpace / totalSpace) * 100;

//            System.out.printf("File Store: %s\n", fs.getName());
//            System.out.printf("Total Space: %,d bytes\n", totalSpace);
//            System.out.printf("Used Space: %,d bytes\n", usedSpace);
//            System.out.printf("Free Space: %,d bytes\n", usableSpace);
//            System.out.printf("Usage: %.1f%%\n", usage);

        }
        return Result.success();
    }


}
