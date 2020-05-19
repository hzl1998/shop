package com.hzl.mapper;

import com.hzl.entity.Attribute;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttributeMapper {

    List<Attribute> getAttributesBySel(Integer id, String sel);

    int addAttributes(Attribute attribute);

    Attribute getAttributesById(Integer id);

    int updateAttribute(Attribute attribute);

    int delAttribute(Integer attr_id, Integer cat_id);

    int updateVals(Attribute attribute);
}
