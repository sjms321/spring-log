package com.ms.service;

import javax.inject.Inject;
import javax.naming.spi.DirStateFactory.Result;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ms.member.Member;


@Repository
public class MemberService {
	public static final int True = 1;
	public static final int False = 0;
	
	@Inject
	private SqlSession session;
	
	
	public void Register(Member m) {
		try {
			session.insert( "memberMapper.Register", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int Id_Check(Member m) {
		try {
			return session.selectOne("memberMapper.Id_Check", m);
		} catch (Exception e) {
			e.printStackTrace();
			return False;
		}
	}
	public int Login(Member m) {
			int result = 0;
			result = session.selectOne("memberMapper.Login", m);
			if(result!=0) {
				return True;
			}
			else return False;
	}
	public Member Login_Info(Member m) {
		
		Member mem = session.selectOne("memberMapper.Login", m);
		
		return mem;
	}
	public void Delete(Member m) {
		
		session.selectOne("memberMapper.Delete", m);
		
	}
}
