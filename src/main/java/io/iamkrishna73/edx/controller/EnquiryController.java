package io.iamkrishna73.edx.controller;

import io.iamkrishna73.edx.constant.LoggingConstant;
import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;
import io.iamkrishna73.edx.service.IEnquiryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/edx/user/enquiries")
public class EnquiryController {
    private final IEnquiryService enquiryService;

    public EnquiryController(IEnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Integer userId) {
        var methodName = "EnquiryController:getDashboard";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        DashboardResponse response = enquiryService.getDashBoardData(userId);
        log.info(LoggingConstant.END_METHOD_LOG, methodName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addEnquiry(@PathVariable Integer userId, @RequestBody EnquiryFormDto enquiryFormDto) {
        var methodName = "EnquiryController:addEnquiry";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);
        System.out.println(enquiryFormDto);
        enquiryService.addEnquiry(userId,enquiryFormDto);
        return new ResponseEntity<>("New Student Enquiry save successfully", HttpStatus.CREATED);
    }

    @GetMapping("/course-names")
    public ResponseEntity<?> getCourseName(){
        var methodName = "EnquiryController:getCourseName";
        log.info(LoggingConstant.START_METHOD_LOG, methodName);
        List<CourseEntity> courseList = enquiryService.getCourseNames();
        return new ResponseEntity<>(courseList,HttpStatus.OK);
    }

    @GetMapping("/status-name")
    public ResponseEntity<?> getEnquiryStatus(){
        var methodName = "EnquiryController:getStatusName";
        log.info(LoggingConstant.START_METHOD_LOG, methodName);
        List<StatusEntity> statusList = enquiryService.getEnquiryStatus();
        return new ResponseEntity<>(statusList,HttpStatus.OK);
    }
}

