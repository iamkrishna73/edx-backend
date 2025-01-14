package io.iamkrishna73.edx.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;

}
