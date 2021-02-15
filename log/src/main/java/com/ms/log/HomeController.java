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
			//�����ڵ带 ���� �����ڵ庰 ��Ʈ�� �信�� ����� �ְ� ���� 
			model.addAttribute("msg", "200"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α��� ȭ������
			return "redirect";
		}
		if(service.Id_Check(m)==False) {
			//����ڰ� �Է��� ID�� ������ ���̽��� �������� �ʾ��� ��
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α���
			return "redirect";
		}
		
		if(service.Login(m)==False) {
			//����ڰ� �Է��� ��й�ȣ�� ��ġ ���� �ʾ��� �� 
			model.addAttribute("msg", "PW is wrong");    
			model.addAttribute("url", "http://localhost:8090/log/"); //�ٽ� �α��� ȭ������ 
			return "redirect";
		}
		
		HttpSession session = request.getSession(); 
		//�α��� ������ ���ǿ� ����
		session.setAttribute("member", m);
		
		return "loginOK";
	}
	
	@RequestMapping(value = "/sessionOUT")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		
		//invalidate()�� ���� ���ǰ� ����
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
		// �Էµ� member�� id�� �ߺ��� ID�� ������ ���̽��� �����ϴ��� Ȯ��
		if(service.Id_Check(m)==True) { 
			model.addAttribute("msg", "The ID already exists"); 
			 //�����Ѵٸ� �ٽ� ȸ������â���� �̵�
			model.addAttribute("url", "http://localhost:8090/log/join");  
			return "redirect";
		}
		
		//�Էµ� Member�� ������ ������ ���̽��� ����.
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
		
		//������ ����Ǿ��ִ� Member�� �ҷ��� 
		Member m =(Member) session.getAttribute("member"); 
		
		//������ ����� Member�� ������ ������ ���̽����� ����
		service.Delete(m);
		
		//������ ���� ����
		session.invalidate();
		return "removeOK"; 
	}
	
	
	
	@RequestMapping("/Update")
	public String Update(){
		return "Update";
	}
	
	@RequestMapping("/Update.do")
	
	//ȸ�� ���� ������ ���������� �Է����� �� ����
	public String Update(Member m,HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		
		//���� Member������ �����ͺ��̽����� ����
		service.Update(m);	
		
		//���� ������ ���� (ȸ�������� �����Ͽ����� �ڵ� �α׾ƿ�)
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
			// �����ϴ� ���̵����� Ȯ��
			model.addAttribute("msg", "The ID doesnt exist"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		
		if(service.IDhint_check(m)==False) {
			// �Է��� hint���� ID�� �ش��ϴ� hint�� �´��� Ȯ��.
			model.addAttribute("msg", "The answer of the question is not correct! please try again!"); 
			model.addAttribute("url", "http://localhost:8090/log/findPW"); 
			return "redirect";
		}
		 // �Է� member�� ��й�ȣ�� ����
		String found_PW = service.find_PW(m);
		
		//��й�ȣ�� �˷��ִ� �˾�â �޼���
		model.addAttribute("msg", "your PW is "+ found_PW); 
		model.addAttribute("url", "http://localhost:8090/log/"); 
		return "redirect";
	}
}

