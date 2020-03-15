<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${ok==true}">
        <span style="color: green">Rezerwacja udana!</span><br>
        <span style="color: green; font-weight: bold">
            Rezerwacja ważna do ${date}, ${expiration}, cena biletu to ${price} zł
        </span>
    </c:if>
    <c:if test="${ok==false}">
        <span style="color: red">Rezerwacja nieudana...</span>
    </c:if>
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
    <c:if test="${not empty redir and (empty from or empty to)}">
        <span style="color: red">Należy podać przedział dat</span><br>
    </c:if>
    <c:if test="${not empty fromH and not empty toH}">
        <h3>w godzinach ${fromH} - ${toH}</h3>
    </c:if>
    <c:if test="${not empty redir and (empty fromH or empty toH)}">
        <span style="color: red">Należy podać przedział godzin</span><br>
    </c:if>
    <c:forEach items="${movies}" var="movie">
        ${movie}
        <form action="/details" method="post">
            <input hidden name="id" value="${movie.id}">
            Liczba miejsc: <input type="number" name="seatsNo">
            <button type="submit">Przejdź do rezerwacji</button>
        </form>
        <br>
    </c:forEach>
</body>
</html>
