package test.jpa.demo;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Transactional
	public Student mergeStudent(UUID studentId, Set<UUID> courseIds) {
		final Student student = studentRepository.findById(studentId).get();

		// check if exist
		final List<Course> courses = courseRepository.findAllById(courseIds);
		if (courses.size() != courseIds.size()) {
			throw new EntityNotFoundException();
		}

		final Set<StudentCourse> currentStudentCourse = student.getStudentCourse().stream()
				.filter(course -> courseIds.contains(course.getCourse().getId())).collect(Collectors.toSet());
		final Set<UUID> currentCourseIds = currentStudentCourse.stream()
				.map(studentCourse -> studentCourse.getCourse().getId()).collect(Collectors.toSet());

		// TODO what is the best merge strategy
		final Set<StudentCourse> mergeCourses = courses.stream()
				.filter(course -> !currentCourseIds.contains(course.getId()))
				.map(course -> new StudentCourse().setCourse(course).setStudent(student)).collect(Collectors.toSet());
		mergeCourses.addAll(currentStudentCourse);
		student.setStudentCourse(mergeCourses);

		return studentRepository.save(student);
	}
}
