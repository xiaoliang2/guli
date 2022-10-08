package com.athuang.educenter.service.impl;

import com.athuang.commonutils.JwtUtils;
import com.athuang.commonutils.MD5;
import com.athuang.educenter.entity.UcenterMember;
import com.athuang.educenter.entity.vo.RegisterVo;
import com.athuang.educenter.mapper.UcenterMemberMapper;
import com.athuang.educenter.service.UcenterMemberService;
import com.athuang.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

//     注入redisTemplate
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
//         获取登录的手机号
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password))
        {
            throw new GuliException(20001,"登录失败");

        }
//         判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
//         没有这个手机号
        if (mobileMember == null)
        {
            throw new GuliException(20001,"登录失败");

        }
//         判断密码
//         因为存储到数据库的密码是使用加密后的
//         所以要把输入的密码进行加密，再和数据库的密码进行比较

        if (!MD5.encrypt(password).equals(mobileMember.getPassword()))
        {
            throw new GuliException(20001,"登录失败");
        }
//         判断是否被禁用
        if (mobileMember.getIsDisabled())
        {
            throw new GuliException(20001,"登录失败");
        }
//         登录成功
//         生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

//         获取注册的数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
//         非空判断
        System.out.println(code+":"+mobile+":" +nickname+"'" +password);
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname))
        {
            throw new GuliException(20001,"注册失败");

        }
//         判断验证码
//         获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode))
        {
            throw new GuliException(20001,"注册失败");

        }
//         注册手机不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new GuliException(20001,"此手机号已经注册过了！");
        }
//         把数据添加到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);     //  用户不禁用
        member.setAvatar("http:// thirdwx.qlogo.cn/mmopen/vi_32/MQ7qUmCprK9am16M1Ia1Cs3RK0qiarRrl9y8gsssBjIZeS2GwKSrnq7ZYhmrzuzDwBxSMMAofrXeLic9IBlW4M3Q/132");
        redisTemplate.delete(mobile);
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);

        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

//     查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);

    }
}
