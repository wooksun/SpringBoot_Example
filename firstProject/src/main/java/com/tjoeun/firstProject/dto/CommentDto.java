package com.tjoeun.firstProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjoeun.firstProject.entity.Article;
import com.tjoeun.firstProject.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {

	private Long id;
	private String nickname;
	private String body;
	//	json 데이터의 key와 dto 객체의 필드 이름이 다르면 json 데이터가 dto에 넘어가지 않는다. 
	//	json은 스네이크 표기법을 주로 사용하고 java는 카멜 표기법을 주로 사용하기 때문에 자주 발생되는 문제이다.
	//	json의 key와 dto 객체의 필드 이름이 다를 경우, @JsonProperty 어노테이션의 인수로 json 데이터의 key를 
	//	지정해서 dto 필드에 지정하면, json 데이터의 key와 dto의 필드 이름이 다르더라도 데이터를 받을 수 있다.
	@JsonProperty("article_id")
	private Long articleId;
	
	//	entity를 dto로 변환하는 메소드
	public static CommentDto createCommentDto(Comment comment) {
		return new CommentDto(
				comment.getId(), 
				comment.getNickname(), 
				comment.getBody(), 
				comment.getArticle().getId());
	}
	
}
