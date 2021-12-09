package com.example.demo.config;

import static org.zalando.logbook.Conditions.contentType;
import static org.zalando.logbook.Conditions.exclude;
import static org.zalando.logbook.Conditions.requestTo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultCorrelationId;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@Configuration
public class LogbookConfig {

  @Bean
  public Logbook logbook() {
    Logbook logbook = Logbook.builder()
        //为请求和响应设置唯一的id
        .correlationId(new DefaultCorrelationId())
        //设置默认的日志写操作
        .sink(new DefaultSink(new JsonHttpLogFormatter(), new SystemHttpLogWriter()))
        //设置过滤规则
        .condition(
            exclude(requestTo("/index/get"),
                contentType("application/octet-stream")))
        .build();
    return logbook;
  }
}
