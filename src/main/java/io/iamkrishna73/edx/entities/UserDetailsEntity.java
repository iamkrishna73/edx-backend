package io.iamkrishna73.edx.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "User_details")
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;
    private String username;

   // @Email(message = "Invalid email")
    private String email;
    private String password;
    private Long phoneNumber;
    private String accountStatus;
    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentEnquiryEntity> enquiries;
}
