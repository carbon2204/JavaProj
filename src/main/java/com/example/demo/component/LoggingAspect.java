package com.example.demo.component;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * The type Logging aspect.
 */
@Aspect
@Component
public class LoggingAspect {
  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
  private final Counter counter = new Counter();

  @Pointcut("execution(* com.example.demo.controllers.*.create*(..))")
    public void create() {

  }

  @Pointcut("execution(* com.example.demo.controllers.*.update*(..))")
    public void update() {

  }

  @Pointcut("execution(* com.example.demo.controllers.*.delete*(..))")
    public void delete() {

  }

  @Pointcut("execution(* com.example.demo.controllers.CarController.*(..))")
  public void countLocation() {

  }

  @AfterReturning(pointcut = "create()", returning = "result")
    public void logCreate(Object result) {
    logger.info("Created: {}", result);
  }

  @AfterReturning(pointcut = "update()", returning = "result")
    public void logUpdate(Object result) {
    logger.info("Updated: {}", result);
  }

  @AfterReturning(pointcut = "delete()", returning = "result")
    public void logDelete(Object result) {
    logger.info("Deleted: {}", result);
  }

  @AfterReturning(pointcut = "countLocation()")
  public void logCountCar() {
    logger.info("Car service count: {}", counter.incrementCounter());
  }
}
