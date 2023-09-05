package com.alvaro.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alvaro.Repository.CourseRepository;
import com.alvaro.dto.CourseDTO;
import com.alvaro.dto.CoursePageDTO;
import com.alvaro.dto.mapper.CourseMapper;
import com.alvaro.exception.RecordNotFoundException;
import com.alvaro.model.Course;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;


@Validated
@Service
@AllArgsConstructor
public class CourseService {

	  private final CourseRepository courseRepository;
	  private final CourseMapper courseMapper;

	  public CoursePageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
		  Page<Course> pageCourse = courseRepository.findAll(PageRequest.of(page, pageSize));
		  List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());
			return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
	  }  
	/*public List<CourseDTO> list() {
		return courseRepository.findAll()
				.stream()
				.map(courseMapper::toDTO)
				.collect(Collectors.toList());
	}*/

	public CourseDTO findById(@NotNull @Positive Long id) {
		return courseRepository.findById(id).map(courseMapper::toDTO)
				.orElseThrow(() -> new RecordNotFoundException(id));

	}

	public CourseDTO create(@Valid @NotNull CourseDTO course) {
		return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
	}

	public CourseDTO update(@NotNull @Positive Long id, @Valid  CourseDTO course) {
		return courseRepository.findById(id).map(recordFound -> {
			recordFound.setName(course.name());
			recordFound.setCategory(courseMapper.convertCategory(course.category()));
			return courseMapper.toDTO(courseRepository.save(recordFound));
		}).orElseThrow(() -> new RecordNotFoundException(id));
	}

	public void delete(@NotNull @Positive Long id) {
		courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
	}
}