package com.mapper;

import com.model.Comment;
import com.entities.CommentEntity;

public class CommentMapper {

    public static Comment entityToGraphQL(CommentEntity entity) {
        Comment comment = new Comment();
        comment.setId(entity.getId());
        comment.setCommentAuthor(entity.getCommentAuthor());
        comment.setText(entity.getText());

        return comment;
    }

    public static CommentEntity graphQLToEntity(Comment comment){
        CommentEntity entity = new CommentEntity();
        entity.setId(comment.getId());
        entity.setCommentAuthor(comment.getCommentAuthor());
        entity.setText(comment.getText());
        return entity;
    }
}
