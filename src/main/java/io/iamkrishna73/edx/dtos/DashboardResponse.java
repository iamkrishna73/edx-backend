package io.iamkrishna73.edx.dtos;

import lombok.Data;

@Data
public class DashboardResponse {
    public Integer totalEnquiresCount;
    public Integer enrolledCount;
    private Integer lostEnquiresCount;
}
