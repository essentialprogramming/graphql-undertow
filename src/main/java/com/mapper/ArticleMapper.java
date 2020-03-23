package com.mapper;

import com.entities.ArticleEntity;
import com.model.*;

import java.util.stream.Collectors;

public class ArticleMapper {

    public static Article entityToGraphQL(ArticleEntity entity) {
        Article article = new Article();

        article.setId(entity.getId());
        article.setTitle(entity.getTitle());
        article.setTags(entity.getTags());
        article.setContent(entity.getContent());
        article.setAuthor(entity.getAuthor() != null ? AuthorMapper.entityToGraphQL(entity.getAuthor()) : null);
        article.setCreationDate(entity.getCreationDate());
        article.setLastModified(entity.getLastModified());
        article.setReadingTime(entity.getReadingTime());
        article.setImage(entity.getImage());
        article.setComments(entity.getComments() != null ? entity.getComments().stream().map(CommentMapper::entityToGraphQL).collect(Collectors.toList()) : null);

        return article;
    }
}

