package com.repository;

import com.mapper.Mapper;
import com.model.*;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleRepository {

    public List<Article> getAllArticlesByTitle(String filter) {

        String descriptionCondition = null;
        List<Article> foundArticles = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            descriptionCondition = "\\b" + filter + "\\b";

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


    public List<Article> getAllByTag(List<String> tags) {

        String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
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

    public List<Article> getAllByAuthor(String firstName, String lastName) {

        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (Article article : allArticles) {
            if (article.getAuthor().getFirstName().equals(firstName) && article.getAuthor().getLastName().equals(lastName)) {
                foundArticles.add(article);
            }
        }
        return foundArticles;
    }

    public List<Article> getAllByDate(String date, String compareValue) {

        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<Article> foundArticles = new ArrayList<>();

        for (Article article : allArticles) {
            LocalDate localDate = LocalDate.parse(date);
            if (compareValue.equals("before") && localDate.isAfter(article.getCreationDate())) {
                foundArticles.add(article);
            } else if (compareValue.equals("after") && localDate.isBefore(article.getCreationDate())) {
                foundArticles.add(article);
            }
        }
        return foundArticles;
    }

    public List<Article> getAllArticles() {
        return ArticleData.articles.values().stream().collect(Collectors.toList());
    }

    public Article getArticleById(String id) {

        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

        for (Article article : allArticles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public Article saveArticle(ArticleDTO article, String firstName) throws IOException {
        ArticleDTO newArticle = new ArticleDTO();
        Random random = new Random();
        BufferedWriter writer = null;
        FileWriter fileWriter;
        Path path = Paths.get("src\\main\\resources\\articles.txt");

        newArticle.setId(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        newArticle.setTitle(article.getTitle());
        newArticle.setTags(article.getTags());
        newArticle.setContent(article.getContent());
        newArticle.setAuthor(Mapper.authorToAuthorDTO(getByFirstName(firstName)));
        newArticle.setCreationDate(LocalDate.now());
        newArticle.setReadingTime(article.getReadingTime());
        newArticle.setImage(article.getImage());

        try {
            fileWriter = new FileWriter(String.valueOf(path), true);
            writer = new BufferedWriter(fileWriter);

            writer.write(Mapper.articleDTOToArticle(newArticle).toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return Mapper.articleDTOToArticle(newArticle);
    }

    private Author getByFirstName(String firstName) {
        List<Article> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

        for (Article article : allArticles) {
            if (article.getAuthor().getFirstName().equals(firstName)) {
                return article.getAuthor();
            }
        }
        return null;
    }


}
