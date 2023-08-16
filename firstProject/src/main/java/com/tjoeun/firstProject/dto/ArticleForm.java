package com.tjoeun.firstProject.dto;

import com.tjoeun.firstProject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor // 기본생성자
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ArticleForm {
	
	private String title;
	private String content;
	
//	public ArticleForm(String title, String content) { // @AllArgsConstructor
//		super();
//		this.title = title;
//		this.content = content;
//	}
	
	//	getter&setter
//	public String getTitle() { // @Getter
//		return title;
//	}
//
//	public void setTitle(String title) { // @Setter
//		this.title = title;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}

//	@Override
//	public String toString() { // @ToString
//		return "ArticleForm [title=" + title + ", content=" + content + "]";
//	}
	
	//	DTO 데이터를 Entity(테이블과 매핑되는 클래스, Article)
	public Article toEntity() {
		return new Article(null, title, content);
	}
	
}
