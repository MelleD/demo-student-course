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

        Course savedCourse = courseRepository.save(course);
        student.setStudentCourse(Set.of(new StudentCourse().setCourse(savedCourse).setStudent(student)));
        Student savedStudent = studentRepository.save(student);

        studentService.mergeStudent(savedStudent.getId(), Set.of(savedCourse.getId()));

        assertThat(studentCourseRepository.findAll()).hasSize(1);
    }

}
