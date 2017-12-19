package app.group.up.repository.search;

import app.group.up.domain.EnrollmentHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EnrollmentHistory entity.
 */
public interface EnrollmentHistorySearchRepository extends ElasticsearchRepository<EnrollmentHistory, Long> {
}
