package com.tjoeun.firstProject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.firstProject.dto.ArticleForm;
import com.tjoeun.firstProject.entity.Article;
import com.tjoeun.firstProject.repository.ArticleRepository;
import com.tjoeun.firstProject.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ArticleApiController {
	
	//	ArticleApiController에서 사용하던 ArticleRepository는 service 계층으로 넘기고, ArticleRepository는 service 객체를 만들어 사용한다.
//	@Autowired //	ArticleRepository 인터페이스의 bean을 자동으로 주입받는다.
//	private ArticleRepository articleRepository;
	
	@Autowired //	ArticleService 클래스의 bean을 자동으로 주입받는다.
	private ArticleService articleService;
	
	//	모든 글 목록 얻어오기
	@GetMapping("/api/articles")
	public List<Article> index() {
		log.info("ArticleApiController의 index() 메소드 실행");
		//return articleRepository.findAll();
		return articleService.index();
	}
	
	//	특정 글 얻어오기
	@GetMapping("/api/articles/{id}")
	public Article show(@PathVariable Long id) {
		log.info("ArticleApiController의 show() 메소드 실행");
		//return articleRepository.findById(id).orElse(null);
		return articleService.show(id);
	}
	
	//	글저장
	@PostMapping("/api/articles")
	//	form에서 데이터를 받아올 때는 커맨드 객체로 받으면 된지만, REST API에서 JSON으로 던지는 데이터를 받을때는
	//	body 부분에 담겨서 넘어오는 데이터를 받아야 하므로 커맨드 객체 앞에 @RequestBody라는 어노테이션을 붙여서 받는다.
	public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
		log.info("ArticleApiController의 create() 메소드 실행");
		//log.info("dto: " + dto);
		//Article saved = articleRepository.save(dto.toEntity());
		Article saved = articleService.create(dto);
		//log.info("saved: " + saved);
		//return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		//	body() 메소드는 body 영역에 데이터를 담아서 넘겨주고 build() 메소드는 body 없이 넘겨준다.
		return saved != null ?
			ResponseEntity.status(HttpStatus.CREATED).body(saved) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	//	글 수정
	@PatchMapping("/api/articles/{id}")
	public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
		log.info("ArticleApiController의 update() 메소드 실행");
		/*
		log.info("id: {}, dto: {}", id, dto.toString());
		//	수정용 entity를 생성한다.
		Article article = dto.toEntity();
		log.info("id: {}, article: {}", id, article.toString());
		//	수정할 entity를 조회한다.
		Article target = articleRepository.findById(id).orElse(null);
		log.info("id: {}, target: {}", id, target.toString());
		//	잘못된 요청(수정 대상이 없거나 id가 다른 경우)을 처리한다.
		if (target == null || id != target.getId()) {
			log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
			//	HttpStatus.BAD_REQUEST: 400 BAD_REQUEST, 잘못된 요청
			//log.info("HttpStatus.BAD_REQUEST: " + HttpStatus.BAD_REQUEST);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		//	글 수정 후 정상 응답을 보낸다.
		//	RequestBody를 통해 ArticleForm으로 넘어오는 수정할 데이터의 일부가 넘어오지 않으면 넘어오지 않은 데이터는 null로 수정되기 떄문에
		//	이 현상을 방지하기 위해 조회된 수정할 entity를 RequestBody를 통해 넘어온 데이터가 있는 경우만 수정한다.
		
		target.patch(article);
		Article updated = articleRepository.save(target);
		log.info("updated: " + updated);
		*/
		
		Article updated = articleService.update(id, dto);
		//	HttpStatus.OK: 200 OK, 응답 성공
		//log.info("HttpStatus.OK: " + HttpStatus.OK);
		//return ResponseEntity.status(HttpStatus.OK).body(updated);
		return updated != null ? 
			ResponseEntity.status(HttpStatus.OK).body(updated) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	//	글 삭제
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Article> delete(@PathVariable Long id) {
		log.info("ArticleApiController의 delete() 메소드 실행");
		/*
		//	삭제할 entity를 조회한다.
		Article target = articleRepository.findById(id).orElse(null);
		//	잘못된 요청을 처리한다.
		if (target == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		//	삭제 후 정상 응답을 보낸다.
		articleRepository.delete(target);
		*/
		
		//return ResponseEntity.status(HttpStatus.OK).body(null);
		Article deleted = articleService.delete(id);
		return deleted != null ? 
			ResponseEntity.status(HttpStatus.OK).build() : 
			ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	//	트랜잭션 => 실패 => 롤백
	@PostMapping("/api/transaction-test")
	public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
		log.info("ArticleApiController의 transactionTest() 메소드 실행");
		//log.info("dtos: " + dtos);
		List<Article> createList = articleService.transactionTest(dtos);
		
		return createList != null ?
				ResponseEntity.status(HttpStatus.OK).body(createList) : 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
