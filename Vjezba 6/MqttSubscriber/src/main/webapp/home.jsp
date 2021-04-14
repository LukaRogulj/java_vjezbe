<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<div class="container">

    <form:form method="POST" modelAttribute="brokerConfig" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>
        <spring:bind path="hostName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="hostName" class="form-control" placeholder="hostName"
                            autofocus="true"></form:input>
                <form:errors path="hostName"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="port">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="port" class="form-control" placeholder="port"></form:input>
                <form:errors path="port"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="topic">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="topic" class="form-control" placeholder="topic"></form:input>
                <form:errors path="topic"></form:errors>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>

