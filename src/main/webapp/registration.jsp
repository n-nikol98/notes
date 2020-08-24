<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>Notes | Create an account</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
  </head>
  <body>
    <div class="container">

        <c:choose>
            <c:when test="${pageContext.request.userPrincipal.name != null}">
                <c:redirect url="/"/>
            </c:when>
            <c:otherwise>
                <div class="form-signin">
                    <form:form id="registrationForm" method="POST" modelAttribute="userForm">
                        <h2 class="form-signin-heading">Create your account</h2>
                        <spring:bind path="username">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" path="username" class="form-control" placeholder="Username"
                                    autofocus="true"></form:input>
                                <form:errors path="username"></form:errors>
                            </div>
                        </spring:bind>

                        <spring:bind path="password">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input id="passwordInputBox" type="password" path="password"
                                    class="form-control" placeholder="Password"></form:input>
                                <form:errors path="password"></form:errors>
                            </div>
                        </spring:bind>
                    </form:form>

                    <div class="form-group">
                        <input id="passwordConfirmationInputBox" type="password"
                            path="passwordConfirmation" class="form-control"
                            placeholder="Confirm your password"></input>
                        <div id="passwordConfirmationError" style="display: none;">
                            <font color="red">These passwords don't match.</font>
                        </div>
                    </div>

                    <button class="btn btn-lg btn-primary btn-block" form="registrationForm"
                        onclick="assurePasswordAndPasswordConfirmationEqualityAndOptionallySubmitRegistrationForm()"
                        type="button">Submit</button>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/common.js"></script>
    <script src="${contextPath}/resources/js/registration.js"></script>
  </body>
</html>
