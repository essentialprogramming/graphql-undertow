package com.repository;

import com.model.*;
import org.springframework.stereotype.Repository;

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

@Repository
public class ArticleRepository {

    public List<ArticleEntity> allByTitle(String filter) {

        String descriptionCondition = null;
        List<ArticleEntity> foundArticles = new ArrayList<>();

        if (filter != null && !filter.isEmpty()) {
            descriptionCondition = "\\b" + filter + "\\b";

            Pattern pattern = Pattern.compile(descriptionCondition, Pattern.CASE_INSENSITIVE);
            List<ArticleEntity> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

            for (ArticleEntity article : allArticles) {
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


    public List<ArticleEntity> allByTag(List<String> tags) {

        String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";
        List<ArticleEntity> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<ArticleEntity> foundArticles = new ArrayList<>();

        for (String t : tags) {
            String regex = String.format(REGEX_FIND_WORD, Pattern.quote(t));

            for (ArticleEntity article : allArticles) {
                for (String tag : article.getTags()) {
                    if (tag.matches(regex) && !foundArticles.contains(article)) {
                        foundArticles.add(article);
                    }
                }
            }
        }
        return foundArticles;
    }

    public List<ArticleEntity> allByAuthor(String firstName, String lastName) {

        List<ArticleEntity> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<ArticleEntity> foundArticles = new ArrayList<>();

        for (ArticleEntity article : allArticles) {
            if (article.getAuthor().getFirstName().equals(firstName) && article.getAuthor().getLastName().equals(lastName)) {
                foundArticles.add(article);
            }
        }
        return foundArticles;
    }

    public List<ArticleEntity> allBetweenDates(String startDate, String endDate) {

        List<ArticleEntity> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());
        List<ArticleEntity> foundArticles = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);

        for (ArticleEntity article : allArticles) {

            if (endDate != null) {
                LocalDate end = LocalDate.parse(endDate);
                if (article.getCreationDate().isAfter(start) && article.getCreationDate().isBefore(end)) {
                    foundArticles.add(article);
                }
            } else {
                if (article.getCreationDate().isAfter(start)) {
                    foundArticles.add(article);
                }
            }
        }
        return foundArticles;
    }

    public List<ArticleEntity> allArticles() {
        return ArticleData.articles.values().stream().collect(Collectors.toList());
    }

    public ArticleEntity getById(String id) {

        List<ArticleEntity> allArticles = ArticleData.articles.values().stream().collect(Collectors.toList());

        for (ArticleEntity article : allArticles) {
            if (article.getId().equals(id)) {
                return article;
            }
        }
        return null;
    }

    public ArticleEntity saveArticle(ArticleInput article) throws IOException {
        ArticleEntity newArticle = new ArticleEntity();
        Random random = new Random();
        BufferedWriter writer = null;
        FileWriter fileWriter;
        Path path = Paths.get("src\\main\\resources\\articles.txt");

        newArticle.setId(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
        newArticle.setTitle(article.getTitle());
        newArticle.setTags(article.getTags());
        newArticle.setContent(article.getContent());
        newArticle.setCreationDate(LocalDate.now());
        newArticle.setReadingTime(article.getReadingTime());
        newArticle.setImage(article.getImage());

        try {
            fileWriter = new FileWriter(String.valueOf(path), true);
            writer = new BufferedWriter(fileWriter);

            writer.write(newArticle.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return newArticle;
    }


}
