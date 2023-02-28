<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 03/01/2023
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<c:set var="printTime" value="${LocalDateTime.now().format(DateTimeFormatter.ofPattern('HH:mm dd/MM/yyyy'))}"/>
<html>
<head>
    <title>Đổi mật khẩu</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <%@include file="/commons/Includes.jsp" %>
</head>
<body>

<div class="d-flex flex-column vh-100 overflow-hidden">
  <%@include file="../commons/Nav.jsp" %>
<div class="container-fluid vh-100 vw-100 justify-content-center flex-column d-flex ">
  <div class="row justify-content-center">
    <div class="col-sm-6 col-lg-4 col-xl-3 bg-white p-4 m-4 rounded shadow-lg bg-transparent">
      <h2 class="mb-2 text-center text-primary">Đổi mật khẩu</h2>
      <form action="" method="post">
        <div class="input-group mb-3 rounded-pill border border-gray" style="border-width: 2px!important;">
          <div class="input-group-prepend">
            <span class="input-group-text bg-transparent border-0" id="addon-username"><i class="fa fa-key" aria-hidden="true"></i></span>
          </div>
          <input type="password" id="old-password" name="old-password" class="form-control border-0" placeholder="Mật khẩu cũ" aria-label="Tài khoản" aria-describedby="addon-username">
        </div>

        <div class="input-group mb-3 rounded-pill border border-gray" style="border-width: 2px!important;">
          <div class="input-group-prepend">
            <span class="input-group-text bg-transparent border-0" id="new--lbl"><i class="fa fa-key" aria-hidden="true"></i></span>
          </div>
          <input type="password" id="new-password" name="new-password" class="form-control border-0" placeholder="Mật khẩu mới" aria-label="Tài khoản" aria-describedby="addon-username">
        </div>

        <div class="input-group mb-3 rounded-pill border border-gray mt-4" style="border-width: 2px!important;">
          <div class="input-group-prepend ">
            <span class="input-group-text bg-transparent border-0" id="retype-password-lbl"><i class="fa fa-key" aria-hidden="true"></i></span>
          </div>
          <input type="password" id="retype-password" name="retype-password" class="form-control border-0" placeholder="Nhập lại mật khẩu" aria-label="Mật khẩu" aria-describedby="addon-password">
        </div>
        <small id="passwordHelpBlock" class="form-text text-danger pb-2">
          <c:out value="${requestScope.changePasswordMessage}"/>
        </small>
        <button type="button" class="btn btn-outline-primary font-weight-bold float-right" onclick="changePassword()">Thay đổi</button>
      </form>
    </div>
  </div>
</div>
</div>

</button>

<!-- Modal -->
<div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="body-modal">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="gohome-btn" onclick="gotoHomepage()">Close</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="ok-btn">OK
        </button>
      </div>
    </div>
  </div>
</div>
<script>

  function changePassword(){
    let oldPassword = $('#old-password').val();
    let newPassword = $('#new-password').val();
    let retypePassword = $('#retype-password').val();

    if(newPassword.length === 0 ||retypePassword.length === 0) {
      showModalError("Mật khẩu không được bỏ trống");
      return;
    }
    if(!(newPassword === retypePassword)) {
      showModalError("Mật khẩu mới chưa khớp, xin vui lòng nhập lại");
      return;
    }

    $.ajax({
      url: '/change-password',
      type: 'POST',
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        oldPassword,
        newPassword,
        retypePassword
      }
    }).then(res => {

      if (res.success == true){
        showModalSuccess(res.message);
      } else {
        showModalError(res.message);
      }
    })
  }

  function gotoHomepage() {
    location.href ='/'
  }

  function showModalError(message){
    $('#exampleModalLong').modal('show');
    $('#gohome-btn').hide();
    $('#ok-btn').show();
    $("#body-modal").html(message);
  }
  function showModalSuccess(message){
    $('#exampleModalLong').modal('show');
    $('#gohome-btn').show();
    $('#ok-btn').hide();
    $("#body-modal").html(message);
  }


</script>

</body>
</html>
