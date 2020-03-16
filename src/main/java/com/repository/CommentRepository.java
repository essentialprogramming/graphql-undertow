package com.repository;

import com.model.Article;
import com.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {

    public List<Comment> getComments(Article article) {
        List<Comment> comments = article.getComments();
        return comments;
    }
}
