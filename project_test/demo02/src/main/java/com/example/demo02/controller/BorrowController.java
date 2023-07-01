package com.example.demo02.controller;

import com.example.demo02.common.Result;
import com.example.demo02.controller.request.BorrowPageRequest;
import com.example.demo02.entity.Borrow;
import com.example.demo02.entity.Retur;
import com.example.demo02.service.IBorrowService;
import com.example.demo02.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //标识
@RequestMapping("/borrow") //一级前缀
public class BorrowController {

    @Autowired
    IBorrowService borrowService;

    

    @PostMapping("/save")
    public Result save(@RequestBody  Borrow obj){
        borrowService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody  Borrow obj){
        borrowService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}") //二级前缀
    public Result getById(@PathVariable Integer id){
       Borrow obj =borrowService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list") //二级前缀
    public Result list(){
        List<Borrow> list =borrowService.list();
        return Result.success(list);
    }

    @GetMapping("/page") //二级前缀
    public Result page(BorrowPageRequest pageRequest){

        return Result.success( borrowService.page(pageRequest));
    }

    @GetMapping("/pageRetur") //二级前缀
    public Result pageRetur(BorrowPageRequest pageRequest){

        return Result.success( borrowService.pageRetur(pageRequest));
    }

    @PostMapping("/saveRetur")
    public Result saveRetur(@RequestBody Retur obj){
        borrowService.saveRetur(obj);
        return Result.success();
    }

    @DeleteMapping("/deleteRetur/{id}")
    public Result deleteRetur(@PathVariable Integer id){
        borrowService.deleteReturById(id);
        return Result.success();
    }

    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(borrowService.getCountByTimeRange(timeRange));
    }
}
