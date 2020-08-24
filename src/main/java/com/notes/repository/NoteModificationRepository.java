package com.notes.repository;

import com.notes.model.NoteModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteModificationRepository extends JpaRepository<NoteModification, UUID> {
}
