package com.java.backend.service;

import com.java.backend.dto.BlogDto;
import com.java.backend.entity.BlogEntity;
import com.java.backend.request.BlogRequest;

import java.util.List;

public interface BlogService {
    BlogEntity saveBlog(BlogEntity blog);
    List<BlogDto> findAllBlogs(String keyword);
    BlogDto getBlogById(Integer blogId);

    BlogDto createBlog(BlogRequest blogRequest);

    BlogDto updateBlog(Integer blogId, BlogRequest blogRequest);

    void deleteBlog(Integer blogId);

    BlogEntity findBlogEntityById(Integer blogId);
}
