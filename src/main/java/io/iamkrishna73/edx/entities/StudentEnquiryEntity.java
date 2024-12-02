package io.iamkrishna73.edx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;


@Entity
@Table(name="student_enquiries")
@Data
public class StudentEnquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enquiryId;

  //  private Integer studentEnquiryId;

    private String studentName;

    private Long studentPhone;

    private String classMode;

    private String courseName;

    private String enquiryStatus;

    @CreationTimestamp
    private LocalDate dateCreated;

    @UpdateTimestamp
    private LocalDate lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private UserDetailsEntity userDetails;
}
