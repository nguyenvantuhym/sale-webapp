<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Emddi
  Date: 6/30/2022
  Time: 9:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<c:set var="printTime" value="${LocalDateTime.now().format(DateTimeFormatter.ofPattern('HH:mm dd/MM/yyyy'))}"/>
<html>
<head>
  <title>Đặt vé</title>
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <%@include file="../commons/Includes.jsp" %>
  <style>
    .box-spinner{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 10;
      opacity: .8;
      background-color: black;
    }
    .spinner-submit {
      position: absolute;
      top: 50%;
      left: 50%;
      margin-top: -20px;
      margin-left: -25px;
      width: 50px;
      height: 50px;
      text-align: center;
      color: white;
      font-size: 10px;
    }
  </style>
</head>
<body>

<div class="d-flex flex-column vh-100 overflow-hidden">
  <%@include file="../commons/Nav.jsp" %>
  <div class="container-fluid d-flex flex-grow-1 p-0 w-100 overflow-hidden">
    <div class="row m-0 d-flex flex-row h-auto flex-nowrap w-100">
      <%@include file="../commons/SideBar.jsp" %>
      <div class="w-100 h-100 d-flex flex-column flex-nowrap shadow">
            <div class="container-fluid pt-2 overflow-auto" id="sell-content">
              <form method="post" id="form-filter">
                <input type="hidden" id="pageSize" name="pageSize" value="20"/>
                <input type="hidden" id="pageNumber" class="page-link" readonly name="pageNumber" value="1" style="width: 90px; text-align: center"/>
                <%--  <input type="hidden" id="pageNumber" name="pageNumber" value="1"/>--%>

                <div class="d-flex flex-row align-items-baseline text-info mt-2">
                  <i class="fa fa-cube fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                  <h3 id="countersLabel" class="text-uppercase"><c:out
                          value="${sessionScope.currentCounters.getName()}"/></h3>
                </div>
                <div class="border border-info rounded p-4 mb-4">
                  <div class="row">
                    <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                      <div class="form-group row flex-row-reverse">
                          <div class="col-7 col-sm-8 col-xl-7 input-group date" id="datetimepicker1" data-target-input="nearest">
                            <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker1" name="dateFrom" data-toggle="datetimepicker"/>
                            <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                              <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                            </div>
                          </div>
                        <label for="datetimepicker1" class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                            Từ lúc
                        </label>
                      </div>
                      <div class="form-group row flex-row-reverse">
                        <div class="col-7 col-sm-8 col-xl-7">
                          <select class="custom-select" id="dich-vu" name="service">
                            <option value="ALL">Tất cả</option>
                            <c:forEach var="prices" items="${sessionScope.pricesReport}">
                              <option value="<c:out value="${prices.getPriceId()}"/>"><c:out value="${prices.getPriceName()}"/></option>
                            </c:forEach>

                          </select>
                        </div>
                        <label for="dich-vu"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Dịch vụ:</label>
                      </div>


                      <div class="form-group row flex-row">
                        <label for="loai-ve"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Loại vé:</label>
                        <div class="col-7 col-sm-8 col-xl-7">
                          <select class="custom-select" id="loai-ve" name="ticketType">
                            <option value="all">Tất cả</option>
                            <option value="1">Vé lẻ</option>
                            <option value="2">Vé đoàn</option>
                            <option value="3">Vé bán qua web</option>
                            <option value="4">Vé in trước</option>
                          </select>
                        </div>
                      </div>

                      <div class="form-group row flex-row-reverse">
                        <div class="col-7 col-sm-8 col-xl-7">
                          <input type="text"
                                 class="input-underline form-control w-100"
                                 id="ticket-code" name="ticketCode"
                                 placeholder="Mã vé">
                        </div>
                        <label for="ticket-code"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Mã vé: </label>
                      </div>

                    </div>
                    <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                      <div class="form-group row flex-row">
                          <label for="datetimepicker2" class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                              Đến lúc
                          </label>
                          <div class="col-7 col-sm-8 col-xl-7 input-group date" id="datetimepicker2" data-target-input="nearest">
                              <input type="text" class="form-control datetimepicker-input" data-target="#datetimepicker2" name="dateTo" data-toggle="datetimepicker"/>
                              <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                                  <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                              </div>
                          </div>
                      </div>
                      <div class="form-group row flex-row">
                        <label for="nhan-vien"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Nhân viên bán:</label>
                        <div class="col-7 col-sm-8 col-xl-7">
                          <select class="custom-select" id="nhan-vien" name="staff">
                            <c:forEach var="employee" items="${sessionScope.employees}">
                              <option value="<c:out value="${employee.getEmployeeId()}"/>" <c:out value="${employee.getEmployeeId() == (sessionScope.currentEmployee.getEmployeeId())? 'selected': ''}"/>><c:out value="${employee.getName()}"/></option>
                            </c:forEach>

                          </select>
                        </div>
                      </div>
                      <div class="form-group row flex-row">
                        <label for="trangthai-ve"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Trạng thái vé:</label>
                        <div class="col-7 col-sm-8 col-xl-7">
                          <select class="custom-select" id="trangthai-ve" name="ticketStatus">
                            <option value="all">Tất cả</option>
                            <option value="0">Vé chưa sử dụng</option>
                            <option value="1">Vé đã sử dụng</option>
                            <option value="2">Vé đã sử dụng 1 lần</option>
                            <option value="-1">Vé đặt qua web chưa thanh toán vnpay</option>
                            <option value="-2">Vé đã hủy bởi QTV</option>
                            <option value="-3">Vé đã hủy tại quầy</option>
                          </select>
                        </div>
                      </div>

                      <div class="form-group row flex-row">
                        <label for="payment-method"
                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Thanh toán:</label>
                        <div class="col-7 col-sm-8 col-xl-7">
                          <select class="custom-select" id="payment-method" name="paymentMethod">
                            <option value="ALL">Tất cả</option>
                            <option value="0">Tiền mặt</option>
                            <option value="1">Chuyển khoản</option>
                          </select>
                        </div>
                      </div>

                    </div>
                    <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                      <div class="row">
                        <div class="col-12">
                          <button type="button" class="btn btn-primary font-weight-bold " onclick="clickSentFilter()">
                            <i class="fa fa-filter" aria-hidden="true"></i> Tìm kiếm
                          </button>
                          <button type="button" class="btn btn-success font-weight-bold" onclick="printTicket()">
                            <i class="fa fa-print" aria-hidden="true"></i> In vé đã chọn
                          </button>
                        </div>
                        <div class="col-12 mt-3">

