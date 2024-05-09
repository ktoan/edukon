package com.java.backend.service;

import com.java.backend.dto.CommentDto;
import com.java.backend.entity.CommentEntity;
import com.java.backend.request.CommentRequest;

public interface CommentService {
    CommentEntity saveComment(CommentEntity comment);

    CommentDto createComment(CommentRequest commentRequest);

    CommentDto updateComment(Integer commentId, String comment);

    void deleteComment(Integer commentId);

    CommentEntity findCommentEntityById(Integer commentId);
}
