package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquiryDto;
import io.iamkrishna73.edx.dtos.response.EnquiryRequest;
import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;
import io.iamkrishna73.edx.entities.StudentEnquiryEntity;
import io.iamkrishna73.edx.entities.UserDetailsEntity;
import io.iamkrishna73.edx.exception.ResourceNotFoundException;
import io.iamkrishna73.edx.repos.CourseRepository;
import io.iamkrishna73.edx.repos.StatusRepository;
import io.iamkrishna73.edx.repos.StudentEnquiryRepository;
import io.iamkrishna73.edx.repos.UserDetailsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnquiryService implements IEnquiryService {
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
        System.out.println(enquiries);

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
    public void addEnquiry(Integer userId, EnquiryRequest enquiryRequest) {
        var methodName = "EnquiryService:addEnquiry";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        StudentEnquiryEntity studentEntity = new StudentEnquiryEntity();
        Optional<UserDetailsEntity> findById = userDetailsRepository.findById(userId);
        UserDetailsEntity userDetails = findById.orElseThrow(() -> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, userId);
            return new EntityNotFoundException("User not found");
        });
        studentEntity.setStudentName(enquiryRequest.getStudentName());
        studentEntity.setStudentPhone(enquiryRequest.getContactNumber());
        studentEntity.setClassMode(enquiryRequest.getClassMode());
        studentEntity.setCourseName(enquiryRequest.getCourse());
        studentEntity.setEnquiryStatus(enquiryRequest.getEnquiryStatus());
        studentEntity.setUserDetails(userDetails);
        studentEnquiryRepository.save(studentEntity);
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
    }

    @Override
    public List<EnquiryDto> getAllEnquires(Integer userId) {
        var methodName = "EnquiryService:getEnquires";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        List<EnquiryDto> enquiriesResponses = new ArrayList<>();

        List<StudentEnquiryEntity> studentEnquiry = studentEnquiryRepository.findAllByUserId(userId)
                .orElseThrow(() -> {
                    log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, userId);
                    return new ResourceNotFoundException("No enquiries found for user with ID: " + userId);
                });

        System.out.println(studentEnquiry);
        for (StudentEnquiryEntity studentEnquiryEntity : studentEnquiry) {
            EnquiryDto enquiryResponse = getEnquiryResponse(studentEnquiryEntity);
            enquiriesResponses.add(enquiryResponse);
        }

        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        System.out.println(enquiriesResponses);
        return enquiriesResponses;
    }

    @Override
    public void updateEnquiry(Integer userId, Integer enquiryId, EnquiryRequest enquiryDto) {
        var methodName = "EnquiryService:updateEnquiry";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId, enquiryId);
        StudentEnquiryEntity studentEnquiry = studentEnquiryRepository.findByEnquiryIdAndUserDetailsId(enquiryId, userId).orElseThrow(()-> {
            log.error(LoggingConstant.ERROR_METHOD_LOG, methodName, "No enquiries found for user with ID: "+ " " + userId);
            return new ResourceNotFoundException("No enquiries found for user with ID: "+ enquiryId + " " + userId);
        });

        System.out.println(studentEnquiry);
        studentEnquiry.setStudentName(enquiryDto.getStudentName());
        studentEnquiry.setStudentPhone(enquiryDto.getContactNumber());
        studentEnquiry.setClassMode(enquiryDto.getClassMode());
        studentEnquiry.setCourseName(enquiryDto.getCourse());
        studentEnquiry.setEnquiryStatus(enquiryDto.getEnquiryStatus());
        System.out.println(studentEnquiry);
        studentEnquiryRepository.save(studentEnquiry);
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
       // System.out.println(studentEnquiry);// studentEnquiry
    }

    private static EnquiryDto getEnquiryResponse(StudentEnquiryEntity studentEnquiryEntity) {
        EnquiryDto enquiryResponse = new EnquiryDto();
        enquiryResponse.setEnquiryId(studentEnquiryEntity.getEnquiryId());
        enquiryResponse.setStudentName(studentEnquiryEntity.getStudentName());
        enquiryResponse.setCourse(studentEnquiryEntity.getCourseName());
        enquiryResponse.setContactNumber(studentEnquiryEntity.getStudentPhone());
        enquiryResponse.setClassMode(studentEnquiryEntity.getClassMode());
        enquiryResponse.setEnquiryStatus(studentEnquiryEntity.getEnquiryStatus());
        enquiryResponse.setEnquiryDate(studentEnquiryEntity.getDateCreated());
        return enquiryResponse;
    }
}
