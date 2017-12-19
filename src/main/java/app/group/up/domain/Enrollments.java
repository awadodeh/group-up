package app.group.up.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Enrollments.
 */
@Entity
@Table(name = "enrollments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enrollments")
public class Enrollments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "circle_id")
    private String circleId;

    @ManyToOne
    private Circle circle;

    @ManyToOne
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public Enrollments personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCircleId() {
        return circleId;
    }

    public Enrollments circleId(String circleId) {
        this.circleId = circleId;
        return this;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public Circle getCircle() {
        return circle;
    }

    public Enrollments circle(Circle circle) {
        this.circle = circle;
        return this;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Person getPerson() {
        return person;
    }

    public Enrollments person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enrollments enrollments = (Enrollments) o;
        if (enrollments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enrollments{" +
            "id=" + getId() +
            ", personId='" + getPersonId() + "'" +
            ", circleId='" + getCircleId() + "'" +
            "}";
    }
}
