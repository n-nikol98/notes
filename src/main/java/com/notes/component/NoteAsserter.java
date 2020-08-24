package com.notes.component;

import com.notes.model.Note;

public interface NoteAsserter {

    void assertNonArchivedPriorToModification(Note note);
}