<%--                          <button type="button" class="btn btn-warning font-weight-bold text-white" onclick="onSaleReport()">--%>
<%--                            <i class="fa fa-money" aria-hidden="true"></i> Doanh thu--%>
<%--                          </button>--%>
                          <button type="button" class="btn btn-danger font-weight-bold" onclick="cancelTicket()">
                            <i class="fa fa-times" aria-hidden="true"></i> Hủy vé đã chọn
                          </button>
                        </div>
                      </div>



                    </div>
                  </div>

                  <div class="row mt-4">
                    <div class="col-12 col-xl-12 ">

                      <table class="table table-sm table-responsive-sm w-100 border border-info"
                             id="tableTicket" style="min-height: 100px">
                        <div class="box-spinner" id="spinner-box">
                          <div class="spinner-border spinner-submit" role="status" >
                            <span class="sr-only">Loading...</span>
                          </div>
                        </div>
                        <thead class="bg-info text-white">
                        <tr>
                          <th>#</th>
                          <th>Dịch vụ</th>
                          <th>Mã vé</th>
                          <th>Ngày bán</th>
                          <th>Trạng thái</th>
                          <th>Giá bán</th>
                          <th>Thanh toán</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                      </table>

                    </div>

                  </div>
                  <div class="row">
                    <div class="col-12 col-xl-12 ">
                      <nav aria-label="Page navigation example">
                        <ul class="pagination">
                          <li class="page-item"><button class="page-link" type="button" id="previousBtn" onclick="previousPage()">Previous</button></li>
                          <li class="page-item"><button class="page-link" type="button" id="currentPage">Previous</button></li>
                          <li class="page-item"><button class="page-link" type="button" id="nextBtn" onclick="nextPage()">Next</button></li>
                        </ul>
                      </nav>
                    </div>
                  </div>
                </div>

              </form>
            </div>
      </div>
    </div>
  </div>
