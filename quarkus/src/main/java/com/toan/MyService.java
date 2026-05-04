package com.toan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@ApplicationScoped
public class MyService {

  @Inject
  FakeRestClient fakeRestClient;
  private static final Semaphore semaphore = new Semaphore(3);

  public void executeAll() {
    try (ExecutorService executorService =
           Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory())) {
      var future1 = CompletableFuture.runAsync(() -> wrapRunnable(fakeRestClient::method1),
        executorService);
      var future2 = CompletableFuture.runAsync(() -> wrapRunnable(fakeRestClient::method2),
        executorService);
      CompletableFuture.allOf(future1, future2).join();
    }
  }

  private void wrapRunnable(Runnable runnable) {
    try {
      semaphore.acquire();
      runnable.run();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      semaphore.release();
    }
  }
}
