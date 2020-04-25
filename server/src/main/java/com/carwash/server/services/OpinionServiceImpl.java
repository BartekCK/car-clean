package com.carwash.server.services;

import com.carwash.server.dto.CreateOpinionDto;
import com.carwash.server.models.Opinion;
import com.carwash.server.models.User;
import com.carwash.server.repositories.OpinionRepository;
import com.carwash.server.repositories.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpinionServiceImpl implements OpinionService {

    @Value("${domain.full.name}")
    private String domainName;
    private final String resourcePath = "/img/opinions/";

    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;

    private final String folder = "src/main/resources/static/img/opinions/";


    public OpinionServiceImpl(OpinionRepository opinionRepository, UserRepository userRepository) {
        this.opinionRepository = opinionRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<CreateOpinionDto> createOpinion(String username, MultipartFile file, CreateOpinionDto createOpinionDto) throws IOException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Użytkownik " + username + " nie został odnaleziony"));
        String image = saveFile(user.getId(), file);
        Opinion opinion = Opinion.builder()
                .user(user)
                .date(LocalDateTime.now())
                .description(createOpinionDto.getDescription())
                .mark(createOpinionDto.getMark())
                .image(image)
                .build();

        opinionRepository.save(opinion);
        return ResponseEntity.ok(CreateOpinionDto.build(opinion));
    }

    @Override
    public ResponseEntity<List<CreateOpinionDto>> getAllOpinions() {
        List<Opinion> opinions = opinionRepository.findAll();
        List<CreateOpinionDto> createOpinionDto = opinions.stream().map(opinion -> CreateOpinionDto.build(opinion)).collect(Collectors.toList());
        createOpinionDto.forEach(opinion -> opinion.setImage(
                new StringBuilder()
                        .append(this.domainName)
                        .append(this.resourcePath)
                        .append(opinion.getImage())
                        .toString()
        ));
        return ResponseEntity.ok(createOpinionDto);
    }

    @Override
    public ResponseEntity deleteOpinion(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Użytkownik " + username + " nie został odnaleziony"));
        Opinion opinion = opinionRepository.findById(user.getOpinion().getId()).orElseThrow(() -> new RuntimeException("Użytkownik " + username + " nie dodał jeszcze swojej opini"));
        boolean isImageDelete = deleteFile(folder + opinion.getImage());
        if (!isImageDelete)
            throw new RuntimeException("Problem z usunięcem zdjęcia o nazwie " + opinion.getImage());

        opinionRepository.deleteById(user.getOpinion().getId());
        return ResponseEntity.ok("Opinia została usunięta");
    }

    @Override
    public ResponseEntity<CreateOpinionDto> createOpinionTemp(MultipartFile file, CreateOpinionDto createOpinionDto) {
        try {
            saveFile((long) 1, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    private String saveFile(Long userId, MultipartFile file) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!(extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg")))
            throw new RuntimeException("Błędne rozszerzenie pliku");

        String imageName = new StringBuilder().append(userId).append(".").append(extension).toString();

        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder + imageName);
        Files.write(path, bytes);
        return imageName;
    }
}
