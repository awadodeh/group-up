package app.group.up.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import app.group.up.domain.enumeration.Language;

/**
 * A EnrollmentHistory.
 */
@Entity
@Table(name = "enrollment_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enrollmenthistory")
public class EnrollmentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @OneToOne
    @JoinColumn(unique = true)
    private Circle circle;

    @OneToOne
    @JoinColumn(unique = true)
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public EnrollmentHistory language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Circle getCircle() {
        return circle;
    }

    public EnrollmentHistory circle(Circle circle) {
        this.circle = circle;
        return this;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Person getPerson() {
        return person;
    }

    public EnrollmentHistory person(Person person) {
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
        EnrollmentHistory enrollmentHistory = (EnrollmentHistory) o;
        if (enrollmentHistory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentHistory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentHistory{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
