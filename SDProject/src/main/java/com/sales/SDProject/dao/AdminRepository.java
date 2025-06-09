package com.sales.SDProject.dao;

import com.sales.SDProject.model.admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<admin, String> {
    boolean existsByUsername(String username);
}
