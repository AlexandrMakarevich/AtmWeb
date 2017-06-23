<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operation add balance</title>
</head>
<body>
<spring:eval expression="commandNameEnum == T(com.command.CommandName).ADD" var="isValid"/>
<c:if test="${isValid}">
    <c:forEach var="printBalance" items="${listPrintBalance}">
        Add on your balance ${printBalance.balance} in currency ${printBalance.currency}!<br/>
    </c:forEach>
</c:if>
</body>
</html>
