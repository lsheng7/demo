package com.example.demo;

import com.example.demo.bean.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * 演示应用程序
 *
 * @author lvsheng
 * @version 1.0.0
 * @date 2021/11/19 15:36
 */
@SpringBootApplication
@Slf4j
public class DemoApplication {

  @Bean
  @ConfigurationProperties(prefix = "people")
  public People people() {
    return new People();
  }

  /**
   * 主要
   *
   * @param args arg游戏
   * @author lvsheng
   * @date 2021/11/19 15:36
   */
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
