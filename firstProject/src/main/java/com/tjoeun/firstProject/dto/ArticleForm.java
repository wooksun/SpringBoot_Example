package com.tjoeun.firstProject.dto;

import com.tjoeun.firstProject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor // 기본생성자
@AllArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
public class ArticleForm {
	
	private Long id; // id 필드 추가
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
		log.info("Entity로 변환");
		//return new Article(null, title, content);
		//	추가된 id 필드로 entity를 초기화 할 수 있도록 수정한다.
		return new Article(id, title, content);
	}
	
}
