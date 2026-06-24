package com.toan;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.inject.Inject;
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
  void shouldSaveArticleConcurrently() {
    assertThatNoException().isThrownBy(this::verifySave);
  }

  @Test
  void shouldSaveArticleConcurrentlyBis() {
    assertThatNoException().isThrownBy(this::verifySaveBis);
  }

  @Test
  void shouldSaveArticleConcurrentlyByQuery() {
    assertThatNoException().isThrownBy(this::verifySaveArticle);
  }

  @Test
  void shouldMergeArticleConcurrently() {
    assertThatNoException().isThrownBy(this::verifyMerge);
  }

  @Test
  void shouldUpsertMultipleConcurrently() {
    assertThatNoException().isThrownBy(this::verifyUpsertMultiple);
  }

  private void verifySave() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 5; i++) {
        var future = executor.submit(upsertArticle(article));
        list.add(future);
      }
      Futures.joinOrThrow(list);
    }
  }

  private void verifySaveBis() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        var future = executorService.submit(upsertArticle(article));
        list.add(future);
      }
      Futures.joinOrThrow(list);
  }

  private void verifySaveArticle() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 5; i++) {
        var future = executor.submit(saveArticle(article));
        list.add(future);
      }
      Futures.joinOrThrow(list);
    }
  }

  private void verifySaveArticleBis() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      var future = executorService.submit(saveArticle(article));
      list.add(future);
    }
    Futures.joinOrThrow(list);
  }
  private void verifyMerge() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      var future = executorService.submit(mergeArticle(article));
      list.add(future);
    }
    Futures.joinOrThrow(list);
  }

  private void verifyUpsertMultiple() {
    Article article = new Article(1L, "article");
    List<Future<?>> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      var future = executorService.submit(upsertMultiple(article));
      list.add(future);
    }
    Futures.joinOrThrow(list);
  }

  private Runnable upsertArticle(Article article) {
    return () -> articleRepository.upsertArticle(article);
  }

  private Runnable saveArticle(Article article) {
    return () -> articleRepository.saveArticle(article);
  }

  private Runnable upsertMultiple(Article article) {
    return () -> articleRepository.upsertMultiple(List.of(article));
  }

  private Runnable mergeArticle(Article article) {
    return () -> articleRepository.mergeArticle(article);
  }
}
