package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.model.Article;
import com.model.Author;
import com.model.Comment;
import com.repository.AuthorRepository;
import com.repository.CommentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArticleResolver implements GraphQLResolver<Article> {

    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public ArticleResolver(AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    public CompletableFuture<Author> author(Article article) {
        return CompletableFuture.supplyAsync(() -> {
            return authorRepository.getById(article.getAuthor().getId());
        });
    }

    public List<Comment> comment(Article article) {
        return commentRepository.getComments(article);
    }


}
