package org.fzu.ybk.controller;


import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.StatusCode;
import org.fzu.ybk.service.FileService;
import org.fzu.ybk.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

@RestController
@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;
    @Autowired
    ResponseService responseService;
    private final Logger logger = LoggerFactory.getLogger(FileController.class);


    @GetMapping(value = "/profilePhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage getProfilePhoto(
                @RequestParam(value = "profilePhotoUrl",required = false) String imgPath,
                HttpServletRequest request, HttpServletResponse response) {

        if (imgPath == null) return null;
        // 注意关闭流
        try ( InputStream is = new FileInputStream(GlobalConstant.profilePhotoPath + imgPath) ) {
            return ImageIO.read(is);

        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/profilePhoto")
    public String updateProfilePhoto(
            @RequestParam("profilePhoto") MultipartFile file,
            HttpServletRequest request) {
        try {
            return fileService.updateProfilePhoto(file);
        } catch ( Exception e) {
//            e.printStackTrace();
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



    public ResponseEntity<FileSystemResource> export(File file,String fileName) throws UnsupportedEncodingException {
        if (file == null) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));

        return ResponseEntity .ok() .headers(headers) .contentLength(file.length()) .contentType(MediaType.parseMediaType("application/octet-stream")) .body(new FileSystemResource(file));
    }

//    @RequestMapping(value = "export_xls.html", method = RequestMethod.GET)
//    public ResponseEntity<FileSystemResource> exportXls() {
//        return export(new File("E:\\mydict.xls"),);
//    }


    @GetMapping(value = "/taskfile")
    public ResponseEntity<FileSystemResource> getTaskFile(
            @RequestParam(value = "taskFileUrl",required = false) String taskFileUrl,
            HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String completed_file_name = GlobalConstant.taskFilePath + taskFileUrl;
        return export(new File(completed_file_name),taskFileUrl);

    }


    @PostMapping("/taskfile")
    public String updateTaskFile(
            @RequestParam("taskfile") MultipartFile file,
            HttpServletRequest request) {
        try {
            return fileService.updateTaskFile(file);
        } catch ( Exception e) {
            return responseService.responseFactory(StatusCode.RESPONSE_ERR,e.toString());
        }
    }



}
