package com.notes.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;


/**
 *
 * A User Entity.
 *
 * For simplicity, this class is also merged with Spring Security's
 * User, so that it may be used in both its context and the Persistence Context.
 *
 * At the current point of development, this Entity stores a username String that is no-longer
 * that 20 Characters (this is enforced by the UserValidator) and a salt-hashed
 * 60-byte password.
 *
 * Also, via @OneToMany mappings, the created & lastModified Notes by this User may be accessed through
 * this class. Additionally, the created NoteModifications via this User may be accessed through this class.
 *
 * As of the present moment, GrantedAuthorities are not used by this application and the presence of this
 * field in this class is merely mandated by Spring Security.
 *
 * */

@Entity
@Table(name = "user")
public final class User extends Identifiable implements UserDetails {

    @Column(name = "username",
            columnDefinition = "VARCHAR(20)",
            nullable = false,
            unique = true,
            updatable = false)
    private String username;

    @Column(name = "password",
            columnDefinition = "BINARY(60)",
            nullable = false)
    private String password;

    @OneToMany(mappedBy = "creator")
    private Collection<Note> createdNotes;

    @OneToMany(mappedBy = "lastModifier")
    private Collection<Note> lastModifiedNotes;

    @OneToMany(mappedBy = "creator")
    private Collection<NoteModification> noteModifications;

    @Transient
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(final Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Collection<Note> getCreatedNotes() { return createdNotes; }

    public void setCreatedNotes(final Collection<Note> createdNotes) {
        this.createdNotes = createdNotes;
    }

    public Collection<Note> getLastModifiedNotes() { return lastModifiedNotes; }

    public void setLastModifiedNotes(final Collection<Note> lastModifiedNotes) {
        this.lastModifiedNotes = lastModifiedNotes;
    }

    public Collection<NoteModification> getNoteModifications() { return noteModifications; }

    public void setNoteModifications(final Collection<NoteModification> noteModifications) {
        this.noteModifications = noteModifications;
    }
}
