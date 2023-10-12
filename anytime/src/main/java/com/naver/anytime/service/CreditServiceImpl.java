package com.naver.anytime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.anytime.domain.Credit;
import com.naver.anytime.mybatis.mapper.CreditMapper;

@Service
public class CreditServiceImpl implements CreditService {
	@Autowired
	private CreditMapper dao;

	@Override
	public int insert(Credit c) {
		return dao.insert(c);
	}
	

}
