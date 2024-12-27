package io.iamkrishna73.edx.dtos.response;

import lombok.Data;


@Data
public class EnquiryRequest {
    private String studentName;
    private String course;
    private long contactNumber;
   // private LocalDate enquiryDate;
    private String classMode;
    private String enquiryStatus;
}
