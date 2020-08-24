package com.notes.component;

import com.notes.exception.ArchivedNoteModificationAttemptException;
import com.notes.model.Note;
import org.springframework.stereotype.Component;

/**
 *
 * Utility Bean for Note-related assertions.
 * Failure in any one of these results in an
 * appropriate exception being thrown. The name
 * of said extension can usually be inferred from
 * the assertion method being called.
 *
 * */

@Component
public final class NoteAsserterImpl implements NoteAsserter {

    @Override
    public void assertNonArchivedPriorToModification(final Note note) {
        if (note.isArchived())
            throw new ArchivedNoteModificationAttemptException(note);
    }
}
