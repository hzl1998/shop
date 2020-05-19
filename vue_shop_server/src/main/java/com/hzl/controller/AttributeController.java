package com.hzl.controller;


import com.hzl.entity.Attribute;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttributeController {
    @Autowired
    AttributeService attributeService;

    @GetMapping("/attributes")
    public Result getAttributesBySel(Integer id,String sel){
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        if(sel == null || sel.equals("")){
            return ResultFactory.buildFailResult("sel不能为空");
        }
        List<Attribute> attributeList = attributeService.getAttributesBySel(id,sel);

        return ResultFactory.buildSuccessResult(attributeList,"获取参数列表成功");
    }

    @PostMapping("/addAttributes")
    public Result addAttributes(@RequestBody Attribute attribute){
        if(attribute.getAttr_id() == null || attribute.getAttr_id().equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        if(attribute.getAttr_name() == null || attribute.getAttr_name().equals("")){
            return ResultFactory.buildFailResult("参数名称不能为空");
        }
        if(attribute.getAttr_sel() == null || attribute.getAttr_sel().equals("")){
            return ResultFactory.buildFailResult("sel不能为空");
        }
        int isok = attributeService.addAttributes(attribute);
        if(isok > 0) {
            return ResultFactory.buildSuccessResult(null,"添加成功");
        } else {
            return ResultFactory.buildFailResult("添加失败");
        }
    }

    @GetMapping("/getAttributesById")
    public Result getAttributesById(Integer id){
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        Attribute attribute = attributeService.getAttributesById(id);

        return ResultFactory.buildSuccessResult(attribute,"获取参数成功");
    }

    @PutMapping("/updateAttribute")
    public Result updateAttribute(@RequestBody Attribute attribute){
        if(attribute.getAttr_id() == null || attribute.getAttr_id().equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        if(attribute.getAttr_name() == null || attribute.getAttr_name().equals("")){
            return ResultFactory.buildFailResult("参数名称不能为空");
        }
        if(attribute.getAttr_sel() == null || attribute.getAttr_sel().equals("")){
            return ResultFactory.buildFailResult("sel不能为空");
        }
        int isok = attributeService.updateAttribute(attribute);
        if(isok > 0) {
            return ResultFactory.buildSuccessResult(null,"修改成功");
        } else {
            return ResultFactory.buildFailResult("修改失败");
        }
    }

    @DeleteMapping("/delAttribute")
    public Result delAttribute(Integer attr_id,Integer cat_id){
        if(attr_id == null || attr_id.equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        if(cat_id == null || cat_id.equals("")){
            return ResultFactory.buildFailResult("分类id不能为空");
        }
        int isok = attributeService.delAttribute(attr_id,cat_id);
        if(isok > 0) {
            return ResultFactory.buildSuccessResult(null,"删除成功");
        } else {
            return ResultFactory.buildFailResult("删除失败");
        }
    }

    @PutMapping("/updateVals")
    public Result updateVals(@RequestBody Attribute attribute){
        if(attribute.getAttr_id() == null || attribute.getAttr_id().equals("")){
            return ResultFactory.buildFailResult("参数id不能为空");
        }
        if(attribute.getAttr_name() == null || attribute.getAttr_name().equals("")){
            return ResultFactory.buildFailResult("参数名称不能为空");
        }
        if(attribute.getAttr_sel() == null || attribute.getAttr_sel().equals("")){
            return ResultFactory.buildFailResult("sel不能为空");
        }
        if(attribute.getAttr_vals() == null || attribute.getAttr_vals().equals("")){
            return ResultFactory.buildFailResult("可选值列表信息不能为空");
        }
        int isok = attributeService.updateVals(attribute);
        if(isok > 0) {
            return ResultFactory.buildSuccessResult(null,"修改成功");
        } else {
            return ResultFactory.buildFailResult("修改失败");
        }
    }
}
