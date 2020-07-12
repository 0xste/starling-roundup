package com.stefanomantini.starlingroundup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan({"com.stefanomantini.*"})
class StarlingRoundupApplicationTests {
  @Test
  void contextLoads() {}
}
