package com.athuang.educenter.service.impl;

import com.athuang.commonutils.JwtUtils;
import com.athuang.commonutils.MD5;
import com.athuang.educenter.entity.UcenterMember;
import com.athuang.educenter.mapper.UcenterMemberMapper;
import com.athuang.educenter.service.UcenterMemberService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(UcenterMember member) {
//        获取登录的手机号
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
        {
            throw new GuliException(20001,"登录失败");

        }
//        判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
//        没有这个手机号
        if (mobileMember == null)
        {
            throw new GuliException(20001,"登录失败");

        }
//        判断密码
//        因为存储到数据库的密码是使用加密后的
//        所以要把输入的密码进行加密，再和数据库的密码进行比较

        if (!MD5.encrypt(password).equals(mobileMember.getPassword()))
        {
            throw new GuliException(20001,"登录失败");
        }
//        判断是否被禁用
        if (mobileMember.getIsDisabled())
        {
            throw new GuliException(20001,"登录失败");
        }
//        登录成功
//        生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }
}
