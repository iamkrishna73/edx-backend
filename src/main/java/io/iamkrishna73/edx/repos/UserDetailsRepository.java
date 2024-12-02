package io.iamkrishna73.edx.repos;


import ch.qos.logback.core.model.INamedModel;
import io.iamkrishna73.edx.entities.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

    boolean existsByEmail(String email);
    Optional<UserDetailsEntity> findByEmail(String email);
   // Optional<UserDetailsEntity> findById(Integer id);
}
