package com.jlumart.controller.admin;

import com.jlumart.constant.MessageConstant;
import com.jlumart.result.Result;
import com.jlumart.service.ComonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口
 */
@RestController("adminCommonController")
@Slf4j
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private ComonService comonService;
    /**
     * 文件上传
     * @param file 文件对象
     * @return 文件路径
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        String filePath = comonService.upload(file);
        return filePath == null ? Result.error(MessageConstant.UPLOAD_FAILED) : Result.success(filePath);
    }
}
