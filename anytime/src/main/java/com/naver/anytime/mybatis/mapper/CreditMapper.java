package com.naver.anytime.mybatis.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.naver.anytime.domain.Credit;

@Mapper
public interface CreditMapper {

	public int insert(Credit c);

}
