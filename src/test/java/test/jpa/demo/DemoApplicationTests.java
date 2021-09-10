package test.jpa.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


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

        Student student = new Student();
        Course course = new Course();
        Course course2 = new Course();
        Course course3 = new Course();

        Course savedCourse1 = courseRepository.save(course);
        Course savedCourse2 = courseRepository.save(course2);
        Course savedCourse3 = courseRepository.save(course3);
        courseRepository.save(course2);
        student.setStudentCourse(Set.of(new StudentCourse().setCourse(savedCourse1).setStudent(student), new StudentCourse().setCourse(savedCourse3).setStudent(student)));
        Student savedStudent = studentRepository.save(student);

        studentService.mergeStudent(savedStudent.getId(), Set.of(savedCourse1.getId(), savedCourse2.getId()));

        assertThat(studentCourseRepository.findAll()).hasSize(2);
    }

}
