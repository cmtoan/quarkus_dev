package com.toan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.StatelessSession;

import java.util.List;

@ApplicationScoped
public class ArticleRepository {

  @Inject
  StatelessSession statelessSession;

  @Inject
  EntityManager entityManager;

  @Transactional
  public void upsertArticle(Article article) {
    statelessSession.upsert(article);
  }

  @Transactional
  public void saveArticle(Article article) {
    statelessSession.createNativeQuery("""
        INSERT INTO Article (id, name) VALUES (:id, :name)
                ON CONFLICT (id) DO UPDATE SET name = EXCLUDED.name
        """)
      .setParameter("id", article.getId())
      .setParameter("name", article.getName())
      .executeUpdate();
  }

  @Transactional
  public void mergeArticle(Article article) {
    entityManager.merge(article);
  }

  @Transactional
  public void upsertMultiple(List<Article> articles) {
    statelessSession.upsertMultiple(articles);
  }
}
