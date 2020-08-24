package com.notes.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 *
 * A NoteModification Entity.
 *
 * These are created for all Notes with a version > 1.
 * They store the the information of all modifications done
 * to to a Note (i.e. to its content field).
 *
 * Also, they store who created a given modification and at what time.
 * The aforementioned values are based on the LastModifiedBy and LastModifiedOn
 * fields of a Note prior to its update with new content.
 *
 * */

@Entity
@Table(name = "note_modification")
public final class NoteModification extends Identifiable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_id",
            referencedColumnName = "id",
            updatable = false)
    private Note note;

    @Column(name = "content",
            nullable = false,
            updatable = false)
    private String content;

    @Column(name = "version",
            columnDefinition = "INT",
            nullable = false,
            updatable = false)
    private int version;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id",
                referencedColumnName = "id",
                updatable = false)
    private User creator;

    @Column(name = "created_timestamp",
            columnDefinition = "DATETIME",
            nullable = false,
            updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTimestamp;

    public Note getNote() {
        return note;
    }

    public void setNote(final Note note) { this.note = note; }

    public String getContent() { return content; }

    public void setContent(final String content) { this.content = content; }

    public int getVersion() { return version; }

    public void setVersion(final int version) { this.version = version; }

    public User getCreator() { return creator; }

    public void setCreator(final User creator) { this.creator = creator; }

    public LocalDateTime getCreatedTimestamp() { return createdTimestamp; }

    public void setCreatedTimestamp(final LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
