package com.alvaro.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alvaro.model.Course;


public interface CourseRepository extends JpaRepository<Course, Long> {

}
