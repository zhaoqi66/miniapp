package com.github.binarywang.demo.wx.miniapp.controller;


import com.github.binarywang.demo.wx.miniapp.config.ResponseModel;
import com.github.binarywang.demo.wx.miniapp.config.ResponseModels;
import com.github.binarywang.demo.wx.miniapp.service.UploadFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * UploadFileController
 * <p>
 * api:单张图片上传接口 /miniApi/picture/uploadOne post
 * api:删除图片接口  /miniApi/picture/delete  delete
 *
 * @author juan
 * @date 2018/7/18 22:51
 */
@RestController
@RequestMapping("miniApi")
@Api(description = "图片上传模块")
public class UploadFileController {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/picture/uploadOne")
    @ApiOperation(value = "单图片上传")
    public ResponseModel<String> uploadOnePicture(MultipartFile file, HttpServletRequest request) {


        logger.info("============>单图片上传 file={}", file);
        if (request.getCharacterEncoding() == null) {
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String uploadFile = uploadFileService.uploadFile(file);
        return ResponseModels.ok(uploadFile);
    }

    @DeleteMapping("/picture/delete")
    @ApiOperation(value = "图片删除")
    public ResponseModel<String> deletePicture(String fileKey) {
        logger.info("============>图片删除 fileKey={}", fileKey);
        String deletePicture = uploadFileService.deletePicture(fileKey);
        return ResponseModels.ok(deletePicture);
    }


}
