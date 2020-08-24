package com.notes.controller;

import com.notes.component.JSONConstructor;
import com.notes.exception.ArchivedNoteModificationAttemptException;
import com.notes.exception.NoteNotFoundException;
import com.notes.model.Note;
import com.notes.model.User;
import com.notes.service.NoteService;
import com.notes.validation.NoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;


/**
 *
 * Contains the four (4) end-points which can be used by logged-in Users to
 * create / update Notes in anyway, specifically:
 *
 *  (1) via POST: /notes/api/save
 *  (2) via PUT: /notes/api/update
 *  (3) via PATCH: /notes/api/archive
 *  (4) via PATCH: /notes/api/publish
 *
 * All of the above-mentioned will produce JSON-Formatted error code, if a Bad Request
 * is made.
 *
 * More details can be found below.
 *
 * */

@RestController
public final class NotesApiRestController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteValidator noteValidator;

    @Autowired
    private JSONConstructor jsonConstructor;

    /**
     *
     * Saves an arriving Note for the current User.
     *
     * The following fields are required:
     *  (1) 'title'
     *  (2) 'content'
     *
     * If the Note 'creator' field is populated, a BadRequest HttpStatus is returned.
     * This is enforced as the NoteValidator uses this field to distinguish a Note being
     * created from one being updated.
     *
     * @return
     *  (1) HttpStatus.BAD_REQUEST if the 'creator' field is populated || Note validation fails
     *  (2) HttpStatus.INTERNAL_SERVER_ERROR if an unexpected exception occurs
     *  (3) HttpStatus.OK if the Note is successfully saved.
     *
     * */

    @PostMapping(value = "/notes/api/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> saveNoteForCurrentUser(@ModelAttribute("noteForm") final Note noteForm,
                                   final BindingResult bindingResult) {
        return checkNoteCreatorPresenceAndOptionallyGetFormattedBadRequestResponseEntityForVariableHttpRequest(
                noteForm.getCreator(), false, "POST")
                .orElseGet(() ->
                        validateNoteFormAndOptionallyExecuteNoteServiceActionAndGetAppropriateResponseEntity(
                noteForm, bindingResult, () -> noteService.save(noteForm)));

    }

    /**
     *
     * Updates an arriving Note.
     *
     * The following fields are required:
     *  (1) 'title'
     *  (2) 'content'
     *  (3) 'creator'
     *
     * If the Note 'creator' field is  not populated, a BadRequest HttpStatus is returned.
     * This is enforced as the NoteValidator uses this field to distinguish a Note being
     * created from one being updated. Additionally, as published Notes can be updated, it
     * is used to uniquely identify a Note via the value-pair { (1), (3) }
     *
     * By default the following exceptions are caught and a BadRequest is returned:
     *  (1) NoteNotFoundException
     *  (2) ArchivedNoteModificationAttemptException
     *  (3) UsernameNotFoundException
     *
     *  All other exceptions arriving here are unexpected and should result in a 500 Internal Server Error
     *  HttpStatus being returned to the end-User.
     *
     * @return
     *  (1) HttpStatus.BAD_REQUEST if the 'creator' field is not populated || Note validation fails ||
     *      one of the above-mentioned expected exceptions is caught.
     *  (2) HttpStatus.INTERNAL_SERVER_ERROR if an unexpected exception occurs
     *  (3) HttpStatus.OK if the Note is updated successfully.
     *
     * */

    @PutMapping(value = "/notes/api/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateNote(@ModelAttribute("noteForm") final Note noteForm,
                             final BindingResult bindingResult) {
        return checkNoteCreatorPresenceAndOptionallyGetFormattedBadRequestResponseEntityForVariableHttpRequest(
                noteForm.getCreator(), true, "PUT")
                .orElseGet(() ->
                {
                    try {
                        return
                                validateNoteFormAndOptionallyExecuteNoteServiceActionAndGetAppropriateResponseEntity(
                                        noteForm, bindingResult,
                                        () -> noteService.updateContentByTitleAndCreator(noteForm));
                    } catch (NoteNotFoundException | ArchivedNoteModificationAttemptException |
                            UsernameNotFoundException ex) {
                        return getFormattedBadRequestResponseEntityForException(ex);
                    }
                });
    }

    /**
     *
     * Archives an arriving Note, which is created by the current User.
     *
     * The following field is required:
     *  (1) 'title'
     *
     * By default the following exception is caught and a BadRequest is returned:
     *  (1) NoteNotFoundException
     *
     *  All other exceptions arriving here are unexpected and should result in a 500 Internal Server Error
     *  HttpStatus being returned to the end-User.
     *
     * @return
     *  (1) HttpStatus.BAD_REQUEST if the above-mentioned expected exception is caught.
     *  (2) HttpStatus.INTERNAL_SERVER_ERROR if an unexpected exception occurs
     *  (3) HttpStatus.OK if the Note is archived successfully.
     *
     * */

    @PatchMapping(value = "/notes/api/archive", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> archiveNoteForCurrentUser(@ModelAttribute("noteForm") final Note noteForm) {
        try {
            noteService.archiveByTitleAndCurrentUser(noteForm.getTitle());
        } catch (NoteNotFoundException ex) {
            return getFormattedBadRequestResponseEntityForException(ex);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * Publishes an arriving Note, which is created by the current User.
     *
     * The following field is required:
     *  (1) 'title'
     *
     * By default the following exceptions are caught and a BadRequest is returned:
     *  (1) NoteNotFoundException
     *  (2) ArchivedNoteModificationAttemptException
     *
     *  All other exceptions arriving here are unexpected and should result in a 500 Internal Server Error
     *  HttpStatus being returned to the end-User.
     *
     * @return
     *  (1) HttpStatus.BAD_REQUEST if one of the above-mentioned expected exceptions is caught.
     *  (2) HttpStatus.INTERNAL_SERVER_ERROR if an unexpected exception occurs
     *  (3) HttpStatus.OK if the Note is published successfully.
     *
     * */

    @PatchMapping(value = "/notes/api/publish", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> publishNoteForCurrentUser(@ModelAttribute("noteForm") final Note noteForm) {
        try {
            noteService.publishByTitleAndCurrentUser(noteForm.getTitle());
        } catch (NoteNotFoundException | ArchivedNoteModificationAttemptException ex) {
            return getFormattedBadRequestResponseEntityForException(ex);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    *
    * PRIVATE UTILITY FUNCTIONS
    *
    * */

    private ResponseEntity<String> getFormattedBadRequestResponseEntityForException(
            final Exception ex) {
        return new ResponseEntity<>(
                jsonConstructor.constructGenericJsonErrorObjectFromMessage(
                        ex.getMessage()).toString(), HttpStatus.BAD_REQUEST);
    }

    private Optional<ResponseEntity<String>>
    checkNoteCreatorPresenceAndOptionallyGetFormattedBadRequestResponseEntityForVariableHttpRequest(
            final User noteCreator, final boolean noteCreatorExpected, final String httpRequest) {
        if (noteCreatorExpected ? Objects.isNull(noteCreator) : Objects.nonNull(noteCreator))
            return Optional.of(new ResponseEntity<>(
                    jsonConstructor.constructGenericJsonErrorObjectFromMessage(
                            "noteForm.creator field must be " +
                                    (noteCreatorExpected ? "non-" : "") + "null " +
                                    "when issuing a " + httpRequest +
                                    " request to this end-point").toString(),
                    HttpStatus.BAD_REQUEST));

        return Optional.empty();
    }

    @FunctionalInterface
    private interface NoteServiceAction {
        void execute();
    }

    private ResponseEntity<String>
    validateNoteFormAndOptionallyExecuteNoteServiceActionAndGetAppropriateResponseEntity(
            final Note noteForm,
            final BindingResult bindingResult,
            final NoteServiceAction noteServiceAction) {
        noteValidator.validate(noteForm, bindingResult);

        if (bindingResult.hasErrors())
            return new ResponseEntity<>(
                    jsonConstructor.constructJsonFieldErrorsArrayFromBindingResult(bindingResult)
                            .toString(),
                    HttpStatus.BAD_REQUEST);

        noteServiceAction.execute();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     *
     * PRIVATE UTILITY FUNCTIONS END
     *
     * */
}
