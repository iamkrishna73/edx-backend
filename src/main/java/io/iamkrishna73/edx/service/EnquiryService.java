package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquirySearchCriteriaDto;
import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;
import io.iamkrishna73.edx.entities.StudentEnquiryEntity;
import io.iamkrishna73.edx.entities.UserDetailsEntity;
import io.iamkrishna73.edx.repos.CourseRepository;
import io.iamkrishna73.edx.repos.StatusRepository;
import io.iamkrishna73.edx.repos.StudentEnquiryRepository;
import io.iamkrishna73.edx.repos.UserDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnquiryService implements IEnquiryService {
    // private final StudentEnquiryRepository studentEnquiryRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final CourseRepository courseRepository;
    private final StatusRepository statusRepository;
    private final StudentEnquiryRepository studentEnquiryRepository;

    public EnquiryService(UserDetailsRepository userDetailsRepository, CourseRepository courseRepository, StatusRepository statusRepository, StudentEnquiryRepository studentEnquiryRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.courseRepository = courseRepository;
        this.statusRepository = statusRepository;
        this.studentEnquiryRepository = studentEnquiryRepository;
    }


    @Override
    public List<CourseEntity> getCourseNames() {
        List<CourseEntity> courseNames = courseRepository.findAll();
        return courseNames;
    }

    @Override
    public List<StatusEntity> getEnquiryStatus() {
        List<StatusEntity> statusEntities = statusRepository.findAll();
        return statusEntities;
    }

    @Override
    public DashboardResponse getDashBoardData(Integer userId) {
        var methodName = "EnquiryService:getDashBoardData";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        DashboardResponse response = new DashboardResponse();
        Optional<UserDetailsEntity> findById = userDetailsRepository.findById(userId);
        UserDetailsEntity userDetails = findById.orElseThrow(() -> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, userId);
            return new EntityNotFoundException("User not found");
        });
        List<StudentEnquiryEntity> enquiries = userDetails.getEnquiries();

        Integer totalEnquiriesCount = enquiries.size();
        Integer enrolledCount = enquiries.stream().filter(enquiry -> enquiry.getEnquiryStatus().equals("Enrolled")).toList().size();
        Integer lostEnquiriesCount = enquiries.stream().filter(enquiry -> enquiry.getEnquiryStatus().equals("Lost")).toList().size();

        response.setTotalEnquiresCount(totalEnquiriesCount);
        response.setEnrolledCount(enrolledCount);
        response.setLostEnquiresCount(lostEnquiriesCount);
        //System.out.println(userData);

        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        return response;
    }

    @Override
    public void addEnquiry(Integer userId, EnquiryFormDto enquiryFormDto) {
        var methodName = "EnquiryService:addEnquiry";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
        Optional<UserDetailsEntity> findById = userDetailsRepository.findById(userId);
        UserDetailsEntity userDetails = findById.orElseThrow(() -> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, userId);
            return new EntityNotFoundException("User not found");
        });
        studentEntity.setStudentName(enquiryFormDto.getStudentName());
        studentEntity.setStudentPhone(enquiryFormDto.getContactNumber());
        studentEntity.setClassMode(enquiryFormDto.getClassMode());
        studentEntity.setCourseName(enquiryFormDto.getCourse());
        studentEntity.setEnquiryStatus(enquiryFormDto.getEnquiryStatus());
        studentEntity.setUserDetails(userDetails);
        studentEnquiryRepository.save(studentEntity);
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
    }

    @Override
    public List<EnquiryFormDto> getEnquires(Integer userId, EnquirySearchCriteriaDto criteriaDto) {
        return null;
    }

//    @Override
//    public EnquiryFormDto getEnquiry(Integer enquiryId) {
//        return null;
//    }
}
