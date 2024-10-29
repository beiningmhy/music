package com.example.controller;

import com.example.service.DatabaseExportService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/database")
@Api(tags = "数据库管理")
public class DatabaseExportController {

    @Resource
    private DatabaseExportService databaseExportService;

    @GetMapping("/export")
    public String exportDatabase() {
        String filePath = "exported_database.sql";
        databaseExportService.exportDatabaseToFile(filePath);
        return "Database exported to " + filePath;
    }
}