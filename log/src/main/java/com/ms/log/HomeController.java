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
	
	@GetMapping(value = "/")//get방식으로 mapping @RequestMapping(value = "/", method = RequestMethod.GET)과 같은방식
	public String main() {
		return "main";
	}
	
	@PostMapping(value = "/login.do")//post방식으로 mapping get은 정보 수집 post는 정보주입을 때 쓴다. 보안과 관련이 있다. get방식은 속도가 조금 더 빠르지만 주소창에 정보가 노출된다.
	public String main(Member m, HttpServletRequest request,Model model) {
		
		if(m.getM_id()=="" || m.getM_pw()=="") { //사용자가 ID 또는 PW를 입력 하지 않았을 시
			model.addAttribute("msg", "please enter all information "); 
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인 화면으로
			return "redirect";
		}
		
		if(service.Id_Check(m)==False) {//사용자가 입력한 ID가 데이터 베이스에 존재하지 않았을 시
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인
			return "redirect";
		}
		
		if(service.Login(m)==False) {//사용자가 입력한 비밀번호가 일치 하지 않았을 시 
			model.addAttribute("msg", "PW is wrong"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //다시 로그인 화면으로 
			return "redirect";
		}
		
		HttpSession session = request.getSession(); //getSession()을 이용하여 서버에 생성된 세션을 반환
		session.setAttribute("member", m);//세션에 위 조건을 모두 통과한 Member정보를 저장
		
		return "loginOK";// 로그인 완료 페이지로 이동
	}
	
	@RequestMapping(value = "/sessionOUT")
	public String logout(HttpServletRequest request) {//로그아웃을 하기 위해선 현재 세션에 로그인 정보가 반드시 있으므로 HttpServletRequest를 통해 세션을 가져올 준비
		HttpSession session = request.getSession(); //현재 세션 반환
		session.invalidate();//invalidate()를 통해 세션값 삭제
		return "redirect:/";//로그인 페이지로 
	}
	
	@RequestMapping(value = "/loginOk")//로그인 후의 페이지
	public String loginOK() {
		return "loginOK";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET) 
	public String join() {// 회원가입 페이지로 Get방식으로 이동
		return "join";
	}
	
	@RequestMapping(value = "/join.do", method = RequestMethod.POST) //
	public String join(Member m,Model model) {
		if(service.Id_Check(m)==True) { 
			model.addAttribute("msg", "The ID already exists"); 
			model.addAttribute("url", "http://localhost:8090/log/join"); 
			return "redirect";
		}
		service.Register(m);
		return "joinOK";
	}
	
	@RequestMapping(value="/removeCHECK")
	public String removeCheck(HttpServletRequest request) {
		
		return "checker";
	}
	
	@RequestMapping(value = "/removeOK")
	public String remove(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member m =(Member) session.getAttribute("member");
		service.Delete(m);
		session.invalidate();
		return "removeOK";
	}
	
	@RequestMapping(value = "/joinOK")
	public String joinOK() {

		return "joinOK";
	}
	
	@RequestMapping("/Update")
	public String Update(HttpServletRequest request){
		return "Update";
	}
	
	@RequestMapping("/Update.do")
	public String Update(Member m,HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		service.Update(m);	
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
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		
		if(service.IDhint_check(m)==False) {
			model.addAttribute("msg", "The answer of the question is not correct! please try again!"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		String found_PW = service.find_PW(m);
		model.addAttribute("msg", "your PW is "+ found_PW); 
		model.addAttribute("url", "http://localhost:8090/log/"); 
		return "redirect";
	}
}

