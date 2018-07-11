package com.haier.controller;

import com.haier.po.Tenvdetail;
import com.haier.po.TenvdetailCustom;
import com.haier.response.Result;
import com.haier.service.TenvdetailService;
import com.haier.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 服务-环境映射关系
 * @Author: luqiwei
 * @Date: 2018/5/30 19:45
 */
@Slf4j
@RestController
@RequestMapping("/tenvdetail")
public class TenvdetailController {

    @Autowired
    TenvdetailService tenvdetailService;

    //增
    @PostMapping("/insertOne")
    public Result insertOne(Tenvdetail tenvdetail){
        return ResultUtil.success(tenvdetailService.insertOne(tenvdetail));
    }

    //删
    @PostMapping("/deleteOne")
    public Result deleteOne(Integer id){
        return ResultUtil.success(tenvdetailService.deleteOne(id));
    }

    //改
    @PostMapping("/updateOne")
    public Result updateOne(Tenvdetail tenvdetail){
        return ResultUtil.success(tenvdetailService.updateOne(tenvdetail));
    }

    //根据id查一条
    @PostMapping("/selectOne")
    public Result selectOne(Integer id){
        return ResultUtil.success(tenvdetailService.selectOne(id));
    }


    //返回List<TenvdetailCustom>
    @PostMapping("/selectTenvdetailCustomListByCondition")
    public Result selectTenvdetailCustomListByCondition(TenvdetailCustom tenvdetailCustom){
        return ResultUtil.success(tenvdetailService.selectByCondition(tenvdetailCustom));
    }


    //查-仅查询tenvdetail表数据,返回list
    @PostMapping("/selectTenvdetailListByCondition")
    public Result selectTenvdetailListByCondition(Tenvdetail tenvdetail){
        return ResultUtil.success(tenvdetailService.selectByCondition(tenvdetail));
    }


    //查-关联表查询,返回包装类,并且携带pageinfo
    @PostMapping("/selectByCondition")
    public Result selectByCondition(TenvdetailCustom tenvdetailCustom,Integer pageNum,Integer pageSize){
        return ResultUtil.success(tenvdetailService.selectByCondition(tenvdetailCustom,pageNum,pageSize));
    }
}
