package com.notes.service;

import com.notes.model.Note;
import com.notes.model.User;

import java.util.Collection;
import java.util.Optional;

public interface NoteService {

    void save(Note note);
    Collection<Note> findAllByNonArchivedAndCreatedByCurrentUser();
    Collection<Note> findAllByArchivedAndCreatedByCurrentUser();
    Collection<Note> findAllByPublishedAndNotCreatedByCurrentUser();
    Optional<Note> findByTitleAndCreatedByCurrentUser(String title);
    Optional<Note> findByTitleAndCreator(String title, User creator);
    void updateContentByTitleAndCreator(Note note);
    void archiveByTitleAndCurrentUser(String title);
    void publishByTitleAndCurrentUser(String title);
}
