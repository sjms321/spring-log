package com.ms.log;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ms.member.Member;
import com.ms.service.MemberService;

@Controller
public class HomeController {
	public static final int True = 1;
	public static final int False = 0;
	
	@Inject
	private MemberService service;
	
	@GetMapping(value = "/")
	public String main() {
		return "main";
	}
	
	@PostMapping(value = "/login.do")
	public String main(Member m, HttpServletRequest request,Model model) {
		
		// id pw valid&auth check
		if(m.getM_id()=="" || m.getM_pw()=="") { 
			//에러코드를 만들어서 에러코드별 멘트를 뷰에서 만들수 있게 하자 
			model.addAttribute("msg", "200"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인 화면으로
			return "redirect";
		}
		if(service.Id_Check(m)==False) {
			//사용자가 입력한 ID가 데이터 베이스에 존재하지 않았을 시
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인
			return "redirect";
		}
		
		if(service.Login(m)==False) {
			//사용자가 입력한 비밀번호가 일치 하지 않았을 시 
			model.addAttribute("msg", "PW is wrong");    
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인 화면으로 
			return "redirect";
		}
		
		HttpSession session = request.getSession(); 
		//로그인 정보를 세션에 저장
		session.setAttribute("member", m);
		
		return "loginOK";
	}
	
	@RequestMapping(value = "/sessionOUT")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		
		//invalidate()를 통해 세션값 삭제
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/loginOk")
	public String loginOK() {
		return "loginOK";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET) 
	public String join() {
		return "join";
	}
	
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	public String join(Member m,Model model) {
		// 입력된 member의 id와 중복된 ID가 데이터 베이스의 존재하는지 확인
		if(service.Id_Check(m)==True) { 
			model.addAttribute("msg", "The ID already exists"); 
			 //존재한다면 다시 회원가입창으로 이동
			model.addAttribute("url", "http://localhost:8090/log/join");  
			return "redirect";
		}
		
		//입력된 Member의 정보를 데이터 베이스에 저장.
		service.Register(m);
		return "joinOK";
	}
	
	@RequestMapping(value = "/joinOK")
	public String joinOK() {
		return "joinOK";
	}
	
	@RequestMapping(value="/removeCHECK") 
	public String removeCheck() {
		return "checker";
	}
	
	@RequestMapping(value = "/removeOK") 
	public String remove(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		//세션의 저장되어있는 Member를 불러옴 
		Member m =(Member) session.getAttribute("member"); 
		
		//세션의 저장된 Member의 정보를 데이터 베이스에서 삭제
		service.Delete(m);
		
		//세션의 정보 삭제
		session.invalidate();
		return "removeOK"; 
	}
	
	
	
	@RequestMapping("/Update")
	public String Update(){
		return "Update";
	}
	
	@RequestMapping("/Update.do")
	
	//회원 정보 수정시 수정정보를 입력했을 떄 실행
	public String Update(Member m,HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		
		//받은 Member정보를 데이터베이스에서 수정
		service.Update(m);	
		
		//세션 데이터 삭제 (회원정보를 수정하였으니 자동 로그아웃)
		session.invalidate(); 
		
		model.addAttribute("msg", "success update! please login again"); 
		model.addAttribute("url", "http://localhost:8090/log/"); 
		
		return "redirect";
	}
	
	@RequestMapping("/findPW")
	public String findpw() {
		return "findPW";
	}
	@RequestMapping("/findPW.do")
	public String findpw(Member m,Model model) {
		
		if(service.Id_Check(m)==False) {
			// 존재하는 아이디인지 확인
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		
		if(service.IDhint_check(m)==False) {
			// 입력한 hint답이 ID에 해당하는 hint가 맞는지 확인.
			model.addAttribute("msg", "The answer of the question is not correct! please try again!"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		 // 입력 member의 비밀번호를 저장
		String found_PW = service.find_PW(m);
		
		//비밀번호를 알려주는 팝업창 메세지
		model.addAttribute("msg", "your PW is "+ found_PW); 
		model.addAttribute("url", "http://localhost:8090/log/"); 
		return "redirect";
	}
}

