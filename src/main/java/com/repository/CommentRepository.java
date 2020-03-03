package com.repository;

import com.model.Article;
import com.model.Comment;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CommentRepository {

    public List<Comment> getComments(Article article) {
        List<Comment> comments = article.getComment();
        return comments;
    }
}
