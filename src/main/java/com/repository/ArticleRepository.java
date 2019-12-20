package com.repository;

import com.mapper.Mapper;
import com.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleRepository {

    public List<Article> getAllArticlesByTitle(SearchCriteria filter) {
        String titlePattern = filter.getTitleContains();

        String descriptionCondition = null;
        List<Article> foundArticles = new ArrayList<>();

        if (titlePattern != null && !titlePattern.isEmpty()) {
            descriptionCondition = "\\b" + titlePattern + "\\b";

            Pattern pattern = Pattern.compile(descriptionCondition, Pattern.CASE_INSENSITIVE);
            List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

            for (Article article : allArticles) {
                Matcher matcher = pattern.matcher(article.getTitle());
                if (matcher.find()) {
                    System.out.print("Start index: " + matcher.start());
                    System.out.print(" End index: " + matcher.end() + " ");
                    System.out.println(matcher.group());
                    foundArticles.add(article);
                }
            }
        }
        return foundArticles;
    }


    public List<Article> getAllByTag(SearchCriteria filter) {

        List<String> searchPattern = filter.getTagsContains();
        String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (String t : searchPattern) {
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

    public List<Article> getAllByAuthor(SearchCriteria filter) {

        String firstNamePattern = filter.getAuthorFirstName();
        String lastNamePattern = filter.getAuthorLastName();
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (Article article : allArticles) {
            if (article.getAuthor().getFirstName().equals(firstNamePattern) && article.getAuthor().getLastName().equals(lastNamePattern)) {
                foundArticles.add(article);
            }
        }
        return foundArticles;
    }

    public List<Article> getAllByDate(SearchCriteria filter, String compareValue) {

        LocalDate datePattern = filter.getDate();
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (Article article : allArticles) {

            if (compareValue.equals("before") && datePattern.isAfter(article.getCreationDate())) {
                foundArticles.add(article);
            } else if (compareValue.equals("after") && datePattern.isBefore(article.getCreationDate())) {
                foundArticles.add(article);
            }
        }
        return foundArticles;
    }

    public List<Article> getAllArticles() {
        return ArticleData.articles.values().stream().collect(Collectors.toList());
    }

    public Article getArticleById(SearchCriteria filter) {
        String articleId = filter.getArticleId();

        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

        for (Article article : allArticles) {
            if (article.getId().equals(articleId)) {
                return article;
            }
        }
        return null;
    }

    public Article saveArticle(ArticleDTO article, String firstName) {
        ArticleDTO newArticle = new ArticleDTO();
        Random random = new Random();

        newArticle.setId(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        newArticle.setTitle(article.getTitle());
        newArticle.setTags(article.getTags());
        newArticle.setContent(article.getContent());
        newArticle.setAuthor(Mapper.authorToAuthorDTO(getByFirstName(firstName)));
        newArticle.setCreationDate(LocalDate.now());
        newArticle.setReadingTime(article.getReadingTime());
        newArticle.setImage(article.getImage());

        try (PrintWriter fileWriter = new PrintWriter("C:\\Work\\graphql_undertow\\src\\main\\resources\\articles.txt", "UTF-8")) {
            fileWriter.write(Mapper.articleDTOToArticle(newArticle).toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return Mapper.articleDTOToArticle(newArticle);
    }

    public Author getByFirstName(String firstName) {
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

        for (Article article : allArticles) {
            if (article.getAuthor().getFirstName().equals(firstName)) {
                return article.getAuthor();
            }
        }
        return null;
    }


}
