<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/doBook" method="post">
        <input hidden value="${seat.id}" name="seatId">
        <input hidden value="${movie.id}" name="movieId">
        Imię: <input type="text" name="name">
        Nazwisko: <input type="text" name="surname">
        <button type="submit">Rezerwuj</button>
    </form>
    <form>
        <input type="button" value="Powrót" onclick="history.back()">
    </form>
</body>
</html>
