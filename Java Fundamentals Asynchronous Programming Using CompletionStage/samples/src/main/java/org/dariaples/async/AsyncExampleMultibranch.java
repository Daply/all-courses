package org.dariaples.async;

import org.dariaples.TimeMeasured;
import org.dariaples.async.model.Email;
import org.dariaples.async.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AsyncExampleMultibranch {

  @TimeMeasured
  public static void tryRunComposeAsynchronously() {
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
