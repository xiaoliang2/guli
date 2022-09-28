package com.athuang.eduservice.client;/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Huang
 * @create: 2022-09-24 18:27
 **/

import com.athuang.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-24 18:27
 **/
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R removeAlyVideo(String videoId) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除视频出错了");
    }
}
