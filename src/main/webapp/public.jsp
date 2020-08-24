<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Notes | Public notes</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<header>
    <div class="container">
        <button class="btn btn-primary btn-sm float-left" onclick="redirectToNotes()">My notes</button>

        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <h5 class="float-right">User: <font color="#009900">${pageContext.request.userPrincipal.name}</font> |
                <button class="btn btn-sm btn-dark" type="submit">Logout</button>
            </h5>
        </form>

        <div class="page-title" style="color:#ff3300;">
            <h1 class="display-4"><i>Public notes</i></h1>
        </div>
    </div>
</header>
<body>
  <div class="container">

    <c:choose>
        <c:when test="${not empty publishedNotesList}">
            <div class="notes-list">
                <c:forEach items="${publishedNotesList}" var="note" varStatus="notesLoop">
                    <div class="notes-list-note">
                        <h6 style="display: inline-block;"><font size="+1"><font color="#0066ff"><b>${note.creator.username}</b></font>&nbsp|&nbsp${note.title}</font></h6>

                        <div class="notes-list-note-buttons">
                            <form:form method="POST" modelAttribute="noteForm" action="${contextPath}/note-editor?mode=view" style='display:inline-block;'>

                                <spring:bind path="title">
                                    <form:input type="hidden" value="${note.title}" path="title"/>
                                </spring:bind>
                                <spring:bind path="content">
                                    <form:input type="hidden" value="${note.content}" path="content"/>
                                </spring:bind>
                                <spring:bind path="archived">
                                    <form:input type="hidden" value="${note.archived}" path="archived"/>
                                </spring:bind>
                                <spring:bind path="published">
                                    <form:input type="hidden" value="${note.published}" path="published"/>
                                </spring:bind>
                                <spring:bind path="version">
                                    <form:input type="hidden" value="${note.version}" path="version"/>
                                </spring:bind>
                                <spring:bind path="creator.username">
                                    <form:input type="hidden" value="${note.creator.username}" path="creator.username"/>
                                </spring:bind>
                                <spring:bind path="createdTimestamp">
                                    <form:input type="hidden" value="${note.createdTimestamp}" path="createdTimestamp"/>
                                </spring:bind>
                                <spring:bind path="lastModifier.username">
                                    <form:input type="hidden" value="${note.lastModifier.username}" path="lastModifier.username"/>
                                </spring:bind>
                                <spring:bind path="lastModifiedTimestamp">
                                    <form:input type="hidden" value="${note.lastModifiedTimestamp}" path="lastModifiedTimestamp"/>
                                </spring:bind>

                                <c:if test="${not empty note.noteModifications}">
                                    <c:forEach items="${note.noteModifications}" var="noteModification" varStatus="noteModificationsLoop">
                                        <spring:bind path="noteModifications[${noteModificationsLoop.index}].content">
                                            <form:input type="hidden" value="${noteModification.content}" path="noteModifications[${noteModificationsLoop.index}].content"/>
                                        </spring:bind>
                                        <spring:bind path="noteModifications[${noteModificationsLoop.index}].version">
                                            <form:input type="hidden" value="${noteModification.version}" path="noteModifications[${noteModificationsLoop.index}].version"/>
                                        </spring:bind>
                                        <spring:bind path="noteModifications[${noteModificationsLoop.index}].creator.username">
                                            <form:input type="hidden" value="${noteModification.creator.username}" path="noteModifications[${noteModificationsLoop.index}].creator.username"/>
                                        </spring:bind>
                                        <spring:bind path="noteModifications[${noteModificationsLoop.index}].createdTimestamp">
                                            <form:input type="hidden" value="${noteModification.createdTimestamp}" path="noteModifications[${noteModificationsLoop.index}].createdTimestamp"/>
                                        </spring:bind>
                                    </c:forEach>
                                </c:if>

                                <button class="btn btn-info btn-sm" type="submit">View note</button>
                            </form:form>

                        </div>
                    </div>
                    <c:if test="${!notesLoop.last}"><hr style="width: 75%;"></c:if>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-danger empty-notes-list-alert" role="alert">
                <h6>No notes to display!</h6>
            </div>
        </c:otherwise>
    </c:choose>
  </div>
  <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  <script src="${contextPath}/resources/js/redirect.js"></script>
</body>
</html>