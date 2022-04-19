package com.seregy77.processor;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AggregationOutputProcessor {

  public Double process(List<Double> doubles) {
    return doubles.get(0);
  }
}
