package com.alvaro.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alvaro.dto.CourseDTO;
import com.alvaro.dto.LessonDTO;
import com.alvaro.enums.Category;
import com.alvaro.model.Course;

@Component
public class CourseMapper {

	public CourseDTO toDTO(Course course) {
		if(course == null) {
			return null;
		}
		List<LessonDTO> lessons = course.getLessons()
				.stream()
				.map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(),lesson.getYoutubeUrl()))
				.collect(Collectors.toList());
		
		return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(),
				lessons);
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
