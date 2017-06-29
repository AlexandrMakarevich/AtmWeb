<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operation add balance</title>
</head>
<body>
<spring:eval expression="errorCode == T(com.atm_exeption.ErrorCodes).NO_CHANGES" var="isError"/>
<c:if test="${isError}">
    No column has been changed!Currency doesn't exist.
</c:if>
<spring:eval expression="errorCode == T(com.atm_exeption.ErrorCodes).NO_CURRENCY" var="isError"/>
<c:if test="${isError}">
    "You don't have money on currency !"
</c:if>
<spring:eval expression="commandNameEnum == T(com.command.CommandName).ADD" var="isValid"/>
<c:if test="${isValid}">
    <c:forEach var="printAdd" items="${operationResult}">
        Add on your balance ${printAdd.balance} in currency ${printAdd.currency}!<br/>
    </c:forEach>
</c:if>
<h3><a href="/account"><input type="submit" value="Back to previous menu"/></a></h3>
</body>
</html>
