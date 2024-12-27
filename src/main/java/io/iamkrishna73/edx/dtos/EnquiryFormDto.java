package io.iamkrishna73.edx.dtos;

import lombok.Data;

@Data
public class EnquiryFormDto {
    private String studentName;

    private Long contactNumber;

    private String classMode;

    private String course;

    private String enquiryStatus;
}
