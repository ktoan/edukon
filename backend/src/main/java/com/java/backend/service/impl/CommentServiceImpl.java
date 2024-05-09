package com.java.backend.service.impl;

import com.java.backend.constant.AppConstants;
import com.java.backend.dto.CommentDto;
import com.java.backend.entity.BlogEntity;
import com.java.backend.entity.CommentEntity;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.CommentMapper;
import com.java.backend.repository.CommentRepository;
import com.java.backend.request.CommentRequest;
import com.java.backend.service.BlogService;
import com.java.backend.service.CommentService;
import com.java.backend.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	private final ContextUtil contextUtil;
	private final BlogService blogService;
	private final MessageSource messageSource;

	@Override
	public CommentEntity saveComment(CommentEntity comment) {
		return commentRepository.save(comment);
	}

	@Override
	public CommentDto createComment(CommentRequest commentRequest) {
		CommentEntity newComment = new CommentEntity();
		newComment.setComment(commentRequest.getComment());
		newComment.setBlog(blogService.findBlogEntityById(commentRequest.getBlogId()));
		newComment.setUser(contextUtil.loadUserFromContext());
		if (commentRequest.getParentId() != null) {
			CommentEntity parentComment = findCommentEntityById(commentRequest.getParentId());
			newComment.setParent(parentComment);
		}
		return commentMapper.toDto(saveComment(newComment));
	}

	@Override
	public CommentDto updateComment(Integer commentId, String comment) {
		CommentEntity updatedComment = findCommentEntityById(commentId);
		if (!Objects.equals(updatedComment.getUser().getId(), contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		updatedComment.setComment(comment);
		return commentMapper.toDto(saveComment(updatedComment));
	}

	@Override
	public void deleteComment(Integer commentId) {
		CommentEntity deletedComment = findCommentEntityById(commentId);
		if (!contextUtil.isAdminRole() || !Objects.equals(deletedComment.getBlog().getAuthor().getId(),
				contextUtil.loadUserFromContext().getId()) || !Objects.equals(deletedComment.getUser().getId(),
				contextUtil.loadUserFromContext().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}
		BlogEntity blog = deletedComment.getBlog();
		blog.removeComment(commentId);
		blogService.saveBlog(blog);
		CommentEntity parentComment = deletedComment.getParent();
		if (parentComment != null) {
			parentComment.removeChildComment(commentId);
			saveComment(parentComment);
		}
		commentRepository.delete(deletedComment);
	}

	@Override
	public CommentEntity findCommentEntityById(Integer commentId) {
		Optional<CommentEntity> comment = commentRepository.findById(commentId);
		if (comment.isEmpty()) {
			throw new NotFoundException(messageSource.getMessage("msg.not-found", new Object[]{"Comment id", commentId}, Locale.ENGLISH));
		}
		return comment.get();
	}
}
