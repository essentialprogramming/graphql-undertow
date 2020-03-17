package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.ArticleData;
import com.model.Author;
import com.model.Comment;
import com.repository.AuthorRepository;
import com.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArticleResolver implements GraphQLResolver<Article> {

    private final AuthorRepository authorRepository;
    private final CommentRepository commentRepository;

    public ArticleResolver(AuthorRepository authorRepository, CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    public CompletableFuture<Author> author(Article article) {

//         Client client = ClientBuilder.newClient();
//         client
//                .target(REST_URI)
//                .path(String.valueOf(id))
//                .request(MediaType.APPLICATION_JSON)
//                .get(Map.class);
        return CompletableFuture.supplyAsync(() -> authorRepository.getById(article.getAuthor().getId()));
    }

    public List<Comment> comment(Article article) {
        return commentRepository.getComments(article);
    }

    public List<Article> tags(Article articleModel,List<String> tags) {
        String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
        List<Article> allArticles = ArticleData.articles.values()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (String t : tags) {
            String regex = String.format(REGEX_FIND_WORD, Pattern.quote(t));

            for (Article article : allArticles) {
                for (String tag : article.getTags()) {
                    if (tag.matches(regex) && !foundArticles.contains(article)) {
                        foundArticles.add(article);
                    }
                }
            }
        }
        return foundArticles;
    }


}
