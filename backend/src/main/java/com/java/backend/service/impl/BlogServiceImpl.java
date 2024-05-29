package com.java.backend.service.impl;

import com.java.backend.dto.BlogDto;
import com.java.backend.entity.BlogEntity;
import com.java.backend.entity.UserEntity;
import com.java.backend.exception.NotAccessException;
import com.java.backend.exception.NotFoundException;
import com.java.backend.mapper.BlogMapper;
import com.java.backend.repository.BlogRepository;
import com.java.backend.request.BlogRequest;
import com.java.backend.service.BlogService;
import com.java.backend.service.CategoryService;
import com.java.backend.service.UserService;
import com.java.backend.util.ContextUtil;
import com.java.backend.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
	private final BlogRepository blogRepository;
	private final UserService userService;
	private final CategoryService categoryService;
	private final BlogMapper blogMapper;
	private final FileUtil fileUtil;
	private final ContextUtil contextUtil;
	private final MessageSource messageSource;

	@Override
	public List<BlogDto> findAllBlogs(String keyword) {
		List<BlogEntity> blogs;
		if (StringUtils.isEmpty(keyword)) {
			blogs = blogRepository.findAll();
		} else {
			blogs = blogRepository.findByTitleContainingOrCategoryNameContainingOrAuthorNameContaining(keyword, keyword,
					keyword);
		}
		return blogs.stream().map(blogMapper::toDto).toList();
	}

	@Override
	public BlogEntity saveBlog(BlogEntity blog) {
		return blogRepository.save(blog);
	}

	@Override
	public BlogDto createBlog(BlogRequest blogRequest) {
		BlogEntity newBlog = new BlogEntity();
		newBlog.setTitle(blogRequest.getTitle());
		newBlog.setPreDescription(blogRequest.getPreDescription());
		newBlog.setContent(blogRequest.getContent());
		newBlog.setApproved(contextUtil.isAdminRole());
		newBlog.setCategory(categoryService.findCategoryEntityById(blogRequest.getCategoryId()));
		newBlog.setAuthor(contextUtil.loadUserFromContext());
		newBlog.setThumbnail(fileUtil.uploadImage(blogRequest.getThumbnail(), "blog"));
		return blogMapper.toDto(saveBlog(newBlog));
	}

	@Override
	public BlogDto updateBlog(Integer blogId, BlogRequest blogRequest) {
		BlogEntity updatedBlog = findBlogEntityById(blogId);

		UserEntity currentUser = contextUtil.loadUserFromContext();
		if (!contextUtil.isAdminRole() || !Objects.equals(currentUser.getId(), updatedBlog.getAuthor().getId())) {
			throw new NotAccessException(messageSource.getMessage("msg.not-permission", null, Locale.ENGLISH));
		}

		if (!StringUtils.isEmpty(blogRequest.getTitle())) {
			updatedBlog.setTitle(blogRequest.getTitle());
		}
		if (!StringUtils.isEmpty(blogRequest.getPreDescription())) {
			updatedBlog.setPreDescription(blogRequest.getPreDescription());
		}
		if (!StringUtils.isEmpty(blogRequest.getContent())) {
			updatedBlog.setContent(blogRequest.getContent());
		}
		if (blogRequest.getThumbnail() != null) {
			updatedBlog.setThumbnail(fileUtil.uploadImage(blogRequest.getThumbnail(), "blog"));
		}
		if (blogRequest.getCategoryId() != null) {
			updatedBlog.setCategory(categoryService.findCategoryEntityById(blogRequest.getCategoryId()));
		}
		return blogMapper.toDto(saveBlog(updatedBlog));
	}

	@Override
	public void deleteBlog(Integer blogId) {
		BlogEntity deletedBlog = findBlogEntityById(blogId);
		UserEntity author = deletedBlog.getAuthor();
		author.removeBlog(blogId);
		userService.saveUser(author);
		blogRepository.delete(deletedBlog);
	}

	@Override
	public BlogEntity findBlogEntityById(Integer blogId) {
		Optional<BlogEntity> blog = blogRepository.findById(blogId);
		if (blog.isEmpty()) {
			throw new NotFoundException(
					messageSource.getMessage("msg.not-found", new Object[]{"Blog id", blogId}, Locale.ENGLISH));
		}
		return blog.get();
	}

	@Override
	public BlogDto getBlogById(Integer blogId) {
		return blogMapper.toDto(findBlogEntityById(blogId));
	}
}
