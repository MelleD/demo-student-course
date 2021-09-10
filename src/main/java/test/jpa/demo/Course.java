package test.jpa.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<StudentCourse> studentCourse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<StudentCourse> getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(Set<StudentCourse> studentCourse) {
        this.studentCourse = studentCourse;
    }
}