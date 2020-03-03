package com.mapper;

import com.model.Author;
import com.model.AuthorEntity;


public class AuthorMapper {

    public static Author entityToGraphQL(AuthorEntity entity) {
        Author author = new Author();
        author.setId(entity.getId());
        author.setFirstName(entity.getFirstName());
        author.setLastName(entity.getLastName());
        author.setContactLinks(entity.getContactLinks());

        return author;
    }


}
