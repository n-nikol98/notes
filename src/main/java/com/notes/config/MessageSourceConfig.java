package com.notes.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 *
 * This config sets up a MessageSource, which is used by the JSONConstructor
 * to parse BindingResult errors in to their readable error messages,
 * as they are defined in classpath:validation.properties.
 *
 * */

@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:validation");
        messageSource.setCacheSeconds(10);

        return messageSource;
    }
}
