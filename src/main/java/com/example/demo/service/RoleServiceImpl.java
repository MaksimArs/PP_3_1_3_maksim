package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;
    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void saveRole(Role role) {
        roleRepo.save(role);
    }

    @Override
    public Optional<Role> findById(long id) {
        return roleRepo.findById(id);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
