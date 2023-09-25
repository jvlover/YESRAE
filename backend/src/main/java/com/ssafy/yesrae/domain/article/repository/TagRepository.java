package com.ssafy.yesrae.domain.article.repository;

import com.ssafy.yesrae.domain.article.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
