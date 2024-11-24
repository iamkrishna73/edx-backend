package io.iamkrishna73.edx.service;

import io.iamkrishna73.edx.dtos.DashboardResponse;
import io.iamkrishna73.edx.dtos.EnquiryFormDto;
import io.iamkrishna73.edx.dtos.EnquirySearchCriteriaDto;

import java.util.List;

public class EnquiryService implements IEnquiryService {
    @Override
    public List<String> getCourseNames() {
        return null;
    }

    @Override
    public List<String> getEnquiryStatus() {
        return null;
    }

    @Override
    public DashboardResponse getDashBoardResponse(Integer userId) {
        return null;
    }

    @Override
    public String addEnquiry(EnquiryFormDto enquiryFormDto) {
        return null;
    }

    @Override
    public List<EnquiryFormDto> getEnquires(Integer userId, EnquirySearchCriteriaDto criteriaDto) {
        return null;
    }

    @Override
    public EnquiryFormDto getEnquiry(Integer enquiryId) {
        return null;
    }
}
