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
            Data od
            <input name="from" type="date">
        </div>
        <div class="col-md-3">
            Data do
            <input name="to" type="date">
        </div>
        <div class="col-md-3">
            Godzina od
            <input name="fromH" type="time">
        </div>
        <div class="col-md-3">
            Godzina do
            <input name="toH" type="time">
        </div>
        <div class="col-md-3">
            <input type="submit" value="Szukaj">
        </div>
    </form>
    <c:if test="${not empty from and not empty to}">
        <h3>Wyniki dla okresu ${from} - ${to}</h3>
    </c:if>
    <c:if test="${not empty fromH and not empty toH}">
        <h3>w godzinach ${fromH} - ${toH}</h3>
    </c:if>
    <c:forEach items="${movies}" var="movie">
        ${movie}
        <br>
    </c:forEach>
</body>
</html>
