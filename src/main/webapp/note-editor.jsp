<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="stringEscapeUtils" class="org.apache.commons.text.StringEscapeUtils"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Notes | Note editor</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">




    <%-- HIDDEN FORMS --%>

    <form:form id="openViewModeForm" method="POST" modelAttribute="noteForm" action="${contextPath}/note-editor?mode=view">

        <spring:bind path="title">
            <form:input type="hidden" value="${noteObject.title}" path="title"/>
        </spring:bind>
        <spring:bind path="content">
            <form:input type="hidden" value="${noteObject.content}" path="content"/>
        </spring:bind>
        <spring:bind path="archived">
            <form:input type="hidden" value="${noteObject.archived}" path="archived"/>
        </spring:bind>
        <spring:bind path="published">
            <form:input type="hidden" value="${noteObject.published}" path="published"/>
        </spring:bind>
        <spring:bind path="version">
            <form:input type="hidden" value="${noteObject.version}" path="version"/>
        </spring:bind>
        <spring:bind path="creator.username">
            <form:input type="hidden" value="${noteObject.creator.username}" path="creator.username"/>
        </spring:bind>
        <spring:bind path="createdTimestamp">
            <form:input type="hidden" value="${noteObject.createdTimestamp}" path="createdTimestamp"/>
        </spring:bind>
        <spring:bind path="lastModifier.username">
            <form:input type="hidden" value="${noteObject.lastModifier.username}" path="lastModifier.username"/>
        </spring:bind>
        <spring:bind path="lastModifiedTimestamp">
            <form:input type="hidden" value="${noteObject.lastModifiedTimestamp}" path="lastModifiedTimestamp"/>
        </spring:bind>

        <c:if test="${not empty noteObject.noteModifications}">
            <c:forEach items="${noteObject.noteModifications}" var="noteModification" varStatus="loop">
                <spring:bind path="noteModifications[${loop.index}].content">
                    <form:input type="hidden" value="${noteModification.content}" path="noteModifications[${loop.index}].content"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].version">
                    <form:input type="hidden" value="${noteModification.version}" path="noteModifications[${loop.index}].version"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].creator.username">
                    <form:input type="hidden" value="${noteModification.creator.username}" path="noteModifications[${loop.index}].creator.username"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].createdTimestamp">
                    <form:input type="hidden" value="${noteModification.createdTimestamp}" path="noteModifications[${loop.index}].createdTimestamp"/>
                </spring:bind>
            </c:forEach>
        </c:if>

    </form:form>

    <form:form id="openEditModeForm" method="POST" modelAttribute="noteForm" action="${contextPath}/note-editor?mode=edit">
        <spring:bind path="title">
            <form:input type="hidden" value="${noteObject.title}" path="title"/>
        </spring:bind>
        <spring:bind path="content">
            <form:input type="hidden" value="${noteObject.content}" path="content"/>
        </spring:bind>
        <spring:bind path="archived">
            <form:input type="hidden" value="${noteObject.archived}" path="archived"/>
        </spring:bind>
        <spring:bind path="published">
            <form:input type="hidden" value="${noteObject.published}" path="published"/>
        </spring:bind>
        <spring:bind path="version">
            <form:input type="hidden" value="${noteObject.version}" path="version"/>
        </spring:bind>
        <spring:bind path="creator.username">
            <form:input type="hidden" value="${noteObject.creator.username}" path="creator.username"/>
        </spring:bind>
        <spring:bind path="createdTimestamp">
            <form:input type="hidden" value="${noteObject.createdTimestamp}" path="createdTimestamp"/>
        </spring:bind>
        <spring:bind path="lastModifier.username">
            <form:input type="hidden" value="${noteObject.lastModifier.username}" path="lastModifier.username"/>
        </spring:bind>
        <spring:bind path="lastModifiedTimestamp">
            <form:input type="hidden" value="${noteObject.lastModifiedTimestamp}" path="lastModifiedTimestamp"/>
        </spring:bind>

        <c:if test="${not empty noteObject.noteModifications}">
            <c:forEach items="${noteObject.noteModifications}" var="noteModification" varStatus="loop">
                <spring:bind path="noteModifications[${loop.index}].content">
                    <form:input type="hidden" value="${noteModification.content}" path="noteModifications[${loop.index}].content"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].version">
                    <form:input type="hidden" value="${noteModification.version}" path="noteModifications[${loop.index}].version"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].creator.username">
                    <form:input type="hidden" value="${noteModification.creator.username}" path="noteModifications[${loop.index}].creator.username"/>
                </spring:bind>
                <spring:bind path="noteModifications[${loop.index}].createdTimestamp">
                    <form:input type="hidden" value="${noteModification.createdTimestamp}" path="noteModifications[${loop.index}].createdTimestamp"/>
                </spring:bind>
            </c:forEach>
        </c:if>

    </form:form>

    <form:form id="archiveNoteForm" method="PATCH" modelAttribute="noteForm" action="/notes/api/archive">

        <spring:bind path="title">
            <form:input type="hidden" value="${noteObject.title}" path="title"/>
        </spring:bind>

    </form:form>

    <form:form id="publishNoteForm" method="PATCH" modelAttribute="noteForm" action="/notes/api/publish">

        <spring:bind path="title">
            <form:input type="hidden" value="${noteObject.title}" path="title"/>
        </spring:bind>

    </form:form>

    <form:form id="updateNoteForm" method="PUT" modelAttribute="noteForm" action="/notes/api/update">

        <spring:bind path="title">
            <form:input type="hidden" value="${noteObject.title}" path="title"/>
        </spring:bind>
        <spring:bind path="content">
            <form:input id="dynamicNoteContentInputForUpdate" type="hidden" value="" path="content"/>
        </spring:bind>
        <spring:bind path="creator.username">
            <form:input type="hidden" value="${noteObject.creator.username}" path="creator.username"/>
        </spring:bind>

    </form:form>

    <form:form id="saveNoteForm" method="POST" modelAttribute="noteForm" action="/notes/api/save">

        <spring:bind path="title">
            <form:input id="dynamicNoteTitleInputForSave" type="hidden" value="" path="title"/>
        </spring:bind>
        <spring:bind path="content">
            <form:input id="dynamicNoteContentInputForSave" type="hidden" value="" path="content"/>
        </spring:bind>

    </form:form>

    <%-- /HIDDEN FORMS --%>




    <%-- MODALS --%>




    <div class="modal fade" id="unexpectedErrorModal" tabindex="-1" role="dialog" aria-labelledby="unexpectedErrorModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg" style="overflow-y: initial !important" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="unexpectedErrorModalLabel">Unexpected error</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div id="unexpectedErrorModalBody" class="modal-body text-center"
                    style="max-height: calc(100vh - 200px); overflow-y: auto; white-space: pre-line;">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="noteHistoryModal" tabindex="-1" role="dialog" aria-labelledby="noteHistoryModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg" style="overflow-y: initial !important" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="noteHistoryModalLabel">Note history</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center" style="max-height: calc(100vh - 200px); overflow-y: auto;">
                    <c:if test="${not empty noteObject.noteModifications}">
                        <c:forEach items="${noteObject.noteModifications}" var="noteModification" varStatus="loop">

                            Version: <b>${noteModification.version}</b> |
                            Created by: <font color="#0066ff"><b>${noteModification.creator.username}</b></font> |
                            Created on: <b>${noteModification.createdTimestamp.toString().replace('T', ' ')}</b>

                            <textarea class="form-control"
                             rows="2" readonly="true">${noteModification.content}</textarea>

                            <c:if test="${!loop.last}"><hr></c:if>
                        </c:forEach>
                    </c:if>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="archiveConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="archiveConfirmationModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="archiveConfirmationModalLabel">Confirm archiving</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-left">Are you sure that you wish to archive this note?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-warning" type="button" onclick="submitForm('archiveNoteForm')">Archive</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="publishConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="publishConfirmationModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="publishConfirmationModalLabel">Confirm publishing</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-left">Are you sure that you wish to publish this note?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-danger" type="button" onclick="submitForm('publishNoteForm')">Publish</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updateConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="updateConfirmationModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="updateConfirmationModalLabel">Confirm update</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                            <span aria-hidden="true">&times;</span>
                        </button>
                </div>
                <div class="modal-body text-left">Are you sure that you wish to update this note?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-success" onclick="assignNoteContentTextAreaValueToFormInput(); submitForm('updateNoteForm');" type="button">Update</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="saveConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="saveConfirmationModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="saveConfirmationModalLabel">Confirm save</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-left">Are you sure that you wish to save this note?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button class="btn btn-success" onclick="assignNoteContentTextAreaValueToFormInput(); assignNoteTitleTextAreaValueToFormInput(); submitForm('saveNoteForm');" type="button">Save</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="discardConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="discardConfirmationModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="discardConfirmationModalLabel">Confirm discard</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-left">Are you sure that you wish to discard your changes?</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button id="confirmDiscardChangesButton" class="btn btn-info" type="button">Discard</button>
                </div>
            </div>
        </div>
    </div>

    <%-- /MODALS --%>

