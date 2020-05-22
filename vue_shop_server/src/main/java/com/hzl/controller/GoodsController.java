package com.hzl.controller;

import com.hzl.entity.Category;
import com.hzl.entity.Goods;
import com.hzl.entity.GoodsPics;
import com.hzl.entity.PageInfo;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/goods")
    @PreAuthorize("hasAuthority('/goods')")
    public Result getGoodsList(Integer page,Integer rows,String goods_name){
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        //第几页
        Integer currentPage = page;

        page = (page - 1) * rows;
        try {
            //总条数
            int total = goodsService.getGoodsListCount(goods_name);
            List<Goods> goodsList = goodsService.getGoodsList(page,rows,goods_name);
            // 计算总页数
            int count=0;
            if(total%rows==0){
                count=total/rows;
            }else{
                count=total/rows+1;
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(goodsList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(goodsList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取商品列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @PutMapping("/goods/delGood")
    @PreAuthorize("hasAuthority('/goods/delGood')")
    public Result delGood(Integer goods_id){
       if(goods_id == null || goods_id.equals("")){
           return ResultFactory.buildFailResult("商品id不能为空");
       }
       try {
           Long delete_time = System.currentTimeMillis()/1000;
           int isok = goodsService.delGood(goods_id,delete_time);
           if(isok > 0){
               return ResultFactory.buildSuccessResult(null,"删除成功");
           }else {
               return ResultFactory.buildFailResult("删除失败");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return ResultFactory.buildFailResult("出现异常");
       }
    }

    @PostMapping("/goods/add")
    @PreAuthorize("hasAuthority('/goods/add')")
    public Result addGood(@RequestBody Goods goods){
        if(goods.getGoods_name() == null || goods.getGoods_name().equals("")){
            return ResultFactory.buildFailResult("商品名称不能为空");
        }
        if(goods.getGoods_price() == null || goods.getGoods_price().equals("")){
            return ResultFactory.buildFailResult("商品价格不能为空");
        }
        if(goods.getGoods_number() == null || goods.getGoods_number().equals("")){
            return ResultFactory.buildFailResult("商品数量不能为空");
        }
        if(goods.getGoods_weight() == null || goods.getGoods_weight().equals("")){
            return ResultFactory.buildFailResult("商品重量不能为空");
        }
        if(goods.getCat_id() == null || goods.getCat_id().equals("")){
            return ResultFactory.buildFailResult("商品分类id不能为空");
        }
        String[] cats_id = goods.getCat_id().split(",");
        goods.setCat_id(cats_id[2]);
        goods.setCat_one_id(cats_id[0]);
        goods.setCat_two_id(cats_id[1]);
        goods.setCat_three_id(cats_id[2]);
        goods.setAdd_time(System.currentTimeMillis()/1000);
        try {
            int isok = goodsService.addGood(goods);
            if(isok > 0){
                if(goods.getPics().length > 0){
                    int isok1 = goodsService.addGoodPics(goods.getPics(),goods.getGoods_id());
                }
                if(goods.getAttrs().length > 0){
                    int isok2 = goodsService.addGoodAttr(goods.getAttrs(),goods.getGoods_id());
                }
                return ResultFactory.buildSuccessResult(null,"添加成功");
            } else {
                return ResultFactory.buildFailResult("添加失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }

    @GetMapping("/goods/getGoodById")
    @PreAuthorize("hasAuthority('/goods/getGoodById')")
    public Result getGoodById(Integer goods_id){
       if(goods_id == null || goods_id.equals("")){
           return ResultFactory.buildFailResult("商品id不能为空");
       }
       try {
           Goods goods = goodsService.getGoodById(goods_id);
           List<GoodsPics> goodsPics = goodsService.getGoodPicsById(goods_id);
           Object[] arr = new Object[goodsPics.size()];
           goodsPics.toArray(arr);
           goods.setPics(arr);
           return ResultFactory.buildSuccessResult(goods,"查询商品信息成功");
       } catch (Exception e) {
           e.printStackTrace();
           return ResultFactory.buildFailResult("出现异常");
       }
    }

    @PutMapping("/goods/update")
    @PreAuthorize("hasAuthority('/goods/update')")
    public Result updateGood(@RequestBody Goods goods){
        if(goods.getGoods_id() == null || goods.getGoods_id().equals("")){
            return ResultFactory.buildFailResult("商品id不能为空");
        }
        if(goods.getGoods_name() == null || goods.getGoods_name().equals("")){
            return ResultFactory.buildFailResult("商品名称不能为空");
        }
        if(goods.getGoods_price() == null || goods.getGoods_price().equals("")){
            return ResultFactory.buildFailResult("商品价格不能为空");
        }
        if(goods.getGoods_number() == null || goods.getGoods_number().equals("")){
            return ResultFactory.buildFailResult("商品数量不能为空");
        }
        if(goods.getGoods_weight() == null || goods.getGoods_weight().equals("")){
            return ResultFactory.buildFailResult("商品重量不能为空");
        }
        if(goods.getCat_id() == null || goods.getCat_id().equals("")){
            return ResultFactory.buildFailResult("商品分类id不能为空");
        }
        String[] cats_id = goods.getCat_id().split(",");
        goods.setCat_id(cats_id[2]);
        goods.setCat_one_id(cats_id[0]);
        goods.setCat_two_id(cats_id[1]);
        goods.setCat_three_id(cats_id[2]);
        goods.setUpd_time(System.currentTimeMillis()/1000);
        try {
            int isok = goodsService.updateGood(goods);
            if(isok > 0){
                if(goods.getPics().length > 0){
                    int isok3 = goodsService.delGoodPics(goods.getGoods_id());
                    int isok1 = goodsService.addGoodPics(goods.getPics(),goods.getGoods_id());
                }
                if(goods.getAttrs().length > 0){
                    int isok4 = goodsService.delGoodAttr(goods.getGoods_id());
                    int isok2 = goodsService.addGoodAttr(goods.getAttrs(),goods.getGoods_id());
                }
                return ResultFactory.buildSuccessResult(null,"修改成功");
            } else {
                return ResultFactory.buildFailResult("修改失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }
}
