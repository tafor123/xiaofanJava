package com.example.cloudshareafter.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/22 10:31
 */
@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;

    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService(){
        staticUserMapper = userMapper;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String createToken(String userId,String sign){
        return JWT.create().withAudience(userId)  //将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign));  // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */

    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(!StrUtil.isBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserMapper.select(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

}
