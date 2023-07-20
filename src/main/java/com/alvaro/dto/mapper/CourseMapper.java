package com.alvaro.dto.mapper;

import org.springframework.stereotype.Component;

import com.alvaro.dto.CourseDTO;
import com.alvaro.enums.Category;
import com.alvaro.model.Course;

import ch.qos.logback.core.status.Status;

@Component
public class CourseMapper {

	public CourseDTO toDTO(Course course) {
		if(course == null) {
			return null;
		}
		return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue());
	}
	
	public Course toEntity(CourseDTO courseDTO) {
		if(courseDTO == null) {
			return null;
		}
		
		Course course = new Course();
		if(courseDTO.id() != null) {
			course.setId(courseDTO.id());
		}
		course.setName(courseDTO.name());
		course.setCategory(convertCategory(courseDTO.category()));
		
		return course;
	}
	
	public Category convertCategory(String value) {
		if(value == null) {
			return null;
		}
		return switch (value) {
		case "Front-end" -> Category.FRONT_END;
		case "Back-end" -> Category.BACK_END;
		default -> throw new IllegalArgumentException("Categoria inválida:" + value);
		};
	}
}
