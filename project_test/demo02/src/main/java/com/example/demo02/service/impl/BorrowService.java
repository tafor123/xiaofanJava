package com.example.demo02.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.entity.Book;
import com.example.demo02.entity.Borrow;
import com.example.demo02.entity.Retur;
import com.example.demo02.entity.User;
import com.example.demo02.exception.ServiceException;
import com.example.demo02.mapper.BookMapper;
import com.example.demo02.mapper.BorrowMapper;
import com.example.demo02.mapper.UserMapper;
import com.example.demo02.mapper.po.BorrowReturCountPO;
import com.example.demo02.service.IBorrowService;
import com.example.demo02.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Service
@Slf4j
public class BorrowService implements IBorrowService {

    @Resource
    BorrowMapper borrowMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Borrow> list() {
        return borrowMapper.list();
    }


    @Override
    @Transactional
    public PageInfo<Borrow> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(baseRequest);
        for (Borrow borrow : borrows) {
            LocalDate return_date = borrow.getReturn_date();
            LocalDate now = LocalDate.now();
            if (now.plusDays(1).isEqual(return_date)) {  // 当前日期比归还的日期小一天
                borrow.setNote("即将到期");
            } else if (now.isEqual(return_date)) {
                borrow.setNote("已到期");
            } else if (now.isAfter(return_date)) {
                borrow.setNote("已过期");
            } else {
                borrow.setNote("正常");
            }
        }
        return new PageInfo<>(borrows);
    }

    @Override
    public void save(Borrow obj) {
           //1.校验用户的积分是否足够
        String user_no = obj.getUser_no();
        User user = userMapper.getByNo(user_no);
        if(Objects.isNull(user)){
            throw new ServiceException("用户不存在");
        }
        //2.校验图书数量是否足够
        Book book = bookMapper.getByBookNo(obj.getBook_no());
        if(Objects.isNull(book)){
            throw new ServiceException("所借图书不存在");
        }
        //3.校验图书数量
        if(book.getNums() < 1){
            throw  new ServiceException("图书不足");
        }
        Integer account = user.getAccount();
        Integer score = book.getScore() * obj.getDays();
        //4.校验用账户余额
        if(score > account){
            throw  new ServiceException("用户积分不足");
        }

        //5.更新余额
        user.setAccount(user.getAccount() - score);
        userMapper.updateById(user);
        //6.更新图书数量
        book.setNums(book.getNums()-1);
        bookMapper.updateById(book);

        obj.setReturn_date(LocalDate.now().plus(obj.getDays(), ChronoUnit.DAYS));//当前日期加days为归还日期
        obj.setScore(score);
        //7.新增借书记录
        borrowMapper.save(obj);
    }

    @Override
    public PageInfo<Retur> pageRetur(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        return new PageInfo<>(borrowMapper.listReturByCondition(baseRequest));
    }

    //还书逻辑
    @Transactional
    @Override
    public void saveRetur(Retur obj) {
        //该状态
        obj.setStatus("已归还");
        borrowMapper.updateStatus("已归还", obj.getId());  // obj.getId() 是前端传来的借书id,要用@Parma方式来写，已在xml文件中写入
        //图书数量增加
       // obj.setId(null);
        obj.setReal_date(LocalDate.now());
        borrowMapper.saveRetur(obj);
        bookMapper.updateNumByNo(obj.getBook_no());

        //返还用户积分
        Book book = bookMapper.getByBookNo(obj.getBook_no());
        if(book !=null){
            long until=0;
            if(obj.getReal_date().isBefore(obj.getReturn_date())){
                 until = obj.getReal_date().until(obj.getReturn_date(), ChronoUnit.DAYS);
            }else if(obj.getReal_date().isAfter(obj.getReturn_date())){
                until = -obj.getReturn_date().until(obj.getReal_date(),ChronoUnit.DAYS);
            }
            int score =(int)until * book.getScore();
            User user = userMapper.getByNo(obj.getUser_no());
            int account = user.getAccount() + score;
            user.setAccount(account);
            if(account<0){
                    //锁定账号
                user.setStatus(false);
            }
            userMapper.updateById(user);

        }
    }

    @Override
    public Borrow getById(Integer id) {
        return borrowMapper.getById(id);
    }

    @Override
    public void update(Borrow obj) {
        obj.setUpdatetime(LocalDate.now());
        borrowMapper.updateById(obj);
    }

    @Override
    public void deleteById(Integer id) {
        borrowMapper.deleteById(id);
    }

    @Override
    public void deleteReturById(Integer id) {
        borrowMapper.deleteReturById(id);
    }

    @Override
    public Map<String ,Object> getCountByTimeRange(String timeRange) {
        Map<String, Object> map = new HashMap<>();
        Date today = new Date();
        List<DateTime> dateRange;
        switch (timeRange) {
            case "week":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -6), today, DateField.DAY_OF_WEEK);
                break;
            case "month":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -29), today, DateField.DAY_OF_MONTH);
                break;
            case "month2":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -59), today, DateField.DAY_OF_MONTH);
                break;
            case "month3":
                dateRange = DateUtil.rangeToList(DateUtil.offsetDay(today, -89), today, DateField.DAY_OF_MONTH);
                break;
            default:
                dateRange = new ArrayList<>();
        }
        List<String> dateStrRange = datetimeToDateStr(dateRange);
        map.put("date", dateStrRange);
        List<BorrowReturCountPO> borrowCount = borrowMapper.getCountByTimeRange(timeRange, 1);
        List<BorrowReturCountPO> returnCount = borrowMapper.getCountByTimeRange(timeRange, 2);
        map.put("borrow", countList(borrowCount, dateStrRange));
        map.put("retur", countList(returnCount, dateStrRange));
        return map;
    }


    private List<String> datetimeToDateStr(List<DateTime> dateTimeList) {
        List<String> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(dateTimeList)) {
            return list;
        }
        for (DateTime dateTime : dateTimeList) {
            String date = DateUtil.formatDate(dateTime);
            list.add(date);
        }
        return list;
    }

    private List<Integer> countList(List<BorrowReturCountPO> countPOList, List<String> dateRange) {
        List<Integer> list = CollUtil.newArrayList();
        if (CollUtil.isEmpty(countPOList)) {
            return list;
        }
        for (String date : dateRange) {
            Integer count = countPOList.stream().filter(countPO -> date.equals(countPO.getDate()))
                    .map(BorrowReturCountPO::getCount).findFirst().orElse(0);
            list.add(count);
        }
        return list;
    }

}
