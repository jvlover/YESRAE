package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
