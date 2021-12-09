package com.example.demo.bean;

import java.util.List;
import lombok.Data;

@Data
public class People {

  private String name;

  private Integer age;

  private List<String> address;

  //@NestedConfigurationProperty 可以省略
  private Phone phone;
}