<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${lengthError==true}">
        <span style="color: red">Imię oraz nazwisko muszą zawierać co najmniej 3 znaki
            i zaczynać się wielką literą</span>
    </c:if>
    <c:if test="${surnameError==true}">
        <span style="color: red">Niepoprawne nazwisko</span>
    </c:if>
    <form action="/doBook" method="post">
        ${seats}
        <input hidden value="${seats}" name="seats">
        <input hidden value="${movie.id}" name="movieId">
        Imię: <input type="text" name="name">
        Nazwisko: <input type="text" name="surname">
        <select name="age">
            <option selected="selected" value="adult">Dorosły</option>
            <option value="student">Student</option>
            <option value="child">Dziecko</option>
        </select>
        <button type="submit">Rezerwuj</button>
    </form>
    <form>
        <input type="button" value="Powrót" onclick="history.back()">
    </form>
</body>
</html>
