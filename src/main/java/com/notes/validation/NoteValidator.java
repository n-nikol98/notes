package com.notes.validation;

import com.notes.component.CurrentUserEntityProvider;
import com.notes.model.Note;
import com.notes.model.User;
import com.notes.service.NoteService;
import com.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * Validates incoming front-end Note Entities.
 *
 * This validator adapts depending on whether or not
 * a Note Entity's creator field is non-null, i.e.
 * if the Note is being created (null field) or updated
 * (non-null) field. The presence / absence of the creator
 * field is enforced directly in the NotesApiRestController.
 *
 * The following restrictions are imposed:
 *  (1) Not-Null / Blank: 'title' field
 *  (2) Not-Null / Blank: 'content' field
 *  (3) <= 50 Characters: 'title' field
 *  (4) 'title' field is unique for all Notes created by the current user, if
 *  this Note is being saved.
 *  (5) 'content' field is different from the current 'content' field of this Note
 *  (i.e. of the last version of this Note) if it is being updated.
 *
 * */

@Component
public final class NoteValidator implements Validator {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserEntityProvider currentUserEntityProvider;

    @Override
    public boolean supports(final Class<?> aClass) {
        return Note.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final Note note = (Note) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");

        final String noteTitle = note.getTitle();

        if (noteTitle.length() > 50)
            errors.rejectValue("title", "Size.noteForm.title");

        final User noteCreator = note.getCreator();
        final User currentUserEntity =
                currentUserEntityProvider.getCurrentUserEntity();
        User noteCreatorEntity;

        if (Objects.isNull(noteCreator))
            noteCreatorEntity = currentUserEntity;
        else {
            final String noteCreatorUsername = noteCreator.getUsername();
            noteCreatorEntity =
                    userService.findByUsername(noteCreatorUsername)
                            .orElseThrow(() ->
                                    new UsernameNotFoundException(noteCreatorUsername));
        }

        final Optional<Note> optionalNoteEntity = noteService.findByTitleAndCreator(
                noteTitle, noteCreatorEntity);

        if (optionalNoteEntity.isPresent()) {
            if (Objects.isNull(noteCreator))
                errors.rejectValue("title", "Duplicate.noteForm.title");
            else if (optionalNoteEntity.get().getContent().equals(note.getContent()))
                errors.rejectValue("content", "Duplicate.noteForm.content");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "NotEmpty");
    }
}
