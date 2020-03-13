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
    <c:forEach items="${availableSeats}" var="seat">
        ${seat}
        <a href="/book?movieId=${movie.id}&seatId=${seat.id}"><button>Rezerwuj</button></a>
        <br>
    </c:forEach>
    <form>
        <input type="button" value="Powrót" onclick="history.back()">
    </form>
</body>
</html>
