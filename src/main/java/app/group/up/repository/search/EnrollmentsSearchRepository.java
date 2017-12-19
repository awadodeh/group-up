package app.group.up.repository.search;

import app.group.up.domain.Enrollments;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Enrollments entity.
 */
public interface EnrollmentsSearchRepository extends ElasticsearchRepository<Enrollments, Long> {
}
