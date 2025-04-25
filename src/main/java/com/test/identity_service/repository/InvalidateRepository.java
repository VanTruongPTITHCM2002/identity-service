package com.test.identity_service.repository;

import com.test.identity_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidateRepository extends JpaRepository<InvalidatedToken,String> {

}
