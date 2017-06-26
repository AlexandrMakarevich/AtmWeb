<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdraw operation</title>
</head>
<body>
<spring:eval expression="errorCode == T(com.atm_exeption.ErrorCodes).NOT_ENOUGH_MONEY" var="isError"/>
<c:if test="${isError}">
    Not enough money on the account!
</c:if>
<spring:eval expression="errorCode == T(com.atm_exeption.ErrorCodes).NO_CURRENCY" var="isError"/>
<c:if test="${isError}">
    "You don't have money on currency "
</c:if>
<h3><a href="/account"><input type="submit" value="Back to previous menu"/></a></h3>
</body>
</html>
