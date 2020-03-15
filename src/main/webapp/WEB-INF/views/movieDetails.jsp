<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>${movie}</h2>
    sala: ${movie.room}, wolne miejsca:<br>
    <c:if test="${noChecked==true}">
        <span style="color: red">Należy wybrać co najmniej jedno miejsce</span>
    </c:if>
    <form action="/book" method="post">
        <input hidden name="movieId" value="${movie.id}">
        <c:forEach items="${availableSeats}" var="seat">
            ${seat}
            <input type="checkbox" name="${seat.no}" value="checked">
            <br>
        </c:forEach>
        <button type="submit">Rezerwuj</button>
    </form>
    <form>
        <input type="button" value="Powrót" onclick="history.back()">
    </form>
</body>
</html>
