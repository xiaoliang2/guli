package com.athuang.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.athuang.oss.service.OssService;
import com.athuang.oss.utils.ConstantPropertiesUtils;
import com.athuang.servicebase.exceptionhandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: guli_parent
 *
 * @description:
 *
 * @author: Mr.Huang
 *
 * @create: 2022-09-12 17:14
 **/
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private ConstantPropertiesUtils constantPropertiesUtils;

//     上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //  Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endPoint = constantPropertiesUtils.getEndpoint();
        //  阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = constantPropertiesUtils.getKeyId();
        String accessKeySecret = constantPropertiesUtils.getKeySecret();
        //  填写Bucket名称，例如examplebucket。
        String bucketName = constantPropertiesUtils.getBucketName();
        //  填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//         String fileHost = ConstantPropertiesUtils.FILE_HOST;

        String uploadUrl = null;

        try {

            // 判断oss实例是否存在：如果不存在则创建，如果存在则获取

            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);

            if (!ossClient.doesBucketExist(bucketName)) {

                // 创建bucket

                ossClient.createBucket(bucketName);

                // 设置oss实例的访问权限：公共读

                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

            }

            // 获取上传文件流

            InputStream inputStream = file.getInputStream();

            // 构建日期路径：avatar/2019/02/26/文件名

            String filePath = new DateTime().toString("yyyy/MM/dd");

            // 文件名：uuid.扩展名

            String original = file.getOriginalFilename();

            String fileName = UUID.randomUUID().toString();

            String fileType = original.substring(original.lastIndexOf("."));

            String newName = fileName + fileType;

            String fileUrl = filePath + "/" + newName;

            // 文件上传至阿里云

            ossClient.putObject(bucketName, fileUrl, inputStream);

            //  关闭OSSClient。

            ossClient.shutdown();

            // 获取url地址

            uploadUrl = "http:// " + bucketName + "." + endPoint + "/" + fileUrl;

        } catch (IOException e) {

//             throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        return null;
        }

        return uploadUrl;
    }
}
