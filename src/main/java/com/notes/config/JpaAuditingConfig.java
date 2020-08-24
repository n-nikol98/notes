package com.notes.config;

import com.notes.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 *
 * Jpa Auditing Config.
 *
 * Auditing is used by this application currently only for the
 * Note Entity. As should be expected the @CreatedBy & @LastModifiedBy
 * fields are populated with the Entity of the current logged in User, which
 * is what this config sets up.
 *
 * */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> Optional.ofNullable(
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
    }
}
