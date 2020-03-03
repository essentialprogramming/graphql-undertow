package com.model;


import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;


public class ArticleData {

    private static ArticleEntity article1;
    private static ArticleEntity article2;
    private static ArticleEntity article3;
    private static AuthorEntity author1;
    private static AuthorEntity author2;

    private static CommentEntity comment1 = new CommentEntity(
            "1",
            "First comment",
            "Ion Popescu"
    );

    private static CommentEntity comment2 = new CommentEntity(
            "2",
            "Second comment",
            "Gigel"
    );

    private static CommentEntity comment3 = new CommentEntity(
            "3",
            "There are no comments yet.",
            "Admin"
    );

    static {

        article1 = new ArticleEntity(
                "1",
                "Best Practices for REST API Error Handling",
                asList("Architecture", "REST"),
                "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
                LocalDate.parse("2018-10-22"),
                LocalDate.parse("2019-01-11"),
                3,
                "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                asList(comment1)

        );

        article2 = new ArticleEntity(
                "2",
                "Causes and Avoidance of java.lang.VerifyError",
                asList("Java", "JVM"),
                "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
                LocalDate.parse("2019-01-12"),
                LocalDate.parse("2019-11-18"),
                2,
                "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                asList(comment2)

        );

        article3 = new ArticleEntity(
                "3",
                "A Guide to Java HashMap",
                asList("Java"),
                "Let's first look at what it means that HashMap is a map. " +
                        "A map is a key-value mapping, which means that every key is mapped to exactly " +
                        "one value and that we can use the key to retrieve the corresponding value from a map.",
                LocalDate.parse("2019-05-22"),
                LocalDate.parse("2019-11-18"),
                4,
                "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
                asList(comment3)
        );

        author1 = new AuthorEntity(
                "1",
                "Justin",
                "Albano",
                asList(article1, article2),
                asList("GitHub", "Twitter")
        );

        author2 = new AuthorEntity(
                "2",
                "Michael",
                "Krimgen",
                asList(article3),
                asList("GitHub")
        );

        article1.setAuthor(author1);
        article2.setAuthor(author1);
        article3.setAuthor(author2);
    }

    public static Map<String, ArticleEntity> articles = new LinkedHashMap<>();
    public static Map<String, AuthorEntity> authors = new LinkedHashMap<>();
    public static Map<String, CommentEntity> comments = new LinkedHashMap<>();

    static {
        articles.put("1", article1);
        articles.put("2", article2);
        articles.put("3", article3);

        authors.put("1", author1);
        authors.put("2", author2);

        comments.put("1", comment1);
        comments.put("2", comment2);
        comments.put("3", comment3);

    }

    public static Object getArticleData(String key) {
        if (articles.get(key) != null) {
            return articles.get(key);
        }
        return null;
    }


}
