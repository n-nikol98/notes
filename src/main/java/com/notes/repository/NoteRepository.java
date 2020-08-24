package com.notes.repository;

import com.notes.model.Note;
import com.notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Helpful custom queries for CRUD operations on Note Entities.
 *
 * */
public interface NoteRepository extends JpaRepository<Note, UUID> {

    @Query("SELECT n FROM Note n WHERE archived = false AND creator = ?1")
    Collection<Note> findAllByNonArchivedAndCreatedBy(User user);

    @Query("SELECT n FROM Note n WHERE archived = true AND creator = ?1")
    Collection<Note> findAllByArchivedAndCreatedBy(User user);

    @Query("SELECT n FROM Note n WHERE published = true AND creator != ?1")
    Collection<Note> findAllByPublishedAndNotCreatedBy(User user);

    @Query("SELECT n FROM Note n WHERE title = ?1 AND creator = ?2")
    Optional<Note> findByTitleAndCreator(String title, User creator);

    @Query("UPDATE Note SET archived = true, published = false WHERE id = ?1")
    @Modifying
    void archiveById(UUID id);

    @Query("UPDATE Note SET published = true WHERE id = ?1")
    @Modifying
    void publishById(UUID id);
}
