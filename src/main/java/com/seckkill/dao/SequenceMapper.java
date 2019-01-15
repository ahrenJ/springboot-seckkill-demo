package com.seckkill.dao;

import com.seckkill.domain.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {

    int deleteByPrimaryKey(String name);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence getSequenceByName(String name);

    Sequence selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);
}