package io.iamkrishna73.edx.runner;

import io.iamkrishna73.edx.entities.CourseEntity;
import io.iamkrishna73.edx.entities.StatusEntity;
import io.iamkrishna73.edx.repos.CourseRepository;
import io.iamkrishna73.edx.repos.StatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {
    private final CourseRepository courseRepository;
    private final StatusRepository enquiryStatusRepository;

    public DataLoader(CourseRepository courseRepository, StatusRepository enquiryStatusRepository) {
        this.courseRepository = courseRepository;
        this.enquiryStatusRepository = enquiryStatusRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        courseRepository.deleteAll();
        CourseEntity c1 = new CourseEntity();
        c1.setCourseName("Java");

        CourseEntity c2 = new CourseEntity();
        c2.setCourseName("Python");

        CourseEntity c3 = new CourseEntity();
        c3.setCourseName("DevOps");

        CourseEntity c4 = new CourseEntity();
        c4.setCourseName("AWS");

        courseRepository.saveAll(Arrays.asList(c1,c2,c3,c4));

        enquiryStatusRepository.deleteAll();

        StatusEntity e1 = new StatusEntity();
        e1.setStatusName("New");
        StatusEntity e2 = new StatusEntity();
        e2.setStatusName("Enrolled");
        StatusEntity e3 = new StatusEntity();
        e3.setStatusName("Lost");

        enquiryStatusRepository.saveAll(Arrays.asList(e1,e2,e3));
    }
}
