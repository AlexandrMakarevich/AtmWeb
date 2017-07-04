<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter command</title>
    <style>
        .error {
            color: red;
            font-weight: 100;
        }
    </style>
</head>
<body>
<h1> Welcome ${commandBean.accountName}!</h1>
<h2>Enter operation: </h2>
<form:form method="POST" modelAttribute="command">
    <form:hidden path="accountName"/>
    <form:hidden path="accountId"/>
    <form:input type="text" path="commandName"/>
    <input type="submit" value="Enter"/>
    <form:errors path="commandName" cssClass="error"/>
</form:form>
</body>
</html>
