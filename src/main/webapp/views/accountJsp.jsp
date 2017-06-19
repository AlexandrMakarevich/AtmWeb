<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1>Hello!</h1>
<h2>Enter you account name : </h2>
<form:form method="POST" modelAttribute="account">
    <form:input type="text" path="accountName"/>
    <input type="submit" value="Enter"/>
</form:form>
</body>
</html>