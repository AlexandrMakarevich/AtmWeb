<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operation balance</title>
</head>
<body>
<spring:eval expression="commandNameEnum == T(com.command.CommandName).PRINT" var="isValid"/>
<c:if test="${isValid}">
    <c:forEach var="printBalance" items="${listPrintBalance}">
        On your balance is ${printBalance.currency} in currency ${printBalance.balance}!<br/>
    </c:forEach>
</c:if>
</body>
</html>
