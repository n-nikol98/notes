$(document).ready(function(){
    registerAjaxForm($('#archiveNoteForm'));
    registerAjaxForm($('#publishNoteForm'));
    registerAjaxForm($('#saveNoteForm'));
    registerAjaxForm($('#updateNoteForm'));

    setupNoteTitle();
    setupNoteContentTextArea();

    if (editorMode != 'create') {
        setNoteContentTextAreaValue(originalNoteContent);
    }

    setupNoteEditorButtons();
    setupNoteDetailsText();

    $("#noteTitleTextArea").keydown(function(e){
    if (e.keyCode == 13)
    {
        e.preventDefault();
    }
    });
});

function registerAjaxForm(form) {
    form.submit(function (e) {

        e.preventDefault();

        $.ajax({
            type: form.attr('method'),
            url: form.attr('action'),
            data: form.serialize(),
            success: function (data) {
                window.location.href = '/';
            },
            error: function(data) {
                $('.modal').modal('hide');
                $('.modal-backdrop').remove();

                $('#noteTitleError').html('');
                $('#noteContentErrorSpan').text('');

                var jsonString = data.responseText;
                var json = JSON.parse(jsonString);

                    $(json).each(function (index, fieldError) {
                        var field = fieldError.field;
                        var message = fieldError.message;

                        if (field == 'title') {
                            $('#noteTitleError').html(message);
                        }
                        else if (field == 'content') {
                            $('#noteContentErrorSpan').text(message);
                        }
                        else {
                            $('div#unexpectedErrorModalBody').text('An unexpected error occurred:\n\n' +
                                JSON.stringify(json));
                            $('#unexpectedErrorModal').modal('show');
                        }
                    });
            }
        });
    });
}

function setupNoteTitle() {
    if (editorMode != 'create') {
        $('#noteTitleTextAreaContainer').css('display', 'none');
    }
    else {
        $('#noteTitleSpan').css('display', 'none');
    }
}

function setupNoteContentTextArea() {
    if (editorMode == 'view') {
        $('#noteContentTextAreaContainer').attr('class', '');
        $('#noteContentTextArea').attr('readonly', true);

        $('#noteContentErrorSpan').css('display', 'none');
    }
}

function setupNoteEditorButtons() {
    var saveButton = '#saveButton';
    var editButton = '#editButton';
    var archiveButton = '#archiveButton';
    var publishButton = '#publishButton';
    var versionHistoryButton = '#viewHistoryButton';

    /*
     * DISCLAIMER: I will spend another 50 hours debugging this page if
     * I change the code below. Please do not hate me.
     */

    /* GLOBAL BUTTON SETTINGS */

    $(saveButton).html('Save note');
    $(versionHistoryButton).html('View note history');

    /* GLOBAL BUTTON SETTINGS END */

    /* SAVE BUTTON */

    if (editorMode == 'view' || noteArchived == 'true') {
        $(saveButton).attr("disabled", true);
    }
    else if (editorMode == 'edit') {
        $(saveButton).attr("disabled", true);

        $('#noteContentTextArea').bind('input propertychange', function() {
          if (getNoteContentTextAreaValue().replace(/(\r\n|\n|\r)/gm, '\r\n')
                == originalNoteContent) {
             $(saveButton).attr("disabled", true);
          }
          else {
              $(saveButton).attr("disabled", false);
          }
        });

        $(document).on('click', saveButton, function() {
            $('#updateConfirmationModal').modal('show');
        });
    }
    else {
        $(document).on('click', saveButton, function() {
            $('#saveConfirmationModal').modal('show');
        });
    }

    /* SAVE BUTTON END */

    /* EDIT BUTTON */

    if (editorMode == 'create' || noteArchived == 'true') {
        $(editButton).html('Edit note');
        $(editButton).attr("disabled", true);
    }
    else if (editorMode == 'edit') {
        $(editButton).html('Cancel edit');
        $(document).on('click', editButton, function() {
            changesDiscardingButtonClicked(submitForm, 'openViewModeForm');
        });
    }
    else {
        $(editButton).html('Edit note');
        $(document).on('click', editButton, function() {
            submitForm('openEditModeForm');
        });
    }

    /* EDIT BUTTON END */

    /* ARCHIVE BUTTON */

    if (noteCreatorUsername != currentUser || editorMode == 'create' || (editorMode == 'edit' && noteArchived != 'true')) {
        $(archiveButton).html('Archive note');
        $(archiveButton).attr("disabled", true);
    }
    else if (noteArchived == 'true') {
        $(archiveButton).html('Archived');
        $(archiveButton).attr("disabled", true);
    }
    else {
        $(archiveButton).html('Archive note');
        $(document).on('click', archiveButton, function() {
            $('#archiveConfirmationModal').modal('show');
        });
    }

    /* ARCHIVE BUTTON END */

    /* PUBLISH BUTTON */

    if (noteArchived == 'true' || editorMode == 'create' || (editorMode == 'edit' && notePublished != 'true')) {
        $(publishButton).html('Publish note');
        $(publishButton).attr("disabled", true);
    }
    else if (notePublished == 'true') {
        $(publishButton).html('Published');
        $(publishButton).attr("disabled", true);
    }
    else {
        $(publishButton).html('Publish note');
        $(document).on('click', publishButton, function() {
            $('#publishConfirmationModal').modal('show');
        });
    }

    /* PUBLISH BUTTON END */

    /* VIEW HISTORY BUTTON */

    if (editorMode != 'view' || noteVersion == '1') {
        $(versionHistoryButton).attr("disabled", true);
    }
    else {
        $(document).on('click', versionHistoryButton, function() {
            $('#noteHistoryModal').modal('show');
        });
    }

    /* VIEW HISTORY BUTTON END */
}

