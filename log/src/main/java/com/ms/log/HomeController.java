package com.ms.log;


import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public String main(Member m,HttpSession session) {
		//로그인 확인해주기
		
		if(service.Id_Check(m)==False) {
			return "main";
		}
		if(service.Login(m)==False) {
			return "main";
		}
		
		
		return "loginOK";
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
	public String join(Member m) {
		 service.Register(m);
		 return "joinOK";
	}

	@RequestMapping(value = "/remove")
	public String remove() {
		return "remove";
	}
	@RequestMapping(value = "/joinFail")
	public String joinFail() {
		return "joinFail";
	}
	@RequestMapping(value = "/joinOK")
	public String joinOK() {
		return "joinOK";
	}
}
