package test.jpa.demo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class StudentCourse extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

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