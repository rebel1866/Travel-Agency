<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${tour.tourName}"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common-styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rating.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/full-tour-info.css">
</head>
<body>
<div id="container">
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <div id="content">
        <div id="full-tour-conten">

            <c:set var="isAdmin"
                   value="${sessionScope.authorization.button_access!=null}"/>

            <h1 id="header-full-tour"><c:out value="${tour.tourName}"/></h1>
            <c:out value="Отель "/>
            <a href="${pageContext.request.contextPath}/executor?command=full_hotel_info&hotel_id=${tour.hotel.hotelId}">
                <c:out value="${tour.hotel.hotelName}"/></a>

            <div class="rating">
                <c:forEach var="i" begin="1" end="${tour.hotel.rating}">
                    <span class="active"></span>
                </c:forEach>
            </div>
            <br>

            <p><c:out value="Отправление: "/>
                <fmt:parseDate value="${tour.departure}" pattern="yyyy-MM-dd" var="parsedDep" type="date"/>
                <fmt:formatDate value="${parsedDep}" var="newTourDeparture" type="date" pattern="dd MMMM yyyy"/>
                <c:out value="${newTourDeparture}"/></p>

            <p><c:out value="Возвращение: "/>
                <fmt:parseDate value="${tour.comeback}" pattern="yyyy-MM-dd" var="parsedBack" type="date"/>
                <fmt:formatDate value="${parsedBack}" var="newTourComeback" type="date" pattern="dd MMMM yyyy"/>
                <c:out value="${newTourComeback}"/></p>

            <p><c:out value="Транспорт: ${tour.transport}"/></p>
            <p><c:out value="Питание: ${tour.feeding}"/></p>

            <p><c:out value="Количество человек: ${tour.amountAdults}"/>

                <c:if test="${tour.amountAdults>1}">
                    <c:out value="взрослых"/>
                </c:if>
                <c:if test="${tour.amountAdults==1}"><c:out value="взрослый"/></c:if>
                <c:if test="${tour.amountChildren>0}"> и <c:out value="${tour.amountChildren}"/>
                    <c:if test="${tour.amountChildren>1}"><c:out value="детей"/></c:if>
                    <c:if test="${tour.amountChildren==1}"><c:out value="ребенок"/></c:if>
                </c:if>
            </p>

            <p><c:out value="Номер: ${tour.hotelRoomType}"/></p>

            <c:if test="${isAdmin}">
                <p><c:out value="Количество оставшихся мест: ${tour.amountSeats}"/></p>
            </c:if>

            <p><c:out value="Цена: ${tour.price}$"/></p>
            <h2>Об отеле: </h2>

            <p><c:out value="Тип расположения: ${tour.hotel.typeLocation}"/></p>
            <p><c:out value="Общее количество номеров: ${tour.hotel.amountRooms}"/></p>
            <p><c:out value="Описание: "/></p>
            <c:out value="${tour.hotel.hotelDescription}"/>

            <p><c:out value="Инфраструктура: "/></p>
            <c:set var="infraStr" value="${tour.hotel.infrastructure}"/>
            <c:set var="infraArray" value="${infraStr.split('; ')}"/>
            <ul class="hr">
                <c:forEach items="${infraArray}" var="element">
                    <li>
                        <c:out value="${element}"/>
                    </li>
                </c:forEach>
            </ul>

            <c:forEach items="${tour.hotel.hotelImages}" var="image">
                <img class="image-hotel" src="${pageContext.request.contextPath}/img/${image.imagePath}">
            </c:forEach>

            <h2>О курорте:</h2>
            <h3>
                <a href="fake${tour.hotel.resort.resortId}"
                   id="resort-name-tour"><c:out value="${tour.hotel.resort.resortName}"/>
                </a>
            </h3>

            <p><c:out value="Страна: ${tour.hotel.resort.location}"/></p>
            <p><c:out value="Население курорта: ${tour.hotel.resort.population} чел."/></p>
            <p><c:out value="Описание: "/></p>

            <c:out value="${tour.hotel.resort.resortDescription}"/>
            <h2><c:out value="Отзывы:"/></h2>

            <c:forEach var="feedback" varStatus="c" items="${tour.feedbacks}">

                <span id="fbk-name-date">
                <c:out value="${feedback.user.firstName} ${feedback.user.lastName}"/>&nbsp;&nbsp;
                <fmt:parseDate value="${feedback.fbkDateTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime"
                               type="both"/>
                <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${ parsedDateTime }"/>&nbsp;&nbsp;
                </span>

                <c:out value="Оценка за тур "/>
                <div class="rating">
                    <c:forEach var="i" begin="1" end="${feedback.fbkRating}">
                        <span class="active"></span>
                    </c:forEach>
                </div>

                <c:set var="len" value="${fn:length(tour.feedbacks)}"/>

                <div id="fbk-body"><c:out value="${feedback.feedbackBody}"/><br><br>
                    <c:if test="${!sessionScope.authorization.user_role.equals('Guest')&&c.count==len}">
                        <form id="fbk-form" action="${pageContext.request.contextPath}/executor" method="post">
                            <input type="hidden" name="user_id" value="${sessionScope.authorization.user_id}">
                            <input type="hidden" name="command" value="add_feedback">
                            <input type="hidden" name="subcommand" value="main">
                            <input type="hidden" name="tour_id" value="${tour.tourId}">
                            <textarea required cols="80" rows="10" placeholder="Оставьте отзыв о туре"
                                      name="fbk_body"></textarea><br><br>
                            <div class="rating-area">
                                <c:forEach var="i" begin="0" end="4">
                                    <input required type="radio" id="star-${5-i}" name="rating" value="${5-i}">
                                    <label for="star-${5-i}" title="Оценка «${5-i}»"></label>
                                </c:forEach>
                            </div>
                            <input id="sub-button-fbk" type="submit" name="submit" value="Оставить отзыв">
                        </form>
                    </c:if>
                </div>
            </c:forEach>

            <c:if test="${sessionScope.authorization.user_role.equals('Guest')}">
                <p id="link-fbk"><c:out value="Чтобы оставить отзыв "/>
                    <a class="link-sign-in" href="${pageContext.request.contextPath}/executor?command=sign_in&subcommand=1"><c:out
                            value="войдите"/>
                    </a> <c:out value="или"/>
                    <a class="link-auth" href="${pageContext.request.contextPath}/executor?command=registration&subcommand=1"><c:out
                            value="зарегистрируйтесь"/></a>
                </p>
            </c:if>


            <p class="add-tour">
                <a class="add-tour"
                   href="${pageContext.request.contextPath}/executor?command=add_order&tour_id=${tour.tourId}&subcommand=3">
                    <c:out value="Оставить заявку"/>
                </a>
            </p>


            <c:set var="path" value="${pageContext.request.contextPath}"/>
            <c:if test="${isAdmin}">

                <a id="remove-tour"
                   href="${path}/executor?command=remove_tour&tour_id=${tour.tourId}&tour_name=${tour.tourName}&subcommand=4">
                    <c:out value="Удалить тур"/>
                </a>

                <a id="change-tour"
                   href="${path}/executor?command=change_tour&tour_id=${tour.tourId}&subcommand=3">
                    <c:out value="Изменить тур"/>
                </a>

            </c:if>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
