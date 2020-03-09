package com.repository;

import com.mapper.AuthorMapper;
import com.model.ArticleData;
import com.model.Author;
import org.springframework.stereotype.Repository;



@Repository
public class AuthorRepository {

    public Author getById(String id) {
        Author author = AuthorMapper.entityToGraphQL(ArticleData.authors.get(id));
        return author;
    }

}
