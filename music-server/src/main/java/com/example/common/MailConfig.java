package com.example.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MailConfig {
    public static Map<String, String> MAIL_MAP = new HashMap<>();
}
