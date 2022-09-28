package com.athuang.educms.service;

import com.athuang.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-09-26
 */
public interface CrmBannerService extends IService<CrmBanner> {

    void updateBannerById(CrmBanner crmBanner);

    void removeBannerById(String id);

    List<CrmBanner> selectAllBanner();
}
