package com.maliinnov.employee.repositories;

import com.maliinnov.employee.enums.Roles;
import com.maliinnov.employee.enums.State;
import com.maliinnov.employee.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
    Role findByNameAndState(String name, State state);
    Role findByIdAndState(Long id, State state);
    List<Role> findByState(State state);
}
