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
 * Archived Notes Controller.
 *
 * Provides a web-page which is populated only by archived notes
 * for the current logged-in User. As with all other pages of this type,
 * these notes may be opened from here via the Note Editor.
 *
 * */
@Controller
public final class NotesArchiveController {

    @Autowired
    private NoteService noteService;

    @ModelAttribute("archivedNotesList")
    public Collection<Note> getNonArchivedNotesList() {
        return noteService
                .findAllByArchivedAndCreatedByCurrentUser();
    }

    @ModelAttribute("noteForm")
    public Note getNoteForm() {
        return new Note();
    }

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }
}
