<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/" method="post">
        <div class="col-md-3">
            Od
            <input name="from" type="date">
        </div>
        <div class="col-md-3">
            Do
            <input name="to" type="date">
        </div>
        <div class="col-md-3">
            <input type="submit" value="Szukaj">
        </div>
    </form>
    <c:if test="${not empty from and not empty to}">
        <h3>Wyniki dla okresu ${from} - ${to}</h3>
    </c:if>
    <c:forEach items="${movies}" var="movie">
        ${movie}
    </c:forEach>
</body>
</html>
