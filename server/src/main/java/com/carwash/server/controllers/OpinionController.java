package com.carwash.server.controllers;

import com.carwash.server.dto.CreateOpinionDto;
import com.carwash.server.services.OpinionService;
import com.carwash.server.utilies.AuthMiner;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("api/v1/opinions")
@RestController
@AllArgsConstructor
public class OpinionController {

    private final OpinionService opinionService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateOpinionDto> upload(Authentication authentication, @RequestPart("file") MultipartFile file, @RequestPart CreateOpinionDto createOpinionDto) throws IOException {
        return opinionService.createOpinion(AuthMiner.getUsername(authentication), file, createOpinionDto);
    }

//    @PostMapping("test")
//    public ResponseEntity<CreateOpinionDto> upload(@RequestPart("file") MultipartFile file, @RequestPart CreateOpinionDto createOpinionDto) {
//        return opinionService.createOpinionTemp(file, createOpinionDto);
//    }

    @GetMapping
    public ResponseEntity<List<CreateOpinionDto>> getAllOpinions() {
        return opinionService.getAllOpinions();
    }

    @DeleteMapping
    public ResponseEntity deleteOpinion(Authentication authentication) {
        return opinionService.deleteOpinion(AuthMiner.getUsername(authentication));
    }

}
