package test.jpa.demo;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.PersistenceConstructor;

@MappedSuperclass
public class AbstractEntity {

	@Id
//	@GeneratedValue(generator = "UUID")
//	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id = UUID.randomUUID();

	public AbstractEntity() {

	}

	@PersistenceConstructor
	public AbstractEntity(UUID id) {
		this.id = id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		if (id == null) {
			return super.hashCode();
		}
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final AbstractEntity other = (AbstractEntity) obj;
		// unsaved objects are never equal
		if (id == null || other.getId() == null) {
			return false;
		}

		return Objects.equals(id, other.id);
	}

}