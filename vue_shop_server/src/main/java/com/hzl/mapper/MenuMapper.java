package com.hzl.mapper;

import com.hzl.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> selectMenusByUserId(String userId);
}
