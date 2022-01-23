package org.dariaples.async;

import org.dariaples.TimeMeasured;
import org.dariaples.async.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ExceptionExample {

  @TimeMeasured
  public static void tryException() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("No data");
      //return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      return ids.stream().map(User::new).collect(Collectors.toList());
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };

    CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<User>> fetch = supply.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    sleep(1_000);
    System.out.println("Supply    : done = " + supply.isDone() +
            " exception = " + supply.isCompletedExceptionally());
    System.out.println("Fetch     : done = " + fetch.isDone() +
            " exception = " + fetch.isCompletedExceptionally());
    System.out.println("Display   : done = " + display.isDone() +
            " exception = " + display.isCompletedExceptionally());
  }

  @TimeMeasured
  public static void tryExceptionHandling() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("No data");
      //return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      return ids.stream().map(User::new).collect(Collectors.toList());
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };

    CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<Long>> exception = supply.exceptionally(e -> List.of());
    CompletableFuture<List<User>> fetch = exception.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    sleep(1_000);
    System.out.println("Supply    : done = " + supply.isDone() +
            " exception = " + supply.isCompletedExceptionally());
    System.out.println("Fetch     : done = " + fetch.isDone() +
            " exception = " + fetch.isCompletedExceptionally());
    System.out.println("Display   : done = " + display.isDone() +
            " exception = " + display.isCompletedExceptionally());
  }

  @TimeMeasured
  public static void tryExceptionHandlingWithJoin() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("No data");
      //return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      return ids.stream().map(User::new).collect(Collectors.toList());
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };

    CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<Long>> exception = supply.exceptionally(e -> List.of());
    CompletableFuture<List<User>> fetch = exception.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    supply.join();

    sleep(1_000);
    System.out.println("Supply    : done = " + supply.isDone() +
            " exception = " + supply.isCompletedExceptionally());
    System.out.println("Fetch     : done = " + fetch.isDone() +
            " exception = " + fetch.isCompletedExceptionally());
    System.out.println("Display   : done = " + display.isDone() +
            " exception = " + display.isCompletedExceptionally());
  }

  @TimeMeasured
  public static void tryExceptionHandlingWithLogging() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("No data");
      //return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      return ids.stream().map(User::new).collect(Collectors.toList());
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };

    CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<Long>> exception =
            supply.whenComplete(
                    (ids, e) -> {
                      if (e != null) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                      }
                    }
            );
    CompletableFuture<List<User>> fetch = exception.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    sleep(1_000);
    System.out.println("Supply    : done = " + supply.isDone() +
            " exception = " + supply.isCompletedExceptionally());
    System.out.println("Fetch     : done = " + fetch.isDone() +
            " exception = " + fetch.isCompletedExceptionally());
    System.out.println("Display   : done = " + display.isDone() +
            " exception = " + display.isCompletedExceptionally());
  }

  @TimeMeasured
  public static void exceptionHandlingWithLogging() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      throw new IllegalStateException("No data");
      //return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      return ids.stream().map(User::new).collect(Collectors.toList());
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };

    CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<Long>> exception =
            supply.handle(
                    (ids, e) -> {
                      if (e != null) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return List.of();
                      } else {
                        return ids;
                      }
                    }
            );
    CompletableFuture<List<User>> fetch = exception.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    sleep(1_000);
    System.out.println("Supply    : done = " + supply.isDone() +
            " exception = " + supply.isCompletedExceptionally());
    System.out.println("Fetch     : done = " + fetch.isDone() +
            " exception = " + fetch.isCompletedExceptionally());
    System.out.println("Display   : done = " + display.isDone() +
            " exception = " + display.isCompletedExceptionally());
  }

  public static void main(String[] args) {
    tryExceptionHandlingWithJoin();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
