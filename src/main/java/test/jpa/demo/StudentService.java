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


        Set<StudentCourse> currentStudentCourse = student.getStudentCourse().stream().filter(course -> courseIds.contains(course.getCourse().getId())).collect(Collectors.toSet());
        Set<Long> currentCourseIds = currentStudentCourse.stream().map(studentCourse -> studentCourse.getCourse().getId()).collect(Collectors.toSet());

        //TODO what is the best merge strategy
        Set<StudentCourse> mergeCourses = courses.stream().filter(course -> !currentCourseIds.contains(course.getId())).map(course -> new StudentCourse().setCourse(course).setStudent(student)).collect(Collectors.toSet());
        mergeCourses.addAll(currentStudentCourse);
        student.setStudentCourse(mergeCourses);


        return studentRepository.save(student);
    }
}
