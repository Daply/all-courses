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

public class TriggerExample {

  @TimeMeasured
  public static void tryTrigger() {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      return Arrays.asList(1L, 2L, 3L);
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

    CompletableFuture<Void> start = new CompletableFuture<>();

    CompletableFuture<List<Long>> supply = start.thenApply(nul -> supplyIDs.get());
    CompletableFuture<List<User>> fetch = supply.thenApply(fetchUsers);
    CompletableFuture<Void> display = fetch.thenAccept(displayer);

    start.completeAsync(() -> null, executor);

    sleep(1_000);
    executor.shutdown();
  }

  public static void main(String[] args) {
    tryTrigger();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
