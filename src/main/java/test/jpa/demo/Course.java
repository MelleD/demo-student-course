package test.jpa.demo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<StudentCourse> studentCourse = new HashSet<>();

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
        this.studentCourse.clear();
        this.studentCourse.addAll(studentCourse);
    }
}