package test.jpa.demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Student extends AbstractEntity {

	@OneToMany(mappedBy = "student", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	private final Set<StudentCourse> studentCourse = new HashSet<>();

	public Set<StudentCourse> getStudentCourse() {
		return studentCourse;
	}

	public void setStudentCourse(Set<StudentCourse> studentCourse) {
		this.studentCourse.clear();
		this.studentCourse.addAll(studentCourse);
		this.studentCourse.forEach(sCourse -> sCourse.setStudent(this));
	}
}