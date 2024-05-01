package com.example.demo.component;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

/** The type Request counter. */
@Service
public class Counter {
  private final AtomicInteger count = new AtomicInteger(0);

  public synchronized int incrementCounter() {
    return count.incrementAndGet();
  }
}