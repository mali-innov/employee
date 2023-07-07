package com.maliinnov.employee.services.role;

import com.maliinnov.employee.dto.role.RoleResponse;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Role;
import com.maliinnov.employee.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public RoleResponse findById(Long id) {
        Role role = repository.findByIdAndState(id, State.Activate);
        return this.mapToResponse(role);
    }


    @Override
    public List<RoleResponse> findAll() {
        List<Role> roles = repository.findAll();
        return this.mapToResponse(roles);
    }

    private RoleResponse mapToResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    private List<RoleResponse> mapToResponse(List<Role> roles) {
        List<RoleResponse> responses = new ArrayList<>();
        for (Role role : roles) {
            responses.add(mapToResponse(role));
        }
        return responses;
    }
}
