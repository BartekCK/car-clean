package com.carwash.server.services;

import com.carwash.server.dto.CreateOpinionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface OpinionService {

    ResponseEntity<CreateOpinionDto> createOpinion(String username, MultipartFile file, CreateOpinionDto createOpinionDto) throws IOException;

    ResponseEntity<List<CreateOpinionDto>> getAllOpinions();

    ResponseEntity deleteOpinion(String username);

    ResponseEntity<CreateOpinionDto> createOpinionTemp(MultipartFile file, CreateOpinionDto createOpinionDto);
}
