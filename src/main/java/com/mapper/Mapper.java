package com.mapper;

import com.model.Article;
import com.model.ArticleInput;
import com.model.Author;
import com.model.AuthorInput;

public class Mapper {

    public static Article articleDTOToArticle(ArticleInput articleInput) {
        Article article = new Article();

        article.setId(articleInput.getId());
        article.setTitle(articleInput.getTitle());
        article.setTags(articleInput.getTags());
        article.setContent(articleInput.getContent());
        article.setAuthor(authorDTOToAuthor(articleInput.getAuthor()));
        article.setCreationDate(articleInput.getCreationDate());
        article.setLastModified(articleInput.getLastModified());
        article.setReadingTime(articleInput.getReadingTime());
        article.setImage(articleInput.getImage());
        article.setComment(articleInput.getComment());

        return article;
    }

    private static Author authorDTOToAuthor(AuthorInput authorInput) {
        Author author = new Author();

        author.setId(authorInput.getId());
        author.setArticles(authorInput.getArticles());
        author.setContactLinks(authorInput.getContactLinks());
        author.setFirstName(authorInput.getFirstName());
        author.setLastName(authorInput.getLastName());

        return author;
    }

    public static AuthorInput authorToAuthorDTO(Author author) {
        AuthorInput authorInput = new AuthorInput();

        authorInput.setId(author.getId());
        authorInput.setArticles(author.getArticles());
        authorInput.setContactLinks(author.getContactLinks());
        authorInput.setFirstName(author.getFirstName());
        authorInput.setLastName(author.getLastName());

        return authorInput;
    }
}

