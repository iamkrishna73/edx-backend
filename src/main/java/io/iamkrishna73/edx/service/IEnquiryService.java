package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquirySearchCriteriaDto;

import java.util.List;

public interface IEnquiryService {
    List<String> getCourseNames();
    List<String> getEnquiryStatus();
    DashboardResponse getDashBoardResponse(Integer userId);
    String addEnquiry(EnquiryFormDto enquiryFormDto);
    List<EnquiryFormDto> getEnquires(Integer userId, EnquirySearchCriteriaDto criteriaDto);
    EnquiryFormDto getEnquiry(Integer enquiryId);

}
