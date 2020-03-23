package com.repository;

import com.entities.AuthorEntity;
import org.springframework.stereotype.Repository;



@Repository
public class AuthorRepository {

    public AuthorEntity getById(String id) {
        return ArticleData.authors.get(id);
    }

}
