package test.jpa.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Test
	void testMerge() {

		final Student student = new Student();
		final Course course = new Course();
		final Course course2 = new Course();
		final Course course3 = new Course();

		final Course savedCourse1 = courseRepository.save(course);
		final Course savedCourse2 = courseRepository.save(course2);
		final Course savedCourse3 = courseRepository.save(course3);
		courseRepository.save(course2);

		final Set<StudentCourse> coursesStudentCourses = new HashSet<>();
		coursesStudentCourses.add(new StudentCourse().setCourse(savedCourse1).setStudent(student));
		coursesStudentCourses.add(new StudentCourse().setCourse(savedCourse2).setStudent(student));
//		coursesStudentCourses.add(new StudentCourse().setCourse(savedCourse3).setStudent(student));

		student.setStudentCourse(coursesStudentCourses);

		final Student savedStudent = studentRepository.save(student);

		studentService.mergeStudent(savedStudent.getId(), Set.of(savedCourse1.getId(), savedCourse2.getId()));

		assertThat(studentCourseRepository.findAll()).hasSize(2);
	}

}
