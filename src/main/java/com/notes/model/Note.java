package com.notes.model;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * A Note Entity.
 *
 * Unique by its 128-bit GUID and also by the (title, creator) value-pair.
 * The latter is used for many look-ups by this application when attaching front-end
 * received data to the Persistence Context.
 *
 * Full-Auditing information (CreatedBy, CreatedOn, LastModifiedBy, LastModifiedOn)
 * is automatically stored on the full-update (one which includes the content field) / save of this Entity
 *
 * More details about specific field can be found below.
 *
 * */

@Entity
@Table(name ="note",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = { "title", "creator_id" }) })
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public final class Note extends Identifiable {

    /**
     *
     * Note titles are up to 50 characters long and are unique for any
     * given User / Note creator. This field cannot be updated.
     *
     * */

    @Column(name ="title",
            columnDefinition = "VARCHAR(50)",
            nullable = false,
            updatable = false)
    private String title;

    /**
     *
     * Note content may be any non-null / 0-length text.
     * A restriction is imposed by the NoteValidator which
     * states that note content should differ between successive versions,
     * e.g Version 1 -> Version 2, however the content of Version 1 may be
     * later restored in Version 3.
     *
     * */

    @Column(name = "content",
            columnDefinition = "TEXT",
            nullable = false)
    private String content;

    /**
     *
     * Boolean-Type. Specifies that a Note Entity is archived.
     * If a Note is archived, its content may NOT be modified in any way,
     * including publishing it.
     *
     * */

    @Column(name = "archived",
            columnDefinition = "BOOLEAN",
            nullable = false)
    private boolean archived;

    /**
     *
     * Boolean-Type. Specifies that a Note Entity is published.
     * If a Note is published, it may be viewed by Users other than
     * its creator and its content (not the title, however) can be modified by them.
     *
     * */

    @Column(name = "published",
            columnDefinition = "BOOLEAN",
            nullable = false)
    private boolean published;

    /**
     *
     * The current / last version of the Note. Each successive Note version is
     * incremented by one (1).
     *
     * */

    @Column(name = "version",
            columnDefinition = "INT",
            nullable = false)
    private int version;

    /**
     *
     * The User who created the Note. Audited automatically.
     * Never updatable.
     *
     * */

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id",
                referencedColumnName = "id",
                nullable = false,
                updatable = false)
    @CreatedBy
    private User creator;

    /**
     *
     * The time at which this note was created. Audited automatically.
     * Never updatable.
     *
     * */

    @Column(name = "created_timestamp",
            columnDefinition = "DATETIME",
            nullable = false,
            updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    private LocalDateTime createdTimestamp;


    /**
     *
     * The last User who modified this note (i.e. its content).
     * Audited automatically.
     *
     * */

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "last_modifier_id",
            referencedColumnName = "id",
            nullable = false)
    @LastModifiedBy
    private User lastModifier;

    /**
     *
     * The time at which this Note was last modified (i.e. its content).
     * Audited automatically.
     *
     * */

    @Column(name = "last_modified_timestamp",
            columnDefinition = "DATETIME",
            nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    private LocalDateTime lastModifiedTimestamp;

    /**
     *
     * A list of all the modification performed
     * on this Note and who performed them.
     * Non-null when the Note's version is > 1.
     *
     * */

    @OneToMany(mappedBy = "note")
    @OrderBy("version ASC")
    private List<NoteModification> noteModifications;

    public Note() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content; }

    public void setContent(final String content) { this.content = content; }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(final boolean archived) {
        this.archived = archived;
    }

    public boolean isPublished() { return published; }

    public void setPublished(final boolean published) { this.published = published; }

    public int getVersion() { return version; }

    public void setVersion(final int version) { this.version = version; }

    public User getCreator() { return creator; }

    public void setCreator(final User creator) { this.creator = creator; }

    public LocalDateTime getCreatedTimestamp() { return createdTimestamp; }

    public void setCreatedTimestamp(final LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public User getLastModifier() { return lastModifier; }

    public void setLastModifier(final User lastModifier) { this.lastModifier = lastModifier; }

    public LocalDateTime getLastModifiedTimestamp() { return lastModifiedTimestamp; }

    public void setLastModifiedTimestamp(final LocalDateTime lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public List<NoteModification> getNoteModifications() { return noteModifications; }

    public void setNoteModifications(final List<NoteModification> noteModifications) {
        this.noteModifications = noteModifications;
    }
}