</head>
<header>

    <div class="container">
        <button class="btn btn-primary btn-sm float-left" onclick="changesDiscardingButtonClicked(redirectToNotes, '')">My notes</button>

        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <h5 class="float-right">User: <font color="#009900">${pageContext.request.userPrincipal.name}</font> |
                <button class="btn btn-sm btn-dark" type="button" onclick="changesDiscardingButtonClicked(submitForm, 'logoutForm')">Logout</button>
            </h5>
        </form>
    </div>

</header>
<body>
  <div class="container">

    <div class="note-editor-label-and-content">
        <span id="noteTitleSpan"><font size="+2"><b>Note:</b> ${noteObject.title}</font></span>

        <div id="noteTitleTextAreaContainer" class="form-group w-50">
            <textarea id="noteTitleTextArea" class="form-control"
                rows="1" cols="50" maxlength="50"
                style="overflow:auto;resize:none;display:inline;"
                placeholder="The title of your note..."></textarea>
            <font color="red"><div id="noteTitleError"></div></font>
        </div>

        <div id="noteContentTextAreaContainer" class="form-group">
            <textarea id="noteContentTextArea" class="form-control" rows="15"
             placeholder="The contents of your note..."></textarea>
             <span id="noteContentErrorSpan" style="color: red;"></span>
        </div>
    </div>

    <div class="note-editor-buttons">
        <button id="saveButton" type="button" class="btn btn-success"></button>
        <button id="editButton" type="button" class="btn btn-info"></button>
        <button id="archiveButton" type="button" class="btn btn-warning"></button>
        <button id="publishButton" type="button" class="btn btn-danger"></button>
    </div>

    <div id="noteDetailsTextContainer" class="float-right note-editor-note-details-text">
        <span id="noteDetailsTextSpan">
            <span id="noteDetailsTextCreatedBySpan"></span>
            <span id="noteDetailsTextCreatorUsernameSpan"></span>
            <span id="noteDetailsTextCreatedOnSpan"></span>
            <span id="noteDetailsTextCreatedTimestampSpan"></span>
            <span id="noteDetailsTextLastModifiedBySpan"></span>
            <span id="noteDetailsTextLastModifierUsernameSpan"></span>
            <span id="noteDetailsTextLastModifiedOnSpan"></span>
            <span id="noteDetailsTextLastModifiedTimestampSpan"></span>
            <span id="noteDetailsTextVerSpan"></span>
            <span id="noteDetailsTextVersionSpan"></span>
        </span>
    </div>

    <br>

    <div class="note-editor-history-button">
        <button id="viewHistoryButton"type="button" class="btn btn-secondary"></button>
    </div>

  </div>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  <script src="${contextPath}/resources/js/common.js"></script>
  <script src="${contextPath}/resources/js/redirect.js"></script>
  <script src="${contextPath}/resources/js/note-editor.js"></script>
  <script type="text/javascript">
    var editorMode = '${editorMode}';
    var currentUser = '${stringEscapeUtils.escapeEcmaScript(pageContext.request.userPrincipal.name)}';
    var originalNoteTitle = '${stringEscapeUtils.escapeEcmaScript(noteObject.title)}';
    var originalNoteContent = '${stringEscapeUtils.escapeEcmaScript(noteObject.content)}';
    var noteArchived = '${noteObject.archived}';
    var notePublished = '${noteObject.published}';
    var noteVersion = '${noteObject.version}';
    var noteCreatorUsername = '${stringEscapeUtils.escapeEcmaScript(noteObject.creator.username)}';
    var noteCreatedTimestamp = '${noteObject.createdTimestamp.toString().replace("T", " ")}';
    var noteLastModifierUsername = '${stringEscapeUtils.escapeEcmaScript(noteObject.lastModifier.username)}';
    var noteLastModifiedTimestamp = '${noteObject.lastModifiedTimestamp.toString().replace("T", " ")}';
  </script>
</body>
</html>