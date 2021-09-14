package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.common.R;
import com.atguigu.yygh.common.handler.YyghException;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

//医院设置接口
@Api(description = "医院设置接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    //查询所有医院设置
    @ApiOperation(value = "医院设置列表")
    @GetMapping("findAll")
//    public List<HospitalSet>findAll(){
//        List<HospitalSet> list = hospitalSetService.list();
//        return list;
//    }
    public R findAll(){
        try {
            int i = 10/0;
        } catch (Exception e) {
            throw new YyghException(20001,"自定义异常");
        }
        List<HospitalSet> list = hospitalSetService.list();
        return R.ok().data("list",list);
    }


    @ApiOperation(value = "根据id逻辑删除医院")
    @DeleteMapping("{id}")
    /*public boolean removeById(@PathVariable String id){
        boolean remove = hospitalSetService.removeById(id);
        return remove;
    }*/
    public R removeById(@PathVariable String id){
        boolean remove = hospitalSetService.removeById(id);
        return  R.ok();
    }


    @ApiOperation(value = "分页医院设置列表")
    @GetMapping("{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){
        Page<HospitalSet> pageParam = new Page<>(page,limit);
        hospitalSetService.page(pageParam);
        List<HospitalSet> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("rows",records).data("total",total);
    }

    @ApiOperation(value = "分页条件医院设置列表")
    @PostMapping("{page}/{limit}")
    public R pageQuery(@PathVariable Long page,
                       @PathVariable Long limit,
                       @RequestBody HospitalSetQueryVo hospitalSetQueryVo){
        //创建分页对象
        Page<HospitalSet> pageParam = new Page<>(page,limit);
        //根据传参，设置查询条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();
        if(!StringUtils.isEmpty(hoscode)){
            wrapper.eq("hoscode",hoscode);
        }
        if(!StringUtils.isEmpty(hosname)){
            wrapper.like("hosname",hosname);
        }
        //带条件分页查询
        hospitalSetService.page(pageParam,wrapper);
        //获取数据返回
        List<HospitalSet> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("rows",records).data("total",total);
    }

    @ApiOperation(value = "新增医院设")
    @PostMapping("save")
    public R save(@RequestBody HospitalSet hospitalSet){
        boolean save = hospitalSetService.save(hospitalSet);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询医院设置")
    @GetMapping("getHospById/{id}")
    public R getHospById(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return R.ok().data("item",hospitalSet);
    }

    @ApiOperation(value = "修改医院设置")
    @PostMapping("update")
    public R update(@RequestBody HospitalSet hospitalSet){
        boolean update = hospitalSetService.updateById(hospitalSet);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "批量删除医院设置")
    //批量删除医院设置，前端传给我个集合就行【用json串传，json串是前端传过来的前端自己实现】
    @DeleteMapping("batchRemove")
    public R batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        boolean remove = hospitalSetService.removeByIds(idList);
        return R.ok();
    }

    // 医院设置锁定和解锁
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public R lockHospitalSet(@PathVariable Long id,
                             @PathVariable Integer status) {
        //根据id查询
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //存入值，进行更新
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login(){
        //{"code":20000,"data":{"token":"admin-token"}}
        return R.ok().data("token","admin-token");

    }

    @ApiOperation(value = "用户信息")
    @GetMapping("info")
    public R info(){
//        {"code":20000,"data":{"roles":["admin"],
//            "introduction":"I am a super administrator",
//                    "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
//                    "name":"Super Admin"}}
        /*return R.ok().data("roles","admin")
                .data("introduction","I am a super administrator")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name","Super Admin");*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");

        return R.ok().data(map);

    }

}
