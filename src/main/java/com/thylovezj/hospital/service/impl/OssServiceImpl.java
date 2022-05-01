package com.thylovezj.hospital.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.service.OssService;
import com.thylovezj.hospital.util.ConstantOssPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class OssServiceImpl implements OssService {

    //上传文件到oss
    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantOssPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantOssPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantOssPropertiesUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantOssPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //上传文件流
        InputStream inputStream = file.getInputStream();

        //构建上传文件路径,注意上传文件路径不能包含bucket,这里最好给一个用户名
        String ObjectName = "xdu_hospital/user/avator/" + file.getOriginalFilename();

        //你的bucketName,文件名,stream流
        ossClient.putObject(ConstantOssPropertiesUtils.BUCKET_NAME, ObjectName, inputStream);

        ossClient.shutdown();

        //把上传文件的路径返回
        //上传uri格式https://bucket-20020501.oss-cn-beijing.aliyuncs.com/hospital/avator/118492169681.png
        String url = "https://" + ConstantOssPropertiesUtils.BUCKET_NAME + "." + ConstantOssPropertiesUtils.END_POINT + "/" + ObjectName;
        return url;
    }
}
