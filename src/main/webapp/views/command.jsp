<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<h1> Welcome ${command.accountName}!</h1>
<h2>Enter operation: </h2>
<form:form action="/command" method="POST" modelAttribute="command">
    <form:hidden path="accountName" />
    <form:hidden path="accountId" />
    <form:input type="text" path="commandName"/>
    <input type="submit" value="Enter"/>
    <form:errors path="commandName" cssClass="error"/>
</form:form>
<c:if test="${not empty listPrintBalance}">
<c:forEach var="printBalance" items="${listPrintBalance}">
    ${printBalance.currency} = ${printBalance.balance}<br/>
</c:forEach>
</c:if>
</body>
</html>
