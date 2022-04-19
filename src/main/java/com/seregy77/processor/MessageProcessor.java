package com.seregy77.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

  public void processMessage(Message<?> message) {
    LOGGER.info("Processing aggregated message: {}", message.getPayload());
  }
}
