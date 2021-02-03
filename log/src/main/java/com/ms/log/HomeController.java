package com.ms.log;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main() {
		return "main";
	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String main(Member m, HttpServletRequest request,Model model) {
		//로그인 확인해주기
		if(service.Id_Check(m)==False) {
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/"); 
			return "redirect";
		}
		
		if(service.Login(m)==False) {
			model.addAttribute("msg", "ID and PW are not matched"); 
			model.addAttribute("url", "http://localhost:8090/log/"); 
			return "redirect";
		}
		HttpSession session = request.getSession();
		session.setAttribute("member", m);
		
		return "loginOK";
	}
	@RequestMapping(value = "/sessionOUT", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("session out");
		return "redirect:/";
	}
	@RequestMapping(value = "/loginOk")
	public String loginOK(Member m, HttpServletRequest request) {
		Member mem = service.Login_Info(m);
		HttpSession session = request.getSession();
		session.setAttribute("member", mem);
		return "loginOK";
	}
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "join";
	}
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(Member m,Model model) {
		if(service.Id_Check(m)==True) { 
			model.addAttribute("msg", "The ID already exists"); 
			model.addAttribute("url", "http://localhost:8090/log/join"); 
			return "redirect";
		}
		service.Register(m);
		return "joinOK";
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
	public String Update(){
		return "Update";
	}
	@RequestMapping("/Update.do")
	public String Update(Member m,HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		Member mem = (Member)session.getAttribute("member");	
		service.Update(m);	
		session.invalidate();
		model.addAttribute("msg", "success Update! please login again"); 
		model.addAttribute("url", "http://localhost:8090/log/"); 
		return "redirect";
	}
}
