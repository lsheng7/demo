package com.example.demo.runner;

import cn.hutool.crypto.SecureUtil;
import com.example.demo.bean.People;
import javax.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SystemApplicationRunner implements ApplicationRunner {

  @Resource
  private People people;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println(people);
    System.out.println(SecureUtil.md5("Lv123456"));
  }

}
