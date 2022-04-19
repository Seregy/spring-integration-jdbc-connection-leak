package com.seregy77;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcConnectionLeakApplication {

  public static void main(String[] args) {
    SpringApplication.run(JdbcConnectionLeakApplication.class, args);
  }
}
