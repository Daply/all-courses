package org.dariaples.async;

import org.dariaples.TimeMeasured;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompletableFutureExample {

  /**
   * The block in CompletableFuture is executed in the random thread,
   * that was distinguished from ForkJoinPool
   */
  @TimeMeasured
  public static void runAsync() throws InterruptedException {
    CompletableFuture.runAsync(
            () -> {
              System.out.println("I run asynchronously in thread " + Thread.currentThread().getName());
            }
    );

    Thread.sleep(100);
  }

  /**
   * The block in CompletableFuture is executed in the thread of executor service
   */
  @TimeMeasured
  public static void runCompletableFutureWithExecutor() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Runnable task = () -> {
      System.out.println("I run asynchronously in thread " + Thread.currentThread().getName());
    };
    CompletableFuture.runAsync(task, executor);

    executor.shutdown();
  }

  /**
   * The block in CompletableFuture is executed in the random thread,
   * that was distinguished from ForkJoinPool
   */
  @TimeMeasured
  public static void runCompletableFutureWithSupplier() {
    Supplier<String> supplier = () -> Thread.currentThread().getName();
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier);
    String string = completableFuture.join();
    System.out.println("Result = " + string);
  }

  /**
   * The block in CompletableFuture is executed in the ExecutorService thread
   */
  @TimeMeasured
  public static void runCompletableFutureWithExecutorAndSupplier() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Supplier<String> supplier = () -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      return Thread.currentThread().getName();
    };
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier, executor);
    String string = completableFuture.join();
    System.out.println("Result = " + string);

    executor.shutdown();
  }

  /**
   * The block in CompletableFuture is executed in the ExecutorService thread
   */
  @TimeMeasured
  public static void runCompletableFutureWithExecutorAndSupplierAndCheckCompletion() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Supplier<String> supplier = () -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      return Thread.currentThread().getName();
    };
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier, executor);

    completableFuture.complete("Too long!");

    String string = completableFuture.join();
    System.out.println("Result = " + string);

    executor.shutdown();
  }

  /**
   * The block in CompletableFuture is executed in the ExecutorService thread
   */
  @TimeMeasured
  public static void runCompletableFutureWithExecutorAndSupplierAndCheckExecution() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Supplier<String> supplier = () -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      return Thread.currentThread().getName();
    };
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier, executor);

    String string = completableFuture.join();
    System.out.println("Result = " + string);

    completableFuture.complete("Too long!");

    string = completableFuture.join();
    System.out.println("Result = " + string);

    executor.shutdown();
  }

  /**
   * The block in CompletableFuture is executed in the ExecutorService thread
   */
  @TimeMeasured
  public static void runCompletableFutureWithExecutorAndSupplierAndCheckObtrude() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Supplier<String> supplier = () -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      return Thread.currentThread().getName();
    };
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(supplier, executor);

    String string = completableFuture.join();
    System.out.println("Result = " + string);

    completableFuture.obtrudeValue("Too long!");

    string = completableFuture.join();
    System.out.println("Result = " + string);

    executor.shutdown();
  }

  /**
   * The block in CompletableFuture is executed in ForkJoinPool thread
   */
  @TimeMeasured
  public static void runCompletableFuture() {
    CompletableFuture<Void> completableFuture = new CompletableFuture<>();

    Runnable task = () -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException exception) {
        exception.printStackTrace();
      }
      completableFuture.complete(null);
      System.out.println("Run in thread " + Thread.currentThread().getName());
    };
    CompletableFuture.runAsync(task);

    Void executed = completableFuture.join();

    System.out.println("We are done!");
  }

  public static void main(String[] args) {
    runCompletableFuture();
  }
}
