package org.dariaples.async;

import org.dariaples.TimeMeasured;
import org.dariaples.async.model.Email;
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

public class AsyncExampleMultibranchEither {

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
      sleep(250);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        System.out.println("Users function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };

    Function<List<Long>, CompletableFuture<List<Email>>> fetchEmails = ids -> {
      sleep(350);
      System.out.println("Emails function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<Email>> supplyEmails = () -> {
        System.out.println("Emails function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(Email::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyEmails);
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<User>> usersFuture = completableFuture.thenCompose(fetchUsers);
    CompletableFuture<List<Email>> emailsFuture = completableFuture.thenCompose(fetchEmails);

    usersFuture.thenAcceptBoth(
            emailsFuture,
            (users, emails) -> {
              System.out.println(users + " " + emails);
            }
    );

    sleep(1_000);
    executor1.shutdown();
    executor2.shutdown();
  }

  @TimeMeasured
  public static void tryAcceptEitherSynchronously() {
    ExecutorService executor1 = Executors.newSingleThreadExecutor();
    ExecutorService executor2 = Executors.newSingleThreadExecutor();

    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers1 = ids -> {
      sleep(150);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        System.out.println("Users function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
      sleep(5000);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        System.out.println("Users function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<User>> usersFuture1 = completableFuture.thenCompose(fetchUsers1);
    CompletableFuture<List<User>> usersFuture2 = completableFuture.thenCompose(fetchUsers2);

    usersFuture1.thenRun(() -> System.out.println("Users 1"));
    usersFuture2.thenRun(() -> System.out.println("Users 2"));

    usersFuture1.acceptEither(usersFuture2, displayer);

    sleep(6_000);
    executor1.shutdown();
    executor2.shutdown();
  }

  @TimeMeasured
  public static void tryAcceptEitherAsynchronously() {
    ExecutorService executor1 = Executors.newSingleThreadExecutor();
    ExecutorService executor2 = Executors.newSingleThreadExecutor();

    Supplier<List<Long>> supplyIDs = () -> {
      sleep(200);
      System.out.println("Supplier is running in thread: " + Thread.currentThread().getName());
      return Arrays.asList(1L, 2L, 3L);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers1 = ids -> {
      sleep(150);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        System.out.println("Users function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
      sleep(5000);
      System.out.println("Users function is running in thread: " + Thread.currentThread().getName());
      Supplier<List<User>> supplyUsers = () -> {
        System.out.println("Users function supplier is running in thread: " + Thread.currentThread().getName());
        return ids.stream().map(User::new).collect(Collectors.toList());
      };
      return CompletableFuture.supplyAsync(supplyUsers);
    };

    Consumer<List<User>> displayer = users -> {
      System.out.println("Consumer is running in thread: " + Thread.currentThread().getName());
      users.forEach(System.out::println);
    };
    CompletableFuture<List<Long>> completableFuture = CompletableFuture.supplyAsync(supplyIDs);
    CompletableFuture<List<User>> usersFuture1 = completableFuture.thenComposeAsync(fetchUsers1);
    CompletableFuture<List<User>> usersFuture2 = completableFuture.thenComposeAsync(fetchUsers2);

    usersFuture1.thenRun(() -> System.out.println("Users 1"));
    usersFuture2.thenRun(() -> System.out.println("Users 2"));

    usersFuture1.acceptEither(usersFuture2, displayer);

    sleep(6_000);
    executor1.shutdown();
    executor2.shutdown();
  }

  public static void main(String[] args) {

    tryAcceptEitherAsynchronously();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
