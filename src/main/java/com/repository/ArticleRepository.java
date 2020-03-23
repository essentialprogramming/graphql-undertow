package com.repository;

import com.entities.ArticleEntity;
import com.model.ArticleInput;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class ArticleRepository {

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
