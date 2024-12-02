package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquirySearchCriteriaDto;
import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;

import java.util.List;

public interface IEnquiryService {
    List<CourseEntity> getCourseNames();
    List<StatusEntity> getEnquiryStatus();
    DashboardResponse getDashBoardData(Integer userId);
    void addEnquiry(Integer userId, EnquiryFormDto enquiryFormDto);
    List<EnquiryFormDto> getEnquires(Integer userId, EnquirySearchCriteriaDto criteriaDto);
   // EnquiryFormDto getEnquiry(Integer enquiryId);

}
