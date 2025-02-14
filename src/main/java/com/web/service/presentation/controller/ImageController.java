package com.web.service.presentation.controller;

import com.web.service.application.service.ImageService;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.ImageResourceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("${api.prefix}/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{filename}")
    public ResponseEntity<Object> downloadFiles(@PathVariable("filename") String filename) {
        try{
            ImageResourceDTO imageFileResources = imageService.retrieveImage(filename);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("File-Name", filename);
            httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + imageFileResources.resource().getFilename());
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(imageFileResources.path())))
                    .headers(httpHeaders).body(imageFileResources.resource());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(),null));
        }
    }
}
