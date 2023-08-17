package com.tjoeun.firstProject.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.firstProject.dto.ArticleForm;
import com.tjoeun.firstProject.entity.Article;
import com.tjoeun.firstProject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 롬복에서 지원하는 로그 어노테이션
public class ArticleController {
	
	//	JPA repository 인터페이스 객체를 선언하고 @Autowired 어노테이션으로 초기화한다.
	@Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결한다.
	private ArticleRepository articleRepository;
	
	//	글 입력 뷰 페이지 호출
	@GetMapping("/articles/new")
	public String newArticleForm() {
		log.info("ArticleController의 newArticleForm() 메소드 실행");
		return "articles/new";
	}
	
	//	입력한 글을 저장하는 메소드
	@PostMapping("/articles/create")
	//	form에서 넘어오는 데이터는 커맨드 객체로 받는다.
	public String creatArticle(ArticleForm form) {
		log.info("ArticleController의 creatArticle() 메소드 실행");
		//System.out.println(form);
		//	@@Slf4j 어노테이션 로그 레벨, 로그는 반드시 문자열로 사용한다.
		//log.trace(form.toString());
		//log.info(form.toString());
		//	DTO의 데이터를 Entity로 변환한다.
		Article article = form.toEntity();
		//System.out.println(article);
		log.info(article.toString());
		//	repository에게 entity를 데이터베이스에 저장하게 된다.
		//	id가 자동으로 생성된다.
		Article saved = articleRepository.save(article);
		//System.out.println(saved);
		log.info(saved.toString());
		
		//	테이블에 글이 저장되면 전체 글 목록을 얻어와서 브라우저에 표시하는 요청을 한다.
		//return "redirect:/articles";
		//	테이블에 글이 저장되면 저장한 글만 얻어와서 브라우저에 표시하는 요청을 한다.
		return "redirect:/articles/" + saved.getId();
		
	}
	
	//	테이블에 저장된 글 1건을 얻어오는 메소드
	//  브라우저에서 "/articles/글ID"형태로 요청을 받아 처리한다.
	//  {}는 "/articles/1", "/articles/2", ... 와 같이 변화되는 데이터를 받는다는 의미
	@GetMapping("/articles/{id}")
	public String show(@PathVariable Long id, Model model) {
		log.info("ArticleController의 show() 메소드 실행");
		//log.info("id = " + id);
	    // articleRepository의 findById()메소드로 id에 해당되는 데이터 1건을 테이블에서 가져온다.
	    // findById() 메소드로 얻어온 데이터가 없을 경우 orElse() 메소드로 null을 리턴시킨다.
	    Article articleEntity = articleRepository.findById(id).orElse(null);
	    // log.info("articleEntity = " + articleEntity);
	    //	테이블에서 얻어온 데이터를 뷰페이지로 전달하기 위해 Model 인터페이스 객체에 넣어준다.
	    model.addAttribute("article", articleEntity);
	    return "articles/show"; // 뷰페이지를 설정한다.
	}
	  
	//	테이블에 저장된 모든 글을 얻어오는 메소드
	@GetMapping("/articles")
	public String index(Model model) {
		log.info("ArticleController의 index() 메소드 실행");
		//	테이블에 저장된 모든 글 목록을 얻어온다.
		List<Article> articleEntityList = articleRepository.findAll();
		// log.info("articleEntityList = " + articleEntityList);
		//	테이블에서 가져온 모든 글 목록을 뷰페이지로 전달한다.
		model.addAttribute("articleList", articleEntityList);
		  
		return "articles/index";
	}
	  
	  //	테이블에 저장된 글을 수정하기 위해 얻어오는 메소드
	  @GetMapping("/articles/{id}/edit")
	  public String edit(@PathVariable Long id, Model model) {
		  log.info("ArticleController의 edit() 메소드 실행");
		  //log.info("id = " + id);
		  
		  //	테이블에서 수정할 데이터를 얻어온다.
		  Article articleEntity = articleRepository.findById(id).orElse(null);
		  log.info("articleEntity = " + articleEntity);
		  
		  //	얻어온 수정할 데이터를 Model 인터페이스 객체에 넣어준다.
		  model.addAttribute("article", articleEntity);
		  
		  //	view 페이지를 요청한다.
		return "articles/edit";
	  }
  
	  //	테이블의 저장된 글을 수정하는 메소드
	  @PostMapping("/articles/update")
	  public String update(ArticleForm form) {
		  log.info("ArticleController의 update() 메소드 실행");
		  log.info(form.toString());
		  //	커맨드 객체로 넘겨받은 데이터로 테이블의 데이터를 수정(덮어쓰기)한다.
		  //	DTO의 데이터를 Entity로 변환한다.
		  Article article = form.toEntity();
		  //log.info(article.toString());
		  
		  //	데이터베이스에 저장된 수정할 데이터를 얻어와서 entity로 수정한 후 다시 데이터베이스에 저장한다.
		  Article target = articleRepository.findById(article.getId()).orElse(null);
		  if (target != null) {
			  articleRepository.save(article);
		  }
		  
		  //	수정 결과를 view 페이지로 redirect 한다.
		  return "redirect:/articles/" + article.getId();
	  }
	  
	  //	테이블의 저장된 글을 삭제하는 메소드
	  @GetMapping("/articles/{id}/delete")
	  //	RedirectAttributes 인터페이스 객체는 뷰페이지로 1회성 메시지 전달에 사용한다.
	  public String delete(@PathVariable Long id, RedirectAttributes rttr) {
		  log.info("ArticleController의 delete() 메소드 실행");
		  //log.info("id = " + id);
		  //	삭제할 데이터가 존재하나 확인하기 위해서 삭제할 데이터를 얻어온다.
		  Article target = articleRepository.findById(id).orElse(null);
		  // log.info(target.toString());
		  
		  //	데이터를 삭제한다.
		  if(target != null) {
			  articleRepository.delete(target);
			  //	addFlashAttribute(): 1회성으로 1번만 사용할 메시지를 뷰페이지로 전달한다.
			  rttr.addFlashAttribute("msg", id + "번 글 삭제 완료!");
		  } else {
			  rttr.addFlashAttribute("msg", id + "번 글은 존재하지 않습니다.");
		  }
		  
		  //	삭제 후 목록보기 페이지로 redirect한다.
		  return "redirect:/articles/";
	  }
	
}
