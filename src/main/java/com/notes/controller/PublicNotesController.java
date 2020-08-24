package com.notes.controller;

import com.notes.model.Note;
import com.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

/**
 *
 * Public Notes Controller.
 *
 * Provides a web-page which is populated only by published notes by other Users,
 * different from the current logged-in User. As with all other pages of this type,
 * these notes may be opened from here via the Note Editor.
 *
 * */

@Controller
public final class PublicNotesController {

    @Autowired
    private NoteService noteService;

    @ModelAttribute("publishedNotesList")
    public Collection<Note> getPublishedNotesList() {
        return noteService
                .findAllByPublishedAndNotCreatedByCurrentUser();
    }

    @ModelAttribute("noteForm")
    public Note getNoteForm() {
        return new Note();
    }

    @GetMapping("/public")
    public String publicNotes() {
        return "public";
    }
}