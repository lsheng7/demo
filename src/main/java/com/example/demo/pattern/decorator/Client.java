package com.example.demo.pattern.decorator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {

  public static void main(String[] args) {
//    Component component = new ConcreteDecorator(new ConcreteComponent());
//    component.method();

    final byte[] src = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(src);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream, 5)) {

      IntStream.range(0, 15).forEach(loop -> {
        try {
          int read = bufferedInputStream.read();
          System.out.println(read);
        } catch (IOException e) {
          log.error(e.getMessage());
        }
      });
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
