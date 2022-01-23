package org.dariaples.async;

import org.dariaples.TimeMeasured;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClientExample {

  @TimeMeasured
  public static void runHttpClient() {
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpRequest httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://www.amazon.com"))
            .build();
    CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient
            .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

    int length = httpResponseFuture.join().body().length();
    System.out.println(length);
  }

  @TimeMeasured
  public static void runHttpClientAsync() {
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpRequest httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://www.amazon.com"))
            .build();
    CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient
            .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

    httpResponseFuture.thenAccept(
              response -> {
                String body = response.body();
                System.out.println("Length = " + body.length() + " Thread: " + Thread.currentThread().getName());
              }
            )
            .thenRun(() -> System.out.println("Finished"))
    .join();
  }

  @TimeMeasured
  public static void runHttpClientAsyncWithExecutor() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpRequest httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://www.amazon.com"))
            .build();
    CompletableFuture<HttpResponse<String>> httpResponseFuture = httpClient
            .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

    httpResponseFuture.thenAcceptAsync(
            response -> {
              String body = response.body();
              System.out.println("Length = " + body.length() + " Thread: " + Thread.currentThread().getName());
            }, executor
    )
            .thenRun(() -> System.out.println("Finished"))
            .join();

    executor.shutdown();
  }

  @TimeMeasured
  public static void runHttpClientAsyncWithTrigger() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpRequest httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("https://www.amazon.com"))
            .build();

    CompletableFuture<Void> start = new CompletableFuture<>();

    CompletableFuture<HttpResponse<String>> httpResponseFuture =
            start.thenCompose(nul ->
                    httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            );

    httpResponseFuture.thenAcceptAsync(
            response -> {
              String body = response.body();
              System.out.println("Length = " + body.length() + " Thread: " + Thread.currentThread().getName());
            }, executor
    )
            .thenRun(() -> System.out.println("Finished"));

    start.complete(null);
    sleep(1_000);

    executor.shutdown();
  }

  public static void main(String[] args) {
    runHttpClientAsyncWithTrigger();
  }

  private void log(String info) {
    System.out.println(info);
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }
  }
}
