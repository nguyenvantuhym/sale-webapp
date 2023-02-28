<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

    if (loggedIn == null || !loggedIn) response.sendRedirect("/login");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Trang chủ</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <%@include file="commons/Includes.jsp" %>
</head>
<body>
<div class="d-flex flex-column vh-100 overflow-hidden">
    <%@include file="commons/Nav.jsp" %>
    <div class="container-fluid d-flex flex-grow-1 p-0 w-100 overflow-hidden">
        <div class="row m-0 d-flex flex-row h-auto flex-nowrap w-100">
            <%@include file="commons/SideBar.jsp" %>
            <div class="w-100 h-100 d-flex flex-nowrap">
                <c:set var="currentPage" scope="session" value="${requestScope.currentPage}"/>
                <c:choose>
                    <c:when test='${currentPage == "sell-ticket"}'>
                        <%@include file="pages/Sell.jsp"%>
                    </c:when>
                    <c:when test="${currentPage == 'fdj'}">
                        Salary is very good.
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>
