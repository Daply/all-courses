package org.dariaples.composition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureCompositionExample {

  private static DatabaseManager databaseManager = new DatabaseManager();

  public static void runFetchingUsersSynchronously() {
    Supplier<List<Long>> getUserIds = () -> {
      System.out.println("Thread: " + Thread.currentThread().getName());
      return databaseManager.getUserIds();
    };
    Function<List<Long>, List<User>> fetchUsers = ids -> {
      System.out.println("Thread: " + Thread.currentThread().getName());
      return databaseManager.selectUsers(ids);
    };

    CompletableFuture<List<User>> completableFuture =
            CompletableFuture.supplyAsync(getUserIds).thenApply(fetchUsers);
    List<User> users = completableFuture.join();
    users.forEach(u -> System.out.println(u.toString()));
  }

  public static void runFetchingUsersAsynchronously() {
    Supplier<List<Long>> getUserIds = () -> {
      System.out.println("Thread: " + Thread.currentThread().getName());
      return databaseManager.getUserIds();
    };

    Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
      System.out.println("Thread: " + Thread.currentThread().getName());
      return CompletableFuture.supplyAsync(() -> {
        System.out.println("Thread: " + Thread.currentThread().getName());
        return databaseManager.selectUsers(ids);
      });
    };

    CompletableFuture<List<User>> completableFuture =
            CompletableFuture.supplyAsync(getUserIds).thenCompose(fetchUsers);
    List<User> users = completableFuture.join();
    users.forEach(u -> System.out.println(u.toString()));
  }

  public static void main(String[] args) {
    runFetchingUsersAsynchronously();
  }
}
