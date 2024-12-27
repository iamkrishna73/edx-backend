package io.iamkrishna73.edx.dtos;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EnquiryDto {
    private Integer enquiryId;
    private String studentName;
    private String course;
    private long contactNumber;
    private LocalDate enquiryDate;
    private String enquiryStatus;
    private String classMode;
}