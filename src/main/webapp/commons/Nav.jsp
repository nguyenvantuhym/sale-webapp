<%--
  Created by IntelliJ IDEA.
  User: Emddi
  Date: 7/1/2022
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-md navbar-light bg-white shadow-sm">
    <img src="../image/company/logo-only-vnp.png" style="width: 30px" alt="Logo"/>
    <a class="navbar-brand text-info ml-2" href="${pageContext.request.contextPath}/">Bán vé DEMO</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
        </ul>
<%--        <div><c:out value="${sessionScope.currentEmployee.getName()}"/>&nbsp;</div>--%>

        <div class="dropdown mr-5">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fa fa-user" aria-hidden="true"></i>  <c:out value="${sessionScope.currentEmployee.getName()}"/>
            </button>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
<%--                <a class="dropdown-item" href="#">Action</a>--%>
                <a class="dropdown-item" href="${pageContext.request.contextPath}/change-password">
                    <button class="btn btn-link text-black-50 my-2 my-sm-0" > <i class="fa fa-sign-out" aria-hidden="true"></i>Đổi mật khẩu</button>
                </a>
                <a class="dropdown-item" href="#">
                    <form action="${pageContext.request.contextPath}/logout" class="form-inline my-2 my-lg-0">
                        <button class="btn btn-link text-black-50" > <i class="fa fa-sign-out" aria-hidden="true"></i>Đăng xuất</button>
                    </form>
                </a>
            </div>
        </div>


    </div>
</nav>
