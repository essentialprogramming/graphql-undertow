package com.mapper;

import com.model.Article;
import com.model.ArticleDTO;
import com.model.Author;
import com.model.AuthorDTO;

public class Mapper {

    public static Article articleDTOToArticle(ArticleDTO articleDTO) {
        Article article = new Article();

        article.setId(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setTags(articleDTO.getTags());
        article.setContent(articleDTO.getContent());
        article.setAuthor(authorDTOToAuthor(articleDTO.getAuthor()));
        article.setCreationDate(articleDTO.getCreationDate());
        article.setLastModified(articleDTO.getLastModified());
        article.setReadingTime(articleDTO.getReadingTime());
        article.setImage(articleDTO.getImage());
        article.setComment(articleDTO.getComment());

        return article;
    }

    private static Author authorDTOToAuthor(AuthorDTO authorDTO) {
        Author author = new Author();

        author.setId(authorDTO.getId());
        author.setArticles(authorDTO.getArticles());
        author.setContactLinks(authorDTO.getContactLinks());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());

        return author;
    }

    public static AuthorDTO authorToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setId(author.getId());
        authorDTO.setArticles(author.getArticles());
        authorDTO.setContactLinks(author.getContactLinks());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());

        return authorDTO;
    }
}

