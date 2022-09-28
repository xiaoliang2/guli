package com.athuang.educms.controller;


import com.athuang.commonutils.R;
import com.athuang.educms.entity.CrmBanner;
import com.athuang.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;
//    分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit)
    {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);

        bannerService.page(pageBanner,null);


        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
//    添加
    @PostMapping("addbanner")
    public R addBanner(@RequestBody CrmBanner crmBanner)
    {

        bannerService.save(crmBanner);

        return R.ok();
    }
//    根据id查询
    @GetMapping("get/{id}")
    public R get(@PathVariable String id)
    {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item",banner);
    }
//    修改
    @ApiOperation(value = "修改Banner")
    @PostMapping("updatebanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner)
    {
        bannerService.updateById(crmBanner);

        return R.ok();
    }
//    删除
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id)
    {

        bannerService.removeById(id);

        return R.ok();
    }
}

