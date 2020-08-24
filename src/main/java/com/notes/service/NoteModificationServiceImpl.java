package com.notes.service;

import com.notes.model.Note;
import com.notes.model.NoteModification;
import com.notes.repository.NoteModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoteModificationServiceImpl implements NoteModificationService {

    @Autowired
    private NoteModificationRepository noteModificationRepository;

    /**
     *
     * Saves a NoteModification, based on an existing Note.
     *
     * The content of the NoteModification = The content of the passed Note.
     * The Version of the NoteModification = The version of the passed Note.
     * The Creator of the NoteModification = The last modifier of the passed Note.
     * TRhe CreatedTimestamp of the NoteModification = The last modifiedTimestamp of the passed Note.
     *
     * */

    @Override
    @Transactional
    public void createAndSaveBasedOnNote(final Note note) {
        final NoteModification noteModification = new NoteModification();

        noteModification.setNote(note);
        noteModification.setContent(note.getContent());
        noteModification.setVersion(note.getVersion());
        noteModification.setCreator(note.getLastModifier());
        noteModification.setCreatedTimestamp(note.getLastModifiedTimestamp());

        noteModificationRepository.save(noteModification);
    }
}
