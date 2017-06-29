<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Technical support page</title>
</head>
<body>
<c:if test="${not empty errorMessage}">
    <h2>${errorMessage}</h2>
</c:if>
<h2>Hello. If you saw this message on the screen, contact technical support please!</h2>
<h3><a href="/account"><button type="button">Back to previous menu</button></a></h3>
</body>
</html>
