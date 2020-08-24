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
 * Notes Controller.
 *
 * Provides the default web-page for this app, which is populated only by non-archived notes
 * for the current logged-in User. Additionally, it shows
 * which of these notes are published. As with all other pages of this type,
 * these notes may be opened from here via the Note Editor.
 *
 * */

@Controller
public final class NotesController {

    @Autowired
    private NoteService noteService;

    @ModelAttribute("nonArchivedNotesList")
    public Collection<Note> getNonArchivedNotesList() {
        return noteService.findAllByNonArchivedAndCreatedByCurrentUser();
    }

    @ModelAttribute("noteForm")
    public Note getNoteForm() {
        return new Note();
    }

    @GetMapping({"/", "/notes"})
    public String notes() {
        return "notes";
    }
}
