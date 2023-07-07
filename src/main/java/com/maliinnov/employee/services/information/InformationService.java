package com.maliinnov.employee.services.information;

import com.maliinnov.employee.dto.information.InformationResponse;
import com.maliinnov.employee.models.Information;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InformationService {

    InformationResponse save(Information information, MultipartFile file) throws IOException;
    InformationResponse publish(Long id);
    byte[] getImage(Long id) throws IOException;
    List<InformationResponse> findAll();
    InformationResponse update(Information information, Long id);
    InformationResponse infoById(Long id);
    InformationResponse delete(Long id);
}
