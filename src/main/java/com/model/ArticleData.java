package com.model;


import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Arrays.asList;


public class ArticleData {

    private static Author author1 = new Author(
            "1",
            "Justin",
            "Albano",
            asList("Best Practices for REST API Error Handling", "Causes and Avoidance of java.lang.VerifyError"),
            asList("GitHub", "Twitter")
    );

    private static Author author2 = new Author(
            "2",
            "Michael",
            "Krimgen",
            asList("A Guide to Java HashMap"),
            asList("GitHub")
    );

    private static Comment comment1 = new Comment(
            "1",
            "First comment",
            "Ion Popescu"
    );

    private static Comment comment2 = new Comment(
            "2",
            "Second comment",
            "Gigel"
    );

    private static Comment comment3 = new Comment(
            "3",
            "There are no comments yet.",
            "Admin"
    );

    private static Article article1 = new Article(
            "1",
            "Best Practices for REST API Error Handling",
            asList("Arhitecture", "REST"),
            "REST is a stateless architecture in which clients can access and manipulate resources on a server.",
            author1,
            LocalDate.parse("2018-10-22"),
            LocalDate.parse("2019-01-11"),
            3,
            "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
            asList(comment1)

    );

    private static Article article2 = new Article(
            "2",
            "Causes and Avoidance of java.lang.VerifyError",
            asList("Java", "JVM"),
            "In this tutorial, we'll look at the cause of java.lang.VerifyError errors and multiple ways to avoid it.",
            author1,
            LocalDate.parse("2019-01-12"),
            LocalDate.parse("2019-11-18"),
            2,
            "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
            asList(comment2)

    );

    private static Article article3 = new Article(
            "3",
            "A Guide to Java HashMap",
            asList("Java"),
            "Let's first look at what it means that HashMap is a map. " +
                    "A map is a key-value mapping, which means that every key is mapped to exactly " +
                    "one value and that we can use the key to retrieve the corresponding value from a map.",
            author2,
            LocalDate.parse("2019-01-12"),
            LocalDate.parse("2019-11-18"),
            4,
            "https://res.cloudinary.com/fittco/image/upload/w_1920,f_auto/ky8jdsfofdkpolpac2yw.jpg",
            asList(comment3)
    );

    private static Map<String, Article> articles = new LinkedHashMap<>();

    static {
        articles.put("1", article1);
        articles.put("2", article2);
        articles.put("3", article3);
    }

    public static Object getArticleData(String key) {
        if (articles.get(key) != null) {
            return articles.get(key);
        }
        return null;
    }



}
