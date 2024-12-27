package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquiryDto;
import io.iamkrishna73.edx.dtos.response.EnquiryRequest;
import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;

import java.util.List;

public interface IEnquiryService {
    List<CourseEntity> getCourseNames();
    List<StatusEntity> getEnquiryStatus();
    DashboardResponse getDashBoardData(Integer userId);
    void addEnquiry(Integer userId, EnquiryRequest enquiryRequest);
    List<EnquiryDto> getAllEnquires(Integer userId);

    void updateEnquiry(Integer userId, Integer enquiryId, EnquiryRequest enquiryDto);
}