</div>

<div class="modal" tabindex="-1" role="dialog" id="dialog-report" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Báo cáo doanh thu</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="modal-content">
        <table class="table table-sm table-responsive-sm w-100 border border-info"
               style="min-height: 100px">

          <thead class="bg-info text-white">
          <tr>
            <th>#</th>
            <th>Dịch vụ</th>
            <th>Số vé bán được</th>
            <th>Doanh thu</th>
          </tr>
          </thead>
          <tbody id="sales-report-content">

          </tbody>
        </table>
        Tổng: <span id="sum"></span>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
      </div>
    </div>
  </div>
</div>




</body>

<script type="text/javascript">

  const host = $(location).attr('protocol') + '//' + $(location).attr('host');
  let tickets = [];
  $(function () {
    let start = new Date();
    start.setHours(0,0,0,0);

    let end = new Date();
    end.setHours(23,59,59,999);
    let datetime2 = $('#datetimepicker2').datetimepicker({
      useCurrent: true,
      defaultDate: end,
      format : 'YYYY-MM-DD HH:mm'
    });
    $('#datetimepicker2').datetimepicker({
      useCurrent: true,
      defaultDate: start,
      format : 'YYYY-MM-DD HH:mm'
    });
    console.log(datetime2);

    $('#datetimepicker1').datetimepicker({
      defaultDate: start,
      format : 'YYYY-MM-DD HH:mm'
    });

  });

  function resetPageNumber (){
    let pageNumber = $('#pageNumber');
    pageNumber.val(1);
  }

  $('#form-filter').submit(function(event){
    event.preventDefault();
    const myFormData = $(this).serializeArray();
    let obj ={}
    myFormData.forEach((el) => obj[el.name] = el.value);
    $('#currentPage').text(obj.pageNumber);
    $('#spinner-box').show();
    $.ajax({
      url: '/ticket-report',
      type: 'POST',
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: obj
    }).then(res => {
      $('#spinner-box').hide();
      tickets = res.ticket_list;
      reRenderTable(res.ticket_list);


      if(!res.has_next_page) {
        $('#nextBtn').prop('class', 'btn btn-outline-secondary');
        $('#nextBtn').prop('disabled', true);
      } else {
        $('#nextBtn').prop('class', 'page-link');
        $('#nextBtn').prop('disabled', false);
      }
      let pageNumber = $('#pageNumber');
      let valPageNumber = Number(pageNumber.val());
      if(valPageNumber > 1) {
        $('#previousBtn').prop('class', 'page-link');
        $('#previousBtn').prop('disabled', false);
      } else {
        $('#previousBtn').prop('class', 'btn btn-outline-secondary');
        $('#previousBtn').prop('disabled', true);
      }
    });
  });

  function clickSentFilter() {
    resetPageNumber();
    $('#form-filter').submit();
  }


  function reRenderTable(dataTable) {
    console.log(dataTable);
    let tbody = $('tbody');
    tbody.html('');
    dataTable.forEach((dataRow, index) => {
      let row = $('<tr/>');
      if(dataRow.status == -3 || dataRow.status == -2 || dataRow.status == 1) {
        row.append('<td></td>');
      } else {
        row.append('<td><input type="checkbox" class="ticket-checkbox" aria-label="Checkbox for following text input" data-id="'+dataRow.ticket_id+'"> </td>');
      }
      row.append('<td>'+dataRow.price_name+'</td>');
      row.append('<td>'+dataRow.card_no+'</td>');
      row.append('<td>'+moment(dataRow.created_at).format('DD/MM/YYYY HH:mm:ss')+'</td>');

      row.append('<td>'+ticketStatus[dataRow.status]+'</td>');
      row.append('<td>'+dataRow.price+'</td>');
      row.append('<td>'+ paymentMethodLabel[dataRow.payment_method] +'</td>');
      tbody.append(row);
    });
  }

  function getIdsFromTable(){
    let arr = [];
    $('.ticket-checkbox:checked').each(function(i, obj) {
      arr.push(obj.getAttribute('data-id'));
    });
    return arr;
  }

  function buildFormSubmit(url, method, data){
    let form = document.createElement("form");
    document.body.appendChild(form);
    Object.keys(data).forEach(key => {
      let value = data[key];
      if(Array.isArray(value)){
        value.forEach(val => {
          $(form).append("<input type='hidden' name='"+key+"' value='" + val + "'>");
        })
      } else {
        $(form).append("<input type='hidden' name='"+key+"' value='" + value + "'>");
      }
    })
    form.action = url;
    form.method = method;
    form.submit();
    $(form).remove();
  }

  function printTicket() {
    let ids = getIdsFromTable();
    if(ids.length < 1) {
      alert("Hãy chọn ít nhất 1 vé để in.");
      return;
    }
    localStorage.setItem("previousUrl", window.location.href);
    let url = host + "/ticket-print?cmd=export";
    buildFormSubmit(url, "post", { ids });
  }

  function onSaleReport() {
    const myFormData = $('#form-filter').serializeArray();
    let obj ={}
    myFormData.forEach((el) => obj[el.name] = el.value);
    console.log(obj);

    $.ajax({
      url: '/sale-report',
      type: 'POST',
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: obj
    }).then(res => {
      let tableData = '';
      let salesSum = 0;
      res.report.forEach((row, index) => {
        salesSum += row.price_sum;
        tableData += "<tr><td>"+ (index + 1)+"</td><td>"+row.price_name+"</td><td>"+row.ticket_count+"</td><td>"+row.price_sum+"</td></tr>";
      })
      console.log(tableData);
      let tableString  = ``;
      console.log(tableString);
      $('#sum').html(salesSum);
      $('#sales-report-content').html(tableData);
      $('#dialog-report').modal('show');
    });
  }

  function cancelTicket() {
    let ids = getIdsFromTable();
    if(ids.length < 1) {
      alert("Hãy chọn ít nhất 1 vé để hủy.");
      return;
    }
    $.ajax({
      url: '/ticket-cancel',
      type: 'POST',
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: { ids }
    }).then(res => {
      console.log(res);
      $('#form-filter').submit();
    });

    // buildFormSubmit("/ticket-cancel", "post", { ids });
  }

  function previousPage(e) {
    let pageNumber = $('#pageNumber');
    let valPageNumber = Number(pageNumber.val());
    if(valPageNumber > 1) {
      pageNumber.val(valPageNumber - 1);
      $('#form-filter').submit();
    }
  }

  function nextPage() {
    let pageNumber = $('#pageNumber');
    let valPageNumber = Number(pageNumber.val());
    pageNumber.val(valPageNumber + 1);

    $('#form-filter').submit();
  }
  $( document ).ready(function() {
    $('#form-filter').submit();
  });

  const ticketStatus = {
    "0":"Vé chưa sử dụng",
    "1":"Vé đã sử dụng",
    "2":"Vé đã sử dụng 1 lần",
    "-1":"Vé đặt qua web chưa thanh toán vnpay",
    "-2":"Vé đã hủy bởi quản trị viên",
    "-3": "Vé đã hủy tại quầy"
  }

  const paymentMethodLabel = {
    "0":"Tiền mặt",
    "1":"Chuyển khoản",
  }
</script>

</html>
