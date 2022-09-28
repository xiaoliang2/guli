package com.athuang.educenter.service;

import com.athuang.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-09-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);
}
