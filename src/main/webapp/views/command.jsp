<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Enter command</title>
</head>
<body>
<h1> Welcome ${account.accountName}!</h1>

<h2>Enter operation: </h2>
<form:form action="/command" method="POST" modelAttribute="command">
    <form:input type="text" path="commandName"/>
    <input type="submit" value="Enter"/>
</form:form>
</body>
</html>
