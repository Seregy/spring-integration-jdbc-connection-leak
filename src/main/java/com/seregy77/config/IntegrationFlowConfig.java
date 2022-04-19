package com.seregy77.config;

import com.seregy77.processor.AggregationOutputProcessor;
import com.seregy77.processor.MessageProcessor;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jdbc.store.JdbcMessageStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@Configuration
public class IntegrationFlowConfig {

  private final AggregationOutputProcessor aggregationOutputProcessor;
  private final MessageProcessor messageProcessor;

  public IntegrationFlowConfig(AggregationOutputProcessor aggregationOutputProcessor,
      MessageProcessor messageProcessor) {
    this.aggregationOutputProcessor = aggregationOutputProcessor;
    this.messageProcessor = messageProcessor;
  }

  @Bean
  public DirectChannel initialMessageChannel() {
    return new DirectChannel();
  }

  @Bean
  public DirectChannel aggregatedMessageChannel() {
    return new DirectChannel();
  }

  @Bean
  public JdbcMessageStore jdbcMessageStore(DataSource dataSource) {
    return new JdbcMessageStore(dataSource);
  }

  @Bean
  public IntegrationFlow randomMessageGeneratorFlow() {
    return IntegrationFlows
        .from(() -> new GenericMessage<>(Math.random()))
        .channel(initialMessageChannel())
        .get();
  }

  @Bean
  public IntegrationFlow messageAggregationFlow(DataSource dataSource) {
    return IntegrationFlows.from(initialMessageChannel())
        .aggregate(aggregator -> aggregator
            .messageStore(jdbcMessageStore(dataSource))
            .processor(aggregationOutputProcessor)
            .correlationStrategy(payloadCorrelationStrategy())
            .releaseStrategy(alwaysReleasingStrategy()))
        .channel(aggregatedMessageChannel())
        .get();
  }

  @Bean
  public IntegrationFlow aggregatedMessageProcessingFlow() {
    return IntegrationFlows
        .from(aggregatedMessageChannel())
        .handle(messageProcessor::processMessage)
        .get();
  }

  private CorrelationStrategy payloadCorrelationStrategy() {
    return Message::getPayload;
  }

  private ReleaseStrategy alwaysReleasingStrategy() {
    return messageGroup -> true;
  }
}
