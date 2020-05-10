package com.hzl.mapper;

import com.hzl.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> selectMenusByUserId(@Param(value="userId")String userId);
}
