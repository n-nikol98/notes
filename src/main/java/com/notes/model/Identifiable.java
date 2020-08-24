package com.notes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 *
 * Super-class which provides a generated 128-bit GUID for all Jpa Entities, which
 * are defined in the model package.
 *
 * */

@MappedSuperclass
abstract class Identifiable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id",
            columnDefinition = "BINARY(16)")
    @JsonProperty
    private UUID id;

    Identifiable() {}

    public UUID getId() {
        return id;
    }
}