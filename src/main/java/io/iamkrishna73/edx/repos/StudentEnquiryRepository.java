package io.iamkrishna73.edx.repos;

import io.iamkrishna73.edx.entities.StudentEnquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiryEntity, Integer> {
}
