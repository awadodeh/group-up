package app.group.up.repository.search;

import app.group.up.domain.Circle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Circle entity.
 */
public interface CircleSearchRepository extends ElasticsearchRepository<Circle, Long> {
}
