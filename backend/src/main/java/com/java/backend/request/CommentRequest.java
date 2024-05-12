package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
	@Required(fieldName = "Comment")
	@JsonProperty("comment")
	private String comment;
	@Required(fieldName = "Blog")
	@JsonProperty("blog_id")
	private Integer blogId;
	@JsonProperty("parent_id")
	private Integer parentId;
}
