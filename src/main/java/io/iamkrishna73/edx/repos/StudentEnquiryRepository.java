package io.iamkrishna73.edx.repos;

import io.iamkrishna73.edx.entities.StudentEnquiryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnquiryRepository extends JpaRepository<StudentEnquiryEntity, Integer> {
    @Query("SELECT s FROM StudentEnquiryEntity s WHERE s.userDetails.id = :userId")
    Optional<List<StudentEnquiryEntity>> findAllByUserId(@Param("userId") Integer userId);
    Optional<StudentEnquiryEntity> findByEnquiryIdAndUserDetailsId(Integer enquiryId, Integer userId);
}

