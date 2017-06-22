<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Operation done</title>
</head>
<body>
<c:forEach var="printBalance" items="${listPrintBalance}">
    <li>${printBalance.currency} = ${printBalance.balance}</li>
</c:forEach>
</body>
</html>
