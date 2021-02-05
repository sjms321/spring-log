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
	
	@GetMapping(value = "/")//get������� mapping @RequestMapping(value = "/", method = RequestMethod.GET)�� �������
	public String main() {
		return "main";
	}
	
	@PostMapping(value = "/login.do")//post������� mapping get�� ���� ���� post�� ���������� �� ����. ���Ȱ� ������ �ִ�. get����� �ӵ��� ���� �� �������� �ּ�â�� ������ ����ȴ�.
	public String main(Member m, HttpServletRequest request,Model model) {
		
		if(m.getM_id()=="" || m.getM_pw()=="") { //����ڰ� ID �Ǵ� PW�� �Է� ���� �ʾ��� ��
			model.addAttribute("msg", "please enter all information "); 
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α��� ȭ������
			return "redirect";
		}
		
		if(service.Id_Check(m)==False) {//����ڰ� �Է��� ID�� ������ ���̽��� �������� �ʾ��� ��
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α���
			return "redirect";
		}
		
		if(service.Login(m)==False) {//����ڰ� �Է��� ��й�ȣ�� ��ġ ���� �ʾ��� �� 
			model.addAttribute("msg", "PW is wrong"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α��� ȭ������ 
			return "redirect";
		}
		
		HttpSession session = request.getSession(); //getSession()�� �̿��Ͽ� ������ ������ ������ ��ȯ
		session.setAttribute("member", m);//���ǿ� �� ������ ��� ����� Member������ ����
		
		return "loginOK";// �α��� �Ϸ� �������� �̵�
	}
	
	@RequestMapping(value = "/sessionOUT")
	public String logout(HttpServletRequest request) {//�α׾ƿ��� �ϱ� ���ؼ� ���� ���ǿ� �α��� ������ �ݵ�� �����Ƿ� HttpServletRequest�� ���� ������ ������ �غ�
		HttpSession session = request.getSession(); //���� ���� ��ȯ
		session.invalidate();//invalidate()�� ���� ���ǰ� ����
		return "redirect:/";//�α��� �������� 
	}
	
	@RequestMapping(value = "/loginOk")//�α��� ���� ������
	public String loginOK() {
		return "loginOK";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET) 
	public String join() {// ȸ������ �������� Get������� �̵�
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