function setupNoteDetailsText(){
    if (editorMode != 'create') {
        $('#noteDetailsTextCreatedBySpan').text('Created by: ');

        $('#noteDetailsTextCreatorUsernameSpan').text(noteCreatorUsername);
        $('#noteDetailsTextCreatorUsernameSpan').css('color', '#0066ff');
        $('#noteDetailsTextCreatorUsernameSpan').css('font-weight', 'bold');

        $('#noteDetailsTextCreatedOnSpan').text(' | Created on: ');

        $('#noteDetailsTextCreatedTimestampSpan').text(noteCreatedTimestamp);
        $('#noteDetailsTextCreatedTimestampSpan').css('font-weight', 'bold');

        if (noteVersion == '1') {
            $('#noteDetailsTextSpan').css('font-size', 'small');
        }
        else {
            $('#noteDetailsTextSpan').css('font-size', 'x-small');

            $('#noteDetailsTextLastModifiedBySpan').text(' | Last modified by: ');

            $('#noteDetailsTextLastModifierUsernameSpan').text(noteLastModifierUsername);
            $('#noteDetailsTextLastModifierUsernameSpan').css('color', '#0066ff');
            $('#noteDetailsTextLastModifierUsernameSpan').css('font-weight', 'bold');

            $('#noteDetailsTextLastModifiedOnSpan').text(' | Last modified on: ');

            $('#noteDetailsTextLastModifiedTimestampSpan').text(noteLastModifiedTimestamp);
            $('#noteDetailsTextLastModifiedTimestampSpan').css('font-weight', 'bold');
        }

        $('#noteDetailsTextVerSpan').text(' | Ver: ');

        $('#noteDetailsTextVersionSpan').text(noteVersion);
        $('#noteDetailsTextVersionSpan').css('font-weight', 'bold');
    }
}

function changesDiscardingButtonClicked(confirmDiscardChangesButtonOnClickFunction, optionalOnClickArg){
    if (editorMode == 'view' ||
            (editorMode == 'edit' && originalNoteContent ==
                getNoteContentTextAreaValue().replace(/(\r\n|\n|\r)/gm, '\r\n')) ||
            (editorMode == 'create' && '' == getNoteTitleTextAreaValue() &&
                '' == getNoteContentTextAreaValue())) {
        confirmDiscardChangesButtonOnClickFunction(optionalOnClickArg);
    }
    else {
        $(document).on('click', '#confirmDiscardChangesButton', function() {
            confirmDiscardChangesButtonOnClickFunction(optionalOnClickArg);
        });

        $('#discardConfirmationModal').modal('show');
    }
}

function getNoteTitleTextAreaValue() {
    return $('textarea#noteTitleTextArea').val();
}

function getNoteContentTextAreaValue() {
    return $('textarea#noteContentTextArea').val();
}

function setNoteContentTextAreaValue(value) {
    $('textarea#noteContentTextArea').val(value);
}

function assignNoteTitleTextAreaValueToFormInput(){
    $('#dynamicNoteTitleInputForSave').val(getNoteTitleTextAreaValue());
}

function assignNoteContentTextAreaValueToFormInput(){
    $('#dynamicNoteContentInputForUpdate').val(getNoteContentTextAreaValue());
    $('#dynamicNoteContentInputForSave').val(getNoteContentTextAreaValue());
}