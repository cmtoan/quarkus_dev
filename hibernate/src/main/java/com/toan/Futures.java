package com.toan;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Futures {

  private Futures() {}

  public static void joinOrThrow(Collection<Future<?>> futures) {
    futures.forEach(Futures::joinOrThrowFailure);
  }

  static void joinOrThrowFailure(Future<?> future) {
    try {
      future.get();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Failed to wait for the thread to complete", e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
