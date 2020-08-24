package com.notes.exception;

import com.notes.model.User;

/**
 *
 * An exception for failed Note loop-ups via a title & creator (a User).
 *
 * Should propagate to the NotesApiRestController and be transformed into a
 * BadRequest HttpStatus.
 *
 * */

public final class NoteNotFoundException extends RuntimeException {

    private final String title;
    private final User user;

    public NoteNotFoundException(final String title, final User user) {
        super("A Note with a title: \"" +
                title + "\" and a User: \"" +
                user + "\" was not discovered via DB Query.");

        this.title = title;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }
}
