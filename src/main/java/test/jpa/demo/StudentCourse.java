package test.jpa.demo;

import javax.persistence.*;

@Entity
@Table
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Long getId() {
        return id;
    }

    public StudentCourse setId(Long id) {
        this.id = id;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public StudentCourse setStudent(Student student) {
        this.student = student;
        return this;
    }

    public Course getCourse() {
        return course;
    }

    public StudentCourse setCourse(Course course) {
        this.course = course;
        return this;
    }
}