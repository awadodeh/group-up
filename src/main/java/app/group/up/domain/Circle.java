package app.group.up.domain;

import app.group.up.service.util.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Circle entity is an group that will contain a group of people in order to save a money target.
 */
@Entity
@Table(name = "circle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "circle")
public class Circle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "circle_name", nullable = false)
    private String circleName;

    @NotNull
    @Column(name = "circle_worth", nullable = false)
    private Long circleWorth;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @NotNull
    @Column(name = "number_of_members", nullable = false)
    private Integer numberOfMembers;

    @OneToMany(mappedBy = "circle")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enrollments> enrollments = new HashSet<>();

    @Column(name = "created_date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate createdDate = LocalDate.now();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCircleName() {
        return circleName;
    }

    public Circle circleName(String circleName) {
        this.circleName = circleName;
        return this;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public Long getCircleWorth() {
        return circleWorth;
    }

    public Circle circleWorth(Long circleWorth) {
        this.circleWorth = circleWorth;
        return this;
    }

    public void setCircleWorth(Long circleWorth) {
        this.circleWorth = circleWorth;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Circle startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Circle endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOfMembers() {
        return numberOfMembers;
    }

    public Circle numberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
        return this;
    }

    public void setNumberOfMembers(Integer numberOfMembers) {
        this.numberOfMembers = numberOfMembers;
    }

    public Set<Enrollments> getEnrollments() {
        return enrollments;
    }

    public Circle enrollments(Set<Enrollments> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public Circle addEnrollments(Enrollments enrollments) {
        this.enrollments.add(enrollments);
        enrollments.setCircle(this);
        return this;
    }

    public Circle removeEnrollments(Enrollments enrollments) {
        this.enrollments.remove(enrollments);
        enrollments.setCircle(null);
        return this;
    }

    public void setEnrollments(Set<Enrollments> enrollments) {
        this.enrollments = enrollments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Circle circle = (Circle) o;
        if (circle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), circle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Circle{" +
            "id=" + getId() +
            ", circleName='" + getCircleName() + "'" +
            ", circleWorth='" + getCircleWorth() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", numberOfMembers='" + getNumberOfMembers() + "'" +
            "}";
    }
}
