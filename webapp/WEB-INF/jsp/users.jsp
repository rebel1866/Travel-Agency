<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Учет пользователей</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/users.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <h1>Учет пользователей: </h1>
        <form action="${pageContext.request.contextPath}/executor" method="get">
            <input type="hidden" name="command" value="users">
            <input type="text" name="user_id" placeholder="ID пользователя">
            <input type="text" name="login" placeholder="Логин"><br>
            <input type="text" name="first_name" placeholder="Имя">
            <input type="text" name="last_name" placeholder="Фамилия"><br>
            <input id="submit-user" type="submit" name="send" value="Поиск">
            <input type="hidden" name="page" value="1">
        </form>
        <c:set var="queryString" value="${pageContext.request.getQueryString()}"/>

        <div id="sorting" style="margin-bottom: 30px">
            <c:out value="Сортировать по"/>
            <c:out value="ID:"/>&nbsp;
            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=user_id&sortingOrder=asc">
                <c:out value="asc"/>
            </a>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=user_id&sortingOrder=desc">
                <c:out value="desc"/>
            </a>&nbsp;
            <c:out value="фамилии:"/>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=last_name&sortingOrder=asc">
                <c:out value="asc"/>
            </a>&nbsp;

            <a class="link-a"
               href="${pageContext.request.contextPath}/executor?<m:cutsort queryStr="${queryString}"/>&sorting=last_name&sortingOrder=desc">
                <c:out value="desc"/>
            </a>
        </div>
        <table>
            <tr class="color-blue">
                <th><c:out value="ID"/></th>
                <th><c:out value="Логин"/></th>
                <th><c:out value="Имя"/></th>
                <th><c:out value="Фамилия"/></th>
                <th><c:out value="Дата рождения"/></th>
                <th><c:out value="Статус"/></th>
                <th><c:out value="Роль"/></th>
                <th><c:out value="Полная информация"/></th>
            </tr>
            <c:forEach var="user" items="${users}" varStatus="i">
                <tr <c:if test="${i.count%2==0}">class="color-blue" </c:if>>
                    <td><c:out value="${user.userID}"/></td>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.birthDate}"/></td>
                    <td><c:out value="${user.userStatus}"/></td>
                    <td><c:out value="${user.userRole}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/executor?command=full_user_info&user_id=${user.userID}">
                            <c:out value="Подробнее"/></a></td>
                </tr>
            </c:forEach>
        </table>
        <p id="add-userr"><a href="${pageContext.request.contextPath}/executor?command=add_user&subcommand=3">
            <c:out value="Добавить пользователя"/></a> </p>
        <%@include file="pagination.jsp"%>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
