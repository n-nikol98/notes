package com.notes.controller;

import com.notes.model.Note;
import com.notes.service.NoteService;
import com.notes.service.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * Note-Editor Controller.
 *
 * The Note-Editor may be accessed ONLY via a POST request, which should
 * include a fully populated NoteForm (excluding it's Persistence Context Id).
 *
 * Additionally, an editor mode should be specified in the request parameters,
 * so that the page may be setup correctly.
 *
 * The web-page supplied by this controller relies heavily on JScript and adapts dynamically
 * to the two inputs, which were mentioned above. Additionally, it is also the only web-page which
 * accesses the NotesApiRestController for the alteration of Note data.
 *
 * */
@Controller
public final class NoteEditorController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @ModelAttribute("noteForm")
    public Note getNoteForm() { return new Note(); }

    @GetMapping("/note-editor")
    public String noteEditor() {
        return "redirect:/";
    }

   @PostMapping("/note-editor")
    public String loadNoteWithEditor(@ModelAttribute("noteForm") final Note noteForm,
                                     @RequestParam(defaultValue = "view") final String mode,
                                     final Model model) {
       System.out.println(StringEscapeUtils.escapeEcmaScript(noteForm.getContent()));
        model.addAttribute("editorMode", mode);
        model.addAttribute("noteObject", noteForm);

        return "note-editor";
    }
}
