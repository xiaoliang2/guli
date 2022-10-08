package com.athuang.educms.controller;


import com.athuang.commonutils.R;
import com.athuang.educms.entity.CrmBanner;
import com.athuang.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * 前台banner，显示控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

//     查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner()
    {
        List<CrmBanner> list = bannerService.selectAllBanner();

        return R.ok().data("list",list);
    }
}

