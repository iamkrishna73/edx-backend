package io.iamkrishna73.edx.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "enquiry_status")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;
    private String statusName;
}
