package com.hzl.service;

import com.hzl.entity.Attribute;
import com.hzl.mapper.AttributeMapper;
import com.hzl.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {
    @Autowired
    AttributeMapper attributeMapper;

    public List<Attribute> getAttributesBySel(Integer id,String sel){
        return attributeMapper.getAttributesBySel(id,sel);
    }

    public int addAttributes(Attribute attribute) {
        return attributeMapper.addAttributes(attribute);
    }

    public Attribute getAttributesById(Integer id) {
        return attributeMapper.getAttributesById(id);
    }

    public int updateAttribute(Attribute attribute) {
        return attributeMapper.updateAttribute(attribute);
    }

    public int delAttribute(Integer attr_id, Integer cat_id) {
        return attributeMapper.delAttribute(attr_id,cat_id);
    }

    public int updateVals(Attribute attribute) {
        return attributeMapper.updateVals(attribute);
    }
}
