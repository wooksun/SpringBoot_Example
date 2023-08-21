package com.tjoeun.firstProject.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.tjoeun.firstProject.entity.Article;
import com.tjoeun.firstProject.entity.Comment;

import lombok.extern.slf4j.Slf4j;

//@SpringBootTest // 어노테이션을 붙여주면 스프링 부트와 연동한 통합 테스트를 수행한다.
@DataJpaTest // 어노테이션을 붙여주면 JPA와 연동한 테스트를 수행한다.
@Slf4j
class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	@Test
	//	@DisplayName 어노테이션 테스트 결과에 보여줄 이름을 지정한다.
	@DisplayName("특정 게시글의 모든 댓글 조회")
	void testFindByArticleId() {
		//	4번 게시글의 모든 댓글 조회
		{
			//	입력 데이터 준비하기
			Long articleId = 4L;
			//	실행하기
			List<Comment> comments = commentRepository.findByArticleId(articleId);
			log.info("comments: " + comments);
			//	예상하기
			Article article = new Article(4L, "당신의 인생 드라마는?", "댓글");
			Comment comment1 = new Comment(1L, "한꼬미", "동밲꽃 필 무렵", article);
			Comment comment2 = new Comment(2L, "두꼬미", "소년심판", article);
			Comment comment3 = new Comment(3L, "세꼬미", "덩게르크", article);
			List<Comment> expected = new ArrayList<Comment>(Arrays.asList(comment1, comment2, comment3));
			log.info("expected: " + expected);
			
			//	검증하기
			//	assertEquals() 메소드의 3번째 인수로 검증 메시지를 출력할 수 있다.
			assertEquals(expected.toString(), comments.toString(), "4번 게시글의 모든 댓글 조회");
		}
		
		//	1번 게시글의 모든 댓글 조회
		{
			Long articleId = 1L;
			List<Comment> comments = commentRepository.findByArticleId(articleId);
			List<Comment> expected = Arrays.asList();
			assertEquals(expected.toString(), comments.toString(), "1번 게시글의 모든 댓글 조회");
		}
	}
	
	@Test
	@DisplayName("특정 게시글의 모든 댓글 조회")
	void testFindByNickname() {
		//	"한꼬미" 닉네임의 모든 댓글 조회
		{
			//	입력 데이터 준비하기
			String nickname = "한꼬미";
			//	실행하기
			List<Comment> comments = commentRepository.findByNickname(nickname);
			log.info("comments: " + comments);
			//	예상하기
			Article article1 = new Article(4L, "당신의 인생 드라마는?", "댓글");
			Article article2 = new Article(5L, "당신의 소울 푸드는?", "댓글");
			Article article3 = new Article(6L, "당신의 취미는?", "댓글");
			Comment comment1 = new Comment(1L, "한꼬미", "동밲꽃 필 무렵", article1);
	        Comment comment2 = new Comment(4L, "한꼬미", "연어", article2);
	        Comment comment3 = new Comment(7L, "한꼬미", "등산", article3);
			List<Comment> expected = Arrays.asList(comment1, comment2, comment3);
			log.info("expected: " + expected);
			//	검증하기
			assertEquals(expected.toString(), comments.toString(), "한꼬미의 모든 댓글 조회");
		}
		
		//	null의 모든 댓글 조회
		{
			//	입력 데이터 준비하기
			String nickname = null;
			//	실행하기
			List<Comment> comments = commentRepository.findByNickname(nickname);
			log.info("comments: " + comments);
			List<Comment> expected = Arrays.asList();
			log.info("expected: " + expected);
			//	검증하기
			assertEquals(expected.toString(), comments.toString(), "null의 모든 댓글 조회");
		}
	}

}
