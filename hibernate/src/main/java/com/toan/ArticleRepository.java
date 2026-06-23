package com.toan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.StatelessSession;

@ApplicationScoped
public class ArticleRepository {

  @Inject
  StatelessSession statelessSession;

  @Transactional
  public void upsertArticle(Article article) {
    statelessSession.upsert(article);
  }
}
