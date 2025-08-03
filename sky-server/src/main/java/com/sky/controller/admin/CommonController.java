package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Tag(name = "通用接口")
public class CommonController {

    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传：{}", file);
        String originalFilename = file.getOriginalFilename();

        if(originalFilename == null){
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID() + suffix;

        file.transferTo(new File("E:/code/resources/images/" + filename));
        return Result.success("http://localhost:8080/admin/common/download/" + filename);
    }

    @GetMapping("/download/{filename}")
    @Operation(summary = "文件下载")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws Exception {
        log.info("文件下载：{}", filename);
        Path path = Paths.get("E:/code/resources/images/" + filename);
        Resource resource = new UrlResource(path.toUri());
        String contentType = Files.probeContentType(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
