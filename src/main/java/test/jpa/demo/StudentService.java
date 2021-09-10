package test.jpa.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Student mergeStudent(Long studentId, Set<Long> courseIds) {
        Student student = studentRepository.findById(studentId).get();

        //check if exist
        List<Course> courses = courseRepository.findAllById(courseIds);
        if (courses.size() != courseIds.size()) {
            throw new EntityNotFoundException();
        }

        //TODO what is the best merge strategy
        Set<StudentCourse> mergeCourses = courses.stream().map(course -> new StudentCourse().setCourse(course).setStudent(student)).collect(Collectors.toSet());
        student.setStudentCourse(mergeCourses);

        studentRepository.findAll();
        courseRepository.findAll();

        return studentRepository.save(student);
    }
}
