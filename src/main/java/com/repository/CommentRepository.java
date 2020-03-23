package com.repository;

import com.entities.CommentEntity;
import com.mapper.CommentMapper;
import com.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CommentRepository {

    public List<CommentEntity> getComments(Article article) {

        return article.getComments()
                .stream()
                .map(CommentMapper::graphQLToEntity)
                .collect(Collectors.toList());
    }
}
