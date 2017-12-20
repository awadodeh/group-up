package app.group.up.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(app.group.up.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(app.group.up.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Blog.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Entry.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Entry.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Tag.class.getName() + ".entries", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Tag.class.getName() + ".persons", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Circle.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Circle.class.getName() + ".enrollments", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Person.class.getName() + ".payments", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Person.class.getName() + ".reviews", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Person.class.getName() + ".enrollments", jcacheConfiguration);
            cm.createCache(app.group.up.domain.Enrollments.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.EnrollmentHistory.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Review.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(app.group.up.domain.Person.class.getName() + ".circles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
