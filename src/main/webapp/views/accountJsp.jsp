<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        .error {
            color: red; font-weight: 100;
        }
    </style>
</head>
<body>
<h1>Hello!</h1>
<h2>Enter you account name : </h2>
<form:form method="POST" modelAttribute="account">
    <form:input type="text" path="accountName"/>
    <input type="submit" value="Enter"/>
    <form:errors path="accountName" cssClass="error"/>
</form:form>
</body>
</html>