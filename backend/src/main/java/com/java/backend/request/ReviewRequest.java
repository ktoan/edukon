package com.java.backend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.backend.annotation.Required;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
	@Required(fieldName = "Comment")
	@JsonProperty("comment")
	private String comment;
	@Required(fieldName = "Rating")
	@JsonProperty("rating")
	private Double rating;
	@Required(fieldName = "Course")
	@JsonProperty("course_id")
	private Integer courseId;
	@JsonProperty("parent_id")
	private Integer parentId;
}
