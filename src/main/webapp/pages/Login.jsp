<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Emddi
  Date: 6/30/2022
  Time: 9:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="utf-8" %>

<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
    <title>Đăng nhập</title>
    <%@include file="/commons/Includes.jsp" %>
    <style>
        input {
            background-color: transparent !important;
        }

        .form-control:focus {
            border-color: transparent;
            box-shadow: 0px 1px 1px rgba(0, 0, 0, 0) inset, 0px 0px 8px rgba(0, 0, 0, 0);
        }
    </style>
</head>
<body>

<div class="container-fluid vh-100 vw-100 justify-content-center flex-column d-flex login-background bg-light">
    <div class="row justify-content-center">
        <div class="col-sm-6 col-lg-4 col-xl-3 bg-white p-4 m-4 rounded shadow-lg">
            <div class="d-flex">
                <div class="">
                    <img src="../image/company/logo-only-vnp.png" style="width: 150px" alt="Logo">
                </div>
                <div class="d-flex flex-column justify-content-center align-items-start ml-4">
                    <h2 class="mb-2 text-center text-info font-weight-bold">VNPass</h2>
                    <h1 class="mb-2 text-center text-info font-weight-bold">Demo Bán vé</h1>
                </div>
            </div>
            <br/>
            <form action="login" method="post">
                <div class="input-group mb-3 border border-white" style="border-width: 2px!important;">
                    <div class="input-group-prepend">
                        <span class="input-group-text border" id="addon-username"><i class="fa fa-user-circle text-info"
                                                                                     aria-hidden="true"></i></span>
                    </div>
                    <input type="text" id="username" name="username" class="form-control border font-weight-bold"
                           placeholder="Tài khoản" aria-label="Tài khoản" aria-describedby="addon-username">
                </div>
                <div class="input-group mb-3  border border-white mt-4" style="border-width: 2px!important;">
                    <div class="input-group-prepend ">
                        <span class="input-group-text border" id="addon-password"><i class="fa fa-key text-info"
                                                                                     aria-hidden="true"></i></span>
                    </div>
                    <input type="password" id="password" name="password" class="form-control border"
                           placeholder="Mật khẩu" aria-label="Mật khẩu" aria-describedby="addon-password">
                </div>
                <small id="passwordHelpBlock" class="form-text text-danger pb-2">
                    <c:out value="${requestScope.loginMessage}"/>
                </small>
                <button type="submit" class="btn btn-info font-weight-bold float-right">Đăng nhập</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    $(function () {
        $('#username').focus()
    })
</script>

