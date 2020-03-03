package com.repository;

import com.mapper.AuthorMapper;
import com.model.ArticleData;
import com.model.Author;

import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class AuthorRepository {

    public Author getById(String id) {
        Author author = AuthorMapper.entityToGraphQL(ArticleData.authors.get(id));
        return author;
    }

}
