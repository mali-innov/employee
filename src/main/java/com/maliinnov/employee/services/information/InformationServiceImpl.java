package com.maliinnov.employee.services.information;

import com.maliinnov.employee.dto.information.InformationResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.exception.NotFoundException;
import com.maliinnov.employee.models.Information;
import com.maliinnov.employee.repositories.InformationRepository;
import com.maliinnov.employee.utils.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.maliinnov.employee.utils.fileConstant.INFO_FOLDER;

@Service
@Transactional
@RequiredArgsConstructor
public class InformationServiceImpl implements InformationService {

    private final InformationRepository repository;

    @Override
    public InformationResponse save(Information information, MultipartFile file) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());
        information.setImagePath(imageName);
        Information info = repository.save(information);
        String uploadDir = INFO_FOLDER + information.getId();
        UploadFile.saveFile(uploadDir,imageName, file);
        return this.mapToResponse(info);
    }

    @Override
    public InformationResponse publish(Long id) {
        Information info = repository.findByIdAndState(id, State.Deactivate);
        if (info == null) {
            throw new NotFoundException("Aucune information n'a été trouvée");
        }
        info.setState(State.Activate);
        return this.mapToResponse(repository.save(info));
    }

    @Override
    public byte[] getImage(Long id) throws IOException {
        Information information = repository.findByIdAndState(id, State.Activate);
        String imageName = information.getImagePath();
        File file = new File(INFO_FOLDER+information.getId() +"/"+ imageName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }


    @Override
    public List<InformationResponse> findAll() {
        List<Information> information = repository.findByStateOrderByIdDesc(State.Activate);
        return this.mapToResponse(information);
    }

    @Override
    public InformationResponse update(Information information, Long id) {
        Information infoToUpdate = repository.findByIdAndState(id, State.Activate);
        infoToUpdate.setTitle(information.getTitle());
        infoToUpdate.setContent(information.getContent());
        infoToUpdate.setImagePath(information.getImagePath());
        return this.mapToResponse(repository.save(infoToUpdate));
    }

    @Override
    public InformationResponse infoById(Long id) {
        Information information = repository.findByIdAndState(id, State.Activate);
        if (information == null) {
            throw new NotFoundException("Aucune information n'a été trouvée");
        }
        return this.mapToResponse(information);
    }

    @Override
    public InformationResponse delete(Long id) {
        Information delete = repository.findByIdAndState(id, State.Activate);
        if (delete == null) {
            throw new NotFoundException("Aucune information n'a été trouvée");
        }
        delete.setState(State.Deactivate);
        return this.mapToResponse(repository.save(delete));
    }

    private InformationResponse mapToResponse(Information info) {
        return InformationResponse.builder()
                .id(info.getId())
                .title(info.getTitle())
                .content(info.getContent())
                .imagePath(info.getImagePath())
                .state(info.getState())
                .date(info.getDate())
                .employee(info.getEmployee())
                .build();
    }

    private List<InformationResponse> mapToResponse(List<Information> infos) {
        List<InformationResponse> responses = new ArrayList<>();
        for (Information info : infos) {
            responses.add(mapToResponse(info));
        }
        return responses;
    }
}
