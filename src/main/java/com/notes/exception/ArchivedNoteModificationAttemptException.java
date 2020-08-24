package com.notes.exception;

import com.notes.model.Note;

/**
 *
 * A generic exception for all publication / content modification attempts
 * which are made for an archived Note. Should propagate directly to the
 * NotesApiRestController and be processed into a BadRequest HtmlStatus.
 *
 * Thrown by the NoteAsserter.
 *
 * */

public final class ArchivedNoteModificationAttemptException extends RuntimeException {

    private final Note note;

    public ArchivedNoteModificationAttemptException(final Note note) {
        super("This note is archived and cannot be" +
                "modified (this includes publishing it).");

        this.note = note;
    }

    public Note getNote() { return note; }
}
