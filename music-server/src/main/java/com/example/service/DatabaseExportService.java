package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DatabaseExportService {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public void exportDatabaseToFile(String filePath) {
        try {
            // 获取数据库名
            String dbName = getDatabaseNameFromUrl(dbUrl);

            // 构建导出命令
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (dbUrl.contains("mysql")) {
                processBuilder.command("mysqldump", "-u" + dbUsername, "-p" + dbPassword, dbName);
            } else if (dbUrl.contains("postgresql")) {
                processBuilder.command("pg_dump", "-U", dbUsername, "-d", dbName);
            } else {
                throw new IllegalArgumentException("Unsupported database type");
            }

            // 设置输出文件
            File outputFile = new File(filePath);
            processBuilder.redirectOutput(outputFile);

            // 重定向标准错误流
            File errorFile = new File("error.log");
            processBuilder.redirectError(errorFile);

            // 执行命令
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Database exported to " + filePath);
            } else {
                System.err.println("Failed to export database. Exit code: " + exitCode);
                System.err.println("Check the error log for more details: " + errorFile.getAbsolutePath());
                // 读取并打印错误日志
                printErrorLog(errorFile);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getDatabaseNameFromUrl(String dbUrl) {
        int lastSlashIndex = dbUrl.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < dbUrl.length() - 1) {
            return dbUrl.substring(lastSlashIndex + 1);
        }
        throw new IllegalArgumentException("Invalid database URL: " + dbUrl);
    }

    private void printErrorLog(File errorFile) {
        try {
            byte[] buffer = new byte[(int) errorFile.length()];
            try (java.io.FileInputStream fis = new java.io.FileInputStream(errorFile)) {
                fis.read(buffer);
                String errorMessage = new String(buffer);
                System.err.println(errorMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}