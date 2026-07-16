package com.sky.controller.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "公共文件接口")
public class CommonController {

    @Autowired
    private OSS ossClient;

    @Value("${sky.aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${sky.aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("文件上传：{}", file.getOriginalFilename());
        // 1. 生成唯一文件名：日期/随机名.后缀
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String suffix = getSuffix(file.getOriginalFilename());
        String key = date + "/" + UUID.randomUUID().toString().substring(0, 8) + suffix;

        try {
            // 2. 获取输入流
            InputStream inputStream = file.getInputStream();

            // 3. 设置元数据
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            // 4. 上传到 OSS
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
            ossClient.putObject(putObjectRequest);

            // 5. 设置文件为公共读
            ossClient.setObjectAcl(bucketName, key, com.aliyun.oss.model.CannedAccessControlList.PublicRead);

            // 5. 拼接外网访问 URL
            String url = "https://" + bucketName + "." + endpoint + "/" + key;
            log.info("文件上传成功: {}", url);

            return Result.success(url);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
    }

    private String getSuffix(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".jpg";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
