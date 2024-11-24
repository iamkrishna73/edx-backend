package io.iamkrishna73.edx.repos;


import io.iamkrishna73.edx.entities.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer> {

    boolean existsByEmail(String email);
    UserDetailsEntity findByEmail(String email);
}
