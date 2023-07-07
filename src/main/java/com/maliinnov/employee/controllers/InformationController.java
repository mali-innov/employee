package com.maliinnov.employee.controllers;

import com.maliinnov.employee.dto.information.InformationResponse;
import com.maliinnov.employee.models.Information;
import com.maliinnov.employee.services.information.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/information")
public class InformationController {

    private final InformationService service;

    @PostMapping
    ResponseEntity<InformationResponse> save(Information information,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        InformationResponse info = service.save(information, file);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("/publish/{id}")
    ResponseEntity<InformationResponse> publish(@PathVariable Long id){
        return new ResponseEntity<>(service.publish(id), HttpStatus.OK);
    }

    @GetMapping()
    List<InformationResponse> findAll(){
        return service.findAll();
    }

    @PutMapping("/{id}")
    InformationResponse update(@RequestBody Information information, @PathVariable Long id){
        return service.update(information, id);
    }

    @GetMapping(value = "/image/{id}", produces ={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    byte[] image(@PathVariable Long id) throws IOException {
        return service.getImage(id);
    }

    @GetMapping("/{id}")
    InformationResponse findById(@PathVariable Long id){
        return service.infoById(id);
    }

    @DeleteMapping("/{id}")
    InformationResponse delete(@PathVariable Long id){
        return service.delete(id);
    }
}
