package com.seregy77;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.seregy77.processor.MessageProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = JdbcConnectionLeakApplication.class,
    properties = "spring.datasource.url: jdbc:tc:postgresql:11:///jdbc-example-db")
class DatabaseConnectionLeakTest {

  @MockBean
  private MessageProcessor messageProcessor;

  @Test
  void shouldProcessMoreMessagesThanDatabaseConnections() {
    verify(messageProcessor, timeout(5000).atLeast(3)).processMessage(any());
  }
}
