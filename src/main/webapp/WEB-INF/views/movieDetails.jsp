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
        <span style="color: red">Liczba wybranych miejsc nie zgadza się z podaną liczbą</span>
    </c:if>
    <c:if test="${listsError==true}">
        <span style="color: red">Należy wybrać miejsca tylko z jednej listy</span>
    </c:if>
    <form action="/book" method="post">
        <input hidden name="seatsNo" value="${seatsNo}">
        <input hidden name="movieId" value="${movie.id}">
        <input hidden name="availableSeats1" value="${availableSeats1}">
        <input hidden name="availableSeats2" value="${availableSeats2}">
        <c:forEach items="${availableSeats1}" var="seat">
            ${seat}
            <input type="checkbox" name="${seat.no}" value="checked">
            <br>
        </c:forEach>
        <c:if test="${not empty availableSeats1 and not empty availableSeats2}">
            lub
        </c:if>
        <br>
        <c:forEach items="${availableSeats2}" var="seat">
            ${seat}
            <input type="checkbox" name="${seat.no}" value="checked">
            <br>
        </c:forEach>
        <input hidden name="availableSeats" value="${availableSeats}">
        <button type="submit">Rezerwuj</button>
    </form>
    <form>
        <input type="button" value="Powrót" onclick="history.back()">
    </form>
</body>
</html>
