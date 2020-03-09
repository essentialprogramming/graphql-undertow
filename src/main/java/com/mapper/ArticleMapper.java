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
        article.setAuthor(AuthorMapper.entityToGraphQL(entity.getAuthor()));
        article.setCreationDate(entity.getCreationDate());
        article.setLastModified(entity.getLastModified());
        article.setReadingTime(entity.getReadingTime());
        article.setImage(entity.getImage());
        article.setComment(entity.getComment().stream().map(CommentMapper::entityToGraphQL).collect(Collectors.toList()));

        return article;
    }

    public static Article articleEntityToGraphQL(ArticleEntity entity) {
        Article article = new Article();

        article.setId(entity.getId());
        article.setTitle(entity.getTitle());
        article.setTags(entity.getTags());
        article.setContent(entity.getContent());
        article.setCreationDate(entity.getCreationDate());
        article.setLastModified(entity.getLastModified());
        article.setReadingTime(entity.getReadingTime());
        article.setImage(entity.getImage());

        return article;
    }


}

