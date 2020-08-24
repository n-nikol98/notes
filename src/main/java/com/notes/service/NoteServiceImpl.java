package com.notes.service;

import com.notes.component.CurrentUserEntityProvider;
import com.notes.component.NoteAsserter;
import com.notes.exception.NoteNotFoundException;
import com.notes.model.Note;
import com.notes.model.User;
import com.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteModificationService noteModificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUserEntityProvider currentUserEntityProvider;

    @Autowired
    private NoteAsserter noteAsserter;

    /**
     *
     * Saves a Note to the DB.
     *
     * The following are imposed:
     *  (1) Note is non-archived.
     *  (2) Note is non-published.
     *  (3) Note Version = 1
     *
     * */

    @Override
    @Transactional
    public void save(final Note note) {
        note.setArchived(false);
        note.setPublished(false);
        note.setVersion(1);

        noteRepository.save(note);
    }

    /**
     *
     * Self-explanatory
     *
     * */

    @Override
    public Collection<Note> findAllByNonArchivedAndCreatedByCurrentUser() {
        return noteRepository.findAllByNonArchivedAndCreatedBy(
                currentUserEntityProvider.getCurrentUserEntity());
    }

    /**
     *
     * Self-explanatory
     *
     * */

    @Override
    public Collection<Note> findAllByArchivedAndCreatedByCurrentUser() {
        return noteRepository.findAllByArchivedAndCreatedBy(
                currentUserEntityProvider.getCurrentUserEntity());
    }

    /**
     *
     * Self-explanatory
     *
     * */

    @Override
    public Collection<Note> findAllByPublishedAndNotCreatedByCurrentUser() {
        return noteRepository.findAllByPublishedAndNotCreatedBy(
                currentUserEntityProvider.getCurrentUserEntity());
    }

    /**
     *
     * Self-explanatory
     *
     * */

    @Override
    public Optional<Note> findByTitleAndCreatedByCurrentUser(final String title) {
        return findByTitleAndCreator(title,
                currentUserEntityProvider.getCurrentUserEntity());
    }

    /**
     *
     * Self-explanatory
     *
     * */

    @Override
    public Optional<Note> findByTitleAndCreator(final String title, final User creator) {
        return noteRepository.findByTitleAndCreator(title, creator);
    }

    /**
     *
     * Updates a Note's content based on its title and the creator.
     *
     * If the creator is not found as an User: @throws UsernameNotFoundException. This should
     * propagate to the NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * If the Note is not found: @throws NoteNotFoundException. This should propagate to the
     * NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * If the Note is archived: @throws ArchivedNoteModificationAttemptException. This should
     * propagate to the NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * The following is imposed:
     *  (1) Updated Note Version = Note Version + 1
     *
     * */

    @Override
    @Transactional
    public void updateContentByTitleAndCreator(final Note note) {
        final String noteTitle = note.getTitle();
        final User noteCreator = note.getCreator();
        final String noteCreatorUsername = noteCreator.getUsername();

        //Attempt to attach arriving Note to persistence context;
        final Note noteEntity = findByTitleAndCreator(noteTitle,
                userService.findByUsername(noteCreatorUsername)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(noteCreatorUsername)))
                .orElseThrow(() -> new NoteNotFoundException(noteTitle, noteCreator));

        noteAsserter.assertNonArchivedPriorToModification(noteEntity);

        noteModificationService.createAndSaveBasedOnNote(noteEntity);

        noteEntity.setContent(note.getContent());
        noteEntity.setVersion(noteEntity.getVersion() + 1);

        noteRepository.save(noteEntity);
    }

    /**
     *
     * Archives a Note based on its title and the creator = the current logged-in User.
     *
     * If the Note is not found: @throws NoteNotFoundException. This should propagate to the
     * NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * */

    @Override
    @Transactional
    public void archiveByTitleAndCurrentUser(final String title) {
        noteRepository.archiveById(findByTitleAndCreatedByCurrentUser(title)
                .orElseThrow(() -> new NoteNotFoundException(title,
                        currentUserEntityProvider.getCurrentUserEntity()))
                .getId());
    }

    /**
     *
     * Published a Note based on its title and the creator = the current logged-in User.
     *
     * If the Note is not found: @throws NoteNotFoundException. This should propagate to the
     * NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * If the Note is archived: @throws ArchivedNoteModificationAttemptException. This should
     * propagate to the NotesApiRestController and be transformed into an appropriate BadRequest HtmlStatus.
     *
     * */

    @Override
    @Transactional
    public void publishByTitleAndCurrentUser(final String title) {
        final Note noteEntity = findByTitleAndCreatedByCurrentUser(title)
                .orElseThrow(() -> new NoteNotFoundException(title,
                        currentUserEntityProvider.getCurrentUserEntity()));

        noteAsserter.assertNonArchivedPriorToModification(noteEntity);

        noteRepository.publishById(noteEntity.getId());
    }
}
