<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operation balance</title>
</head>
<body>
<spring:eval expression="commandNameEnum == T(com.command.CommandName).PRINT" var="isValid"/>
<c:if test="${isValid}">
    <c:forEach var="printBalanceService" items="${operationResult}">
        On your balance is ${printBalanceService.balance} in currency ${printBalanceService.currency}!<br/>
    </c:forEach>
</c:if>
<h3><a href="/account"><input type="submit" value="Back to previous menu"/></a></h3>
</body>
</html>
