package com.toan;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThatNoException;

@QuarkusTest
class ArticleTest {

  @Inject
  ArticleRepository articleRepository;

  @VirtualThreads
  ExecutorService executorService;

  @Test
  @Transactional
  void shouldSaveArticleConcurrently() {
    assertThatNoException().isThrownBy(this::verifySave);
  }

  @Test
  @Transactional
  void shouldSaveArticleConcurrentlyBis() {
    assertThatNoException().isThrownBy(this::verifySaveBis);
  }

  private void verifySave() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 10; i++) {
        var future = executor.submit(() -> {
          articleRepository.upsertArticle(article);
        });
        list.add(future);
      }
      Futures.joinOrThrow(list);
    }
  }

  private void verifySaveBis() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        var future = executorService.submit(() -> {
          articleRepository.upsertArticle(article);
        });
        list.add(future);
      }
      Futures.joinOrThrow(list);
  }
}
