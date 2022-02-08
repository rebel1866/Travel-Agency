<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Добавить новую роль</title>
</head>
<body>
<div id="container">
    <jsp:include page="header.jsp"/>
    <div id="content">
        <div id="add-role">
         <form action="${pageContext.request.contextPath}/executor" method="post">
             <input type="hidden" name="command" value="add_new_role">
             <input type="hidden" name="subcommand" value="main">
             <input type="hidden" name="input1" value="HOME">
             <input type="hidden" name="input2" value="TOURS">
             <input type="hidden" name="input3" value="SIGN_IN">
             <input type="hidden" name="input4" value="SIGN_OUT">
             <input type="hidden" name="input5" value="REGISTRATION">
             <input type="hidden" name="input6" value="FULL_TOUR_INFO">
             <input type="hidden" name="input7" value="HOTELS">
             <input type="hidden" name="input8" value="FULL_HOTEL_INFO">
             <input type="hidden" name="input9" value="ADD_FEEDBACK">
             <input type="hidden" name="input10" value="FEEDBACKS">
             <input type="hidden" name="input11" value="PERSONAL_DATA">
             <input type="hidden" name="input12" value="RESORTS">
             <input type="hidden" name="input13" value="LOCATION_AJAX">
             <input type="hidden" name="input14" value="CHANGE_PERSONAL_DATA">
             <input type="hidden" name="input15" value="ADD_ORDER">
             <input type="hidden" name="input16" value="RESET_PASSWORD">
             <label><c:out value="Имя роли:"/>
                 <input type="text" name="role_name">
             </label><br>
             <label><c:out value="Просмотр заявок:"/>
                 <input  type="checkbox" name="input17" value="ORDERS">
             </label><br>
             <label><c:out value="Изменение информации о пользователе:"/>
                 <input  type="checkbox" name="input18" value="UPDATE_USER">
             </label><br>
             <label><c:out value="Удаление отзывов:"/>
                 <input  type="checkbox" name="input19" value="REMOVE_FEEDBACK">
             </label><br>
             <label><c:out value="Удаление отелей:"/>
                 <input  type="checkbox" name="input20" value="REMOVE_HOTEL">
             </label><br>
             <label><c:out value="Добавление отелей:"/>
                 <input  type="checkbox" name="input21" value="ADD_HOTEL">
             </label><br>
             <label><c:out value="Изменение отелей:"/>
                 <input  type="checkbox" name="input22" value="CHANGE_HOTEL">
             </label><br>
             <label><c:out value="Удаление отзывов:"/>
                 <input  type="checkbox" name="input23" value="ADD_TOUR">
             </label><br>
             <label><c:out value="Добавление туров:"/>
                 <input  type="checkbox" name="input24" value="REMOVE_TOUR">
             </label><br>
             <label><c:out value="Изменение туров:"/>
                 <input  type="checkbox" name="input25" value="CHANGE_TOUR">
             </label><br>
             <label><c:out value="Удаление заявок:"/>
                 <input  type="checkbox" name="input26" value="REMOVE_ORDER">
             </label><br>
             <label><c:out value="Просмотр полной информации о заказе:"/>
                 <input  type="checkbox" name="input27" value="FULL_ORDER_INFO">
             </label><br>
             <label><c:out value="Просмотр пользователей:"/>
                 <input  type="checkbox" name="input28" value="USERS">
             </label><br>
             <label><c:out value="Просмотр полной информации о пользователе:"/>
                 <input  type="checkbox" name="input29" value="FULL_USER_INFO">
             </label><br>
             <label><c:out value="Добавление пользователя:"/>
                 <input  type="checkbox" name="input30" value="ADD_USER">
             </label><br>
             <label><c:out value="Удаление пользователя:"/>
                 <input  type="checkbox" name="input31" value="REMOVE_USER">
             </label><br>

             <input  type="submit" name="submit" value="ok">
         </form>
        </div>
    </div>
    <jsp:include page="navbar.jsp"/>
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
