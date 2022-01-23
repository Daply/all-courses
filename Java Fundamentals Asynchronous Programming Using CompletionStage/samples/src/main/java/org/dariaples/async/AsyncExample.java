package org.dariaples.async;

import org.dariaples.TimeMeasured;
import org.dariaples.async.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncExample {

  @TimeMeasured
  public static void runInChainSynchronously() {
    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, List<User>> fetchUsers = ids -> {
      sleep(300);
      return ids.stream().map(User::new).collect(Collectors.toList());
    };
    Consumer<List<User>> displayer = users -> {
      System.out.println("Running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    completableFuture.thenApply(fetchUsers).thenAccept(displayer);

    sleep(1_000);
  }

  @TimeMeasured
  public static void runAcceptAsynchronously() {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        sleep(200);
        System.out.println("Function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };
    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    completableFuture.thenCompose(fetchUsers)
            .thenAcceptAsync(displayer, executor);

    sleep(1_000);
    executor.shutdown();
  }

  @TimeMeasured
  public static void tryRunComposeAsynchronously() {
    ExecutorService executor1 = Executors.newSingleThreadExecutor();
    ExecutorService executor2 = Executors.newSingleThreadExecutor();

    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
      sleep(300);
      System.out.println("Function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        sleep(200);
        System.out.println("Function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers, executor2);
    };
    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    completableFuture.thenComposeAsync(fetchUsers, executor2)
            .thenAcceptAsync(displayer, executor1);

    sleep(1_000);
    executor1.shutdown();
    executor2.shutdown();
  }

  public static void main(String[] args) {

    tryRunComposeAsynchronously();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
