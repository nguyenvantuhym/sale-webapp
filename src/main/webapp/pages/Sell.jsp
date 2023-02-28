<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Emddi
  Date: 6/30/2022
  Time: 9:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.*" pageEncoding="utf-8" %>
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
    <style>

        .grid-container {
            display: grid;
            grid-template-columns: auto auto auto auto;
            grid-gap: 10px;
            padding: 10px;
        }
        .grid-item {

            display: flex;
            flex-direction: column;
            justify-content: center;
        }
    </style>
    <%@include file="../commons/Includes.jsp" %>

</head>
<body>
<div class="d-flex flex-column vh-100 overflow-hidden">
    <%@include file="../commons/Nav.jsp" %>
    <div class="container-fluid d-flex flex-grow-1 p-0 w-100 overflow-hidden bg-light">
        <div class="row m-0 d-flex flex-row h-auto flex-nowrap w-100">
            <%@include file="../commons/SideBar.jsp" %>

            <div class="w-100 h-100 d-flex flex-column flex-nowrap shadow">
                <c:choose>
                    <c:when test="${param.cmd=='add'}">
                        <div class="container-fluid pt-2 h-100 d-flex flex-column overflow-auto" id="sell-content">
                            <div class="d-flex flex-row align-items-baseline">
                                <i class="fa fa-file-text fa-lg" aria-hidden="true">&nbsp;</i>
                                <h4>Bán vé</h4>
                            </div>
                            <div class="d-flex flex-row flex-wrap">
                                <div class="mr-auto align-items-baseline my-1">
                                    <form id="formReserveOrder"
                                          action="${pageContext.request.contextPath}/sell-ticket"
                                          class="d-flex flex-row">
                                        <div class="mr-1">Mã đặt</div>
                                        <input type="text" name="orderCode" id="orderCodeInput" class="mr-1">
                                        <button type="submit" class="btn btn-primary btn-sm"><i class="fa fa-search"
                                                                                                aria-hidden="true"></i>
                                            Tìm
                                        </button>
                                    </form>
                                </div>
                                <div class="d-flex flex-row flex-wrap my-1">
                                    <button type="button" class="btn btn-danger m-1" data-toggle="modal" style="width: 100px; height: 100px"
                                            data-target="#chooseGate">Chọn Quầy
                                    </button>
                                    <button type="button" class="btn btn-info m-1" id="btnSaveAndPrintSingle"  style="width: 100px; height: 100px"
                                            onclick="exportTicket('single')">
<%--                                        <i class="fa fa-print" aria-hidden="true"></i>--%>
                                        Lưu & Xuất vé
                                    </button>
                                    <button type="button" class="btn btn-info m-1"  style="width: 100px; height: 100px"
                                            onclick="exportTicketDemo()">
<%--                                        <i class="fa fa-print" aria-hidden="true"></i>--%>
                                        In thử
                                    </button>
                                        <%--
                                                                            <button type="button" class="btn btn-warning m-1" id="btnSaveAndPrintGroup"
                                                                                    onclick="exportTicket('group')">
                                                                                <i class="fa fa-print" aria-hidden="true"></i> Lưu & Xuất vé HĐ
                                                                            </button>
                                        --%>
                                    <button type="button" class="btn btn-success m-1" onclick="resetTicket()"  style="width: 100px; height: 100px">
<%--                                        <i class="fa fa-refresh" aria-hidden="true"></i> --%>
                                        Nhập mới
                                    </button>
                                </div>
                            </div>
                            <div class="d-flex flex-row align-items-baseline text-info mt-2">
                                <i class="fa fa-cube fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                                <h3 id="countersLabel" class="text-uppercase">
                                    <c:if test="${not empty sessionScope.currentGate}">
                                        <c:out value="${sessionScope.currentGate.getName()}"/>
                                    </c:if>
                                </h3>
                            </div>
                            <div class="border border-info rounded p-4 mb-4 flex-grow-1">
                                <div class="row">
                                    <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                                            <%--
                                                                                    <div class="form-group row flex-row-reverse">
                                                                                        <div class="col-7 col-sm-8 col-xl-7">
                                                                                            <input type="text"
                                                                                                   class="input-underline form-control form-control-sm w-100"
                                                                                                   id="mahd">
                                                                                        </div>
                                                                                        <label for="mahd"
                                                                                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Mã
                                                                                            HĐ</label>
                                                                                    </div>
                                            --%>


                                    </div>
                                    <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">

                                            <%--
                                                                                    <div class="form-group row flex-row">
                                                                                        <label for="orderCode"
                                                                                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Mã
                                                                                            đặt</label>
                                                                                        <div class="col-7 col-sm-8 col-xl-7">
                                                                                            <input type="text"
                                                                                                   class="input-underline form-control form-control-sm w-100"
                                                                                                   id="orderCode">
                                                                                        </div>
                                                                                    </div>
                                            --%>
                                            <%--
                                                                                    <div class="form-group row flex-row">
                                                                                        <label for="nguonkhach"
                                                                                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Nguồn
                                                                                            khách</label>
                                                                                        <div class="col-7 col-sm-8 col-xl-7">
                                                                                            <input type="text"
                                                                                                   class="input-underline form-control form-control-sm w-100"
                                                                                                   id="nguonkhach"
                                                                                                   placeholder="Nhập nguồn khách">
                                                                                        </div>
                                                                                    </div>
                                            --%>
                                            <%--
                                                                                    <div class="form-group row flex-row">
                                                                                        <label for="gianet"
                                                                                               class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Giá
                                                                                            NET</label>
                                                                                        <div class="col-7 col-sm-8 col-xl-7 d-flex align-items-center">
                                                                                            <input type="checkbox" id="gianet" style="width: 20px; height: 20px;"
                                                                                                   checked
                                                                                                   aria-label="Giá net">
                                                                                        </div>
                                                                                    </div>
                                            --%>
                                    </div>
                                        <%--
                                                                            <div class="col-12 col-sm-12 col-md-6 col-lg-6 col-xl-4">
                                                                                <div class="form-group row d-none d-xl-block">
                                                                                    <div class="col-12 form-control-sm">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row d-none d-xl-block">
                                                                                    <div class="col-12 form-control-sm">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row d-none d-xl-block">
                                                                                    <div class="col-12 form-control-sm border-bottom border-dark rounded-0">
                                                                                    </div>
                                                                                </div>
                                                                                <div class="form-group row flex-row">
                                                                                    <label for="phanTramThue"
                                                                                           class="col-5 col-sm-4 col-lg-5 col-form-label col-form-label-sm text-right">Thuế/Phí
                                                                                        DV</label>
                                                                                    <div class="col-7 col-sm-8 col-lg-7 d-flex flex-row">
                                                                                        <div class="col-6">
                                                                                            <input type="text"
                                                                                                   class="input-underline form-control form-control-sm w-100"
                                                                                                   id="phanTramThue" value="10">
                                                                                        </div>
                                                                                        <div class="col-6">
                                                                                            <input type="text" id="phanTramPhiDV"
                                                                                                   class="input-underline form-control form-control-sm w-100"
                                                                                                   value="0">
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                        --%>
                                </div>

                                <div class="row mt-4 flex-wrap-reverse">
                                    <div class="col-12 col-xl-6">
                                        <div class="row">
                                            <div class="col-6">
                                                <div class="form-group row flex-row-reverse">
                                                    <div id="datepicker" class="col-7 col-sm-8 col-xl-7 input-group date"
                                                         data-date-format="dd-mm-yyyy">
                                                        <input class="form-control" type="text" readonly id="usageTime">
                                                        <div class="input-group-append">
                                                            <span class="input-group-text" id="addon-wrapping">
                                                                <i class="fa fa-calendar"></i>
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <label for="usageTime"
                                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Ngày sử dụng</label>
                                                </div>
                                                <div class="form-group row flex-row-reverse ">
<%--                                                    d-none--%>
                                                    <div class="col-7 col-sm-8 col-xl-7">
                                                        <input type="text"
                                                               class="input-underline form-control form-control-sm w-100"
                                                               disabled
                                                               id="bookingPersonName">
                                                    </div>
                                                    <label for="bookingPersonName"
                                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Người đặt</label>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="form-group row flex-row-reverse">
                                                    <div class="col-7 col-sm-8 col-xl-7">
                                                        <select class="custom-select" id="loai-khach">
                                                            <option value="1">Khách lẻ</option>
                                                            <option value="2">Khách đoàn</option>
                                                        </select>
                                                    </div>
                                                    <label for="loai-khach"
                                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">Loại khách</label>
                                                </div>
                                                <div class="form-group row flex-row">
<%--                                                    d-none--%>
                                                    <label for="bookingPersonEmail"
                                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                                        Email
                                                    </label>
                                                    <div class="col-7 col-sm-8 col-xl-7">
                                                        <input type="text"
                                                               class="input-underline form-control form-control-sm w-100"
                                                               disabled
                                                               id="bookingPersonEmail">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <table class="table table-sm  w-100 border border-info"
                                               id="tableTicket" style="min-height: 100px">
                                            <thead class="bg-info text-white">
                                            <tr>
                                                <th style="width: 30%">Loại vé</th>
                                                <th style="width: 20%">Số lượng</th>
                                                <th style="width: 20%">Giá</th>
                                                <th style="width: 20%">Thành tiền</th>
                                                <th style="width: 10%; max-width: 20px"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td class="font-weight-bold"></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                                <td></td>
                                            </tr>

                                            </tbody>
                                        </table>

                                        <div>
                                            <table class="table table-sm w-100 border border-secondary mb-1"
                                                   id="tablePayment">
                                                <thead class="bg-light">
                                                <tr>
                                                    <th>Khách trả</th>
                                                    <th>Giảm giá (%)</th>
                                                    <th>Tổng tiền</th>
                                                    <th id="datCocHeader" class="d-none" style="width: 150px">Đặt cọc
                                                    </th>
                                                    <th>Trả lại</th>
                                                    <th>Thanh toán</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td class="border-top-0">
                                                        <div class="input-group w-100">
                                                            <input type="text"
                                                                   class="form-control text-right input-bill"
                                                                   id="so-tien-khach-dua"
                                                                   aria-describedby="basic-addon2">
                                                            <div class="input-group-append">
                                                                <span class="input-bill input-group-text pl-1"
                                                                      id="basic-addon2">000</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="input-group w-100">
                                                            <input type="text"
                                                                   class="form-control text-right input-bill"
                                                                   style="max-width: 100px"
                                                                   id="phan-tram-giam-gia" disabled
                                                                   aria-describedby="so-tien-giam-gia">
                                                            <div class="input-group-append">
                                                                <span class="input-group-text input-bill"
                                                                      id="so-tien-giam-gia">0</span>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <input type="text"
                                                               class="w-100 form-control text-right input-bill"
                                                               id="tong-tien" disabled/>

                                                    </td>
                                                    <td id="datCocSoTien" class="d-none">
                                                        <input type="text"
                                                               class="w-100 form-control text-right input-bill"
                                                               id="datCocSoTienInput" disabled/>

                                                    </td>
                                                    <td><input type="text"
                                                               class="w-100 form-control text-right input-bill"
                                                               id="thoi-lai" disabled/></td>
                                                    <td>
                                                        <select class="custom-select"
                                                                style="width: 150px"
                                                                id="payment-method">
                                                            <option value="0">Tiền mặt</option>
                                                            <option value="1">Chuyển khoản</option>
                                                        </select>
                                                    </td>
                                                </tr>

                                                </tbody>
                                            </table>
                                                <%--
                                                                                                                                    <button type="button" class="btn btn-primary" id="btnAddPayment" onclick="addPayment()">
                                                                                                                                        <i class="fa fa-plus" aria-hidden="true"></i> Thêm
                                                                                                                                        thanh toán
                                                                                                                                    </button>
                                                --%>
                                        </div>
                                    </div>
                                    <div class="col-12 col-xl-6 mb-4 mb-xl-0" style="overflow: visible">
                                        <div class="grid-container">
                                            <c:forEach var="price" items="${sessionScope.prices}" varStatus="loop">

                                                    <div type="button"
                                                            class="btn btn-outline-secondary w-80 text-center bg-gray"
                                                            style="text-transform: uppercase; font-size: 16px; display: flex;flex-direction: column;justify-content: center;  <c:out value='${loop.index%4==0?"aspect-ratio: 1 / 1;":""}'/> "
                                                            onclick="addBill(<c:out value="${price.toString()}"/>)"
                                                    >
                                                        <p>
                                                            <i class="fa fa-ticket" aria-hidden="true"></i>
                                                            <c:out value="${price.getPriceName()}"/> - <c:out value="${price.getDecimalPrice()}"/>
                                                        </p>
                                                    </div>
                                            </c:forEach>
                                        </div>

                                    </div>
                                </div>

                                    <%--                                <div class="row">--%>
                                    <%--                                    <div class="col-12 col-xl-8 mt-4">--%>
                                    <%--                                    </div>--%>
                                    <%--                                </div>--%>
                            </div>

                        </div>

                        <div class="modal fade" id="chooseGate" data-backdrop="static" data-keyboard="false"
                             tabindex="-1" aria-labelledby="chooseGateLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="chooseGateLabel">Chọn quầy mặc định</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="d-flex p-2 justify-content-around">
                                            <c:forEach var="gate" items="${sessionScope.listGate}">
                                                <button type="button" class="btn btn-warning text-uppercase"
                                                        onclick="setCurrentGate(<c:out
                                                                value="${gate.toJson()}"/>)">
                                                    <c:out value="${gate.getName()}"/>
                                                </button>
                                            </c:forEach>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>

                    <c:when test="${param.cmd=='export'}">
                        <div class="container-fluid pt-2 overflow-auto" id="print-content">
                            <div class="d-flex flex-row flex-wrap">
                                <div class="d-flex flex-row mr-auto align-items-baseline my-1">
                                    <button type="button" class="btn btn-outline-dark" id="btnReturnSell">
                                        <i class="fa fa-arrow-left" aria-hidden="true"></i> Quay lại
                                    </button>
                                </div>
                                <div class="d-flex flex-row flex-wrap my-1">
                                    <i class="fa fa-print fa-5x" aria-hidden="true" onclick="printTicket()"></i>
                                </div>
                            </div>
                            <div id="ticket-to-print" class="p-4">
                                <c:forEach var="ticket" items="${requestScope.ticketsToExport}" varStatus="loop">
                                    <div style="width: 437px; height: 620px" class="shadow-sm">
                                        <table class="w-100">
                                            <tbody>
                                            <tr>
                                                <td colspan="2">&nbsp;</td>
                                            </tr>

                                            <tr class="text-center align-items-center font-weight-bold">
                                                <td colspan="2" style="text-transform: uppercase">Công ty cổ phần VNPass - Vé Việt</td>
                                            </tr>
                                            <tr class="text-center align-items-center">
                                                <td colspan="2">MST: 240xxxxxxx</td>
                                            </tr>
                                            <tr class="text-center align-items-center">
                                                <td colspan="2">ĐT: 1900xxxx</td>
                                            </tr>
                                            <tr class="text-center align-items-center">
                                                <td colspan="2" class="" style="min-width: 150px">
                                                    <img src="data:image/png;base64,<c:out value="${ticket.toQrBase64(220,220)}"/>"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" class="text-center font-weight-bold"
                                                    style="font-size: 14px">
                                                    <div class="text-uppercase"><c:out
                                                            value="${ticket.getCardNo()}"/></div>
                                                </td>
                                            </tr>
                                            <tr class="text-center align-items-center font-weight-bold">
                                                <td colspan="2" style="font-size: 24px">
                                                    <c:out value="${ticket.getPriceObj().getPriceName()}"/>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td colspan="2">&nbsp;</td>
                                            </tr>
                                            <tr style="font-size: 18px" class="font-weight-bold">
                                                <td class="pl-4">Giá vé (VNĐ):</td>
                                                <td class="pr-4" style="text-align: right"><c:out
                                                        value="${ticket.getPriceObj().getDecimalPrice()}"/></td>
                                            </tr>
                                            <tr style="font-size: 18px">
                                                <td class="pl-4">Ngày sử dụng:</td>
                                                <td class="pr-4" style="text-align: right"><c:out
                                                        value='${ticket.formatDateTime("yyyy-MM-dd","dd/MM/yyyy", ticket.getUsageTime())}'/></td>
                                            </tr>

                                            <tr style="font-size: 18px">
                                                <td class="pl-4">Thu ngân:</td>
                                                <td class="pr-4" style="text-align: right"><c:out
                                                        value="${ticket.getEmployee().getName()}"/></td>
                                            </tr>
                                            <tr style="font-size: 18px">
                                                <td class="pl-4">Giờ in:</td>
                                                <td class="pr-4" style="text-align: right"><c:out
                                                        value='${ticket.formatDateTime("yyyy-MM-dd HH:mm:ss","dd/MM/yyyy HH:mm:ss", ticket.getLastPrintedTime())}'/></td>
                                            </tr>
                                            <tr style="font-size: 18px">
                                                <td class="pl-4">Quầy:</td>
                                                <td class="pr-4" style="text-align: right"><c:out
                                                        value="${ticket.getGate().getName()}"/></td>
                                            </tr>

                                            <tr class="text-center align-items-center">
                                                <td colspan="2" class="pl-4 font-weight-bold"
                                                    style="font-size: 16px">
                                                    <div class="">Lưu ý: Vé chỉ có giá trị sử dụng 1 lần trong ngày -
                                                        KHÔNG ĐỔI TRẢ
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2">&nbsp;</td>
                                            </tr>
                                                <%--                                            <tr>--%>
                                                <%--                                                <td colspan="2" class="font-italic pl-4" style="font-size: 12px">--%>
                                                <%--                                                    <div>(Vé có giá trị để sử dụng và căn cứ xuất hóa đơn GTGT cho khách--%>
                                                <%--                                                        hàng)--%>
                                                <%--                                                    </div>--%>
                                                <%--                                                </td>--%>
                                                <%--                                            </tr>--%>
                                                <%--                                            <tr>--%>
                                                <%--                                                <td colspan="2" class="font-italic pl-4" style="font-size: 12px">--%>
                                                <%--                                                    <div class="font-weight-bold">Vé đã qua cổng soát vé không đổi--%>
                                                <%--                                                        trả.--%>
                                                <%--                                                    </div>--%>
                                                <%--                                                    <div>The ticket has used, do not return.</div>--%>
                                                <%--                                                        &lt;%&ndash;                                                    <div>Vé đã qua cổng không được hoàn lại. Cảm ơn quý khách!</div>&ndash;%&gt;--%>
                                                <%--                                                    <div>Vé được in bởi phần mềm của Emddi Group</div>--%>
                                                <%--                                                </td>--%>
                                                <%--                                            </tr>--%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div style="break-after: page; height: 10px"></div>
                                </c:forEach>
                            </div>
                        </div>
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


<script type="text/javascript">
    function prepareDatePicker() {
        $("#datepicker").datepicker({
            autoclose: true,
            todayHighlight: true,
            weekStart: 1,
            format: 'dd/mm/yyyy'
        }).datepicker('update', new Date());
    }

    $(function () {
        let cmd = getUrlParameter("cmd")
        if (cmd === "export") {
            setTimeout(printTicket, 100)
        }
        if (cmd === "add") {
            prepareDatePicker()
            if (!gateId) openChooseModal()
        }
    });

    let gateId = <c:out value="${sessionScope.currentGate.getId()}"/>;
    let bills = []
    let printData = []
    let soTienKhachDua = $('#so-tien-khach-dua')
    let tongTien = $('#tong-tien')
    let phanTramGiamGia = $('#phan-tram-giam-gia')
    let soTienGiamGia = $('#so-tien-giam-gia')
    let thoiLai = $('#thoi-lai')
    let orderId = null;
    let bookingPersonName = $('#bookingPersonName')
    let bookingPersonEmail = $('#bookingPersonEmail')
    let orderCodeInput = $('#orderCodeInput')
    let loaiKhachSelect = $('#loai-khach')
    let datCocHeader = $('#datCocHeader')
    let datCocSoTien = $('#datCocSoTien')
    let datCocSoTienInput = $('#datCocSoTienInput')
    let paymentMethod = $('#payment-method')

    const host = $(location).attr('protocol') + '//' + $(location).attr('host');

    function addBill(price) {
        let quantity = 1;
        if (!isNaN(quantity)) {
            let usageTime = $('#usageTime').val();

            let {priceId, price: priceVal, priceName} = price

            let index = bills.findIndex(bill => bill.priceId === priceId);
            if (index < 0) {

                let bill = {priceId, price: priceVal, priceName, quantity, usageTime}
                bills.push(bill)
            } else {
                let currentQuantity = bills[index].quantity;
                bills[index] = {...bills[index], quantity: currentQuantity + quantity}
            }

            updateTicketTable()
        }
    }

    function removeBill(index) {
        bills.splice(index, 1)
        updateTicketTable()
    }

    function changeQuantity(index, quantity) {
        bills[index].quantity = quantity
        updateTicketTable()
    }

    function updateTicketTable() {
        let ticketTable = $('#tableTicket tbody')
        ticketTable.empty();
        let discount = phanTramGiamGia.val()
        if (!discount) discount = 0

        bills.forEach((bill, index) => {
            let ticketRow = $('<tr>', {id: 'row-' + index})
                .append($('<td>', {class: "font-weight-bold text-uppercase"}).append(bill.priceName))
                // .append($('<td>').append(bill.quantity))
                .append($('<td>').append(
                    $('<input>', {type: 'text', value: bill.quantity, class: 'input-border-0', style: "width: 90%"})
                        .focus(function () {
                            $(this).select();
                        })
                        .keydown(function (e) {
                            if (e.keyCode === 13) {
                                e.preventDefault();
                                changeQuantity(index, $(this).val())
                                updateGiamGia()
                            }
                        })
                        .blur(function (e) {
                            changeQuantity(index, $(this).val())
                            updateGiamGia();
                        })
                ))
                .append($('<td>').append(bill.price.toLocaleString()))
                // .append($('<td>').append(discount))
                .append($('<td>').append((bill.price * bill.quantity * (100) / 100).toLocaleString()))
                .append($('<td>').append($('<i>', {class: "fa fa-times text-danger"}).attr("aria-hidden", "true").click(() => removeBill(index))))
            ticketTable.append(ticketRow)
        })

        if (bills.length === 0) ticketTable.append('<tr><td class="font-weight-bold"></td><td></td><td></td><td></td><td></td></tr>')

        updateBill()
    }

    function updateBill() {
        let totalPrice = bills.reduce((sum, bill) => {
            return sum + bill.price * bill.quantity;
        }, 0)
        // let phanTramThue = $('#phanTramThue').val() || 0
        // let phanTramPhiDV = $('#phanTramPhiDV').val() || 0
        // let datCoc = parseInt($('#dat-coc').val().replaceAll(',', '')) || 0

        // $('#doanh-thu-thuan').val((totalPrice * (100 - phanTramThue) / 100).toLocaleString())
        // $('#tien-phi-dich-vu').val((totalPrice * phanTramPhiDV / 100).toLocaleString())
        // $('#tien-thue').val((totalPrice * phanTramThue / 100).toLocaleString())
        // $('#con-phai-thanh-toan').val((totalPrice - datCoc).toLocaleString())
        tongTien.val(totalPrice.toLocaleString())
        // soTienKhachDua.val(totalPrice.toLocaleString())
        // soTienThanhToan.val(totalPrice.toLocaleString())

        updateGiamGia()
    }

    soTienKhachDua.keyup(() => {
        let prevVal = soTienKhachDua.val().replace(/[^0-9]+/g, "")
        let nextVal = (parseInt(prevVal) || 0)
        soTienKhachDua.val(nextVal.toLocaleString())

        updateMoney()
    })

    function updateGiamGia() {
        let prevVal = phanTramGiamGia.val().replace(/[^0-9]+/g, "")
        let nextVal = (parseInt(prevVal) || 0)
        phanTramGiamGia.val(nextVal.toLocaleString())

        let soTienDuocGiam = 0;
        bills.forEach(bill => {
            if (bill.applyDiscount) {
                soTienDuocGiam += ((bill.quantity * bill.price) / ((100 + bill.vat) / 100)) * nextVal / 100
            }
        })

        soTienGiamGia.text('= ' + soTienDuocGiam.toLocaleString(undefined, {maximumFractionDigits: 0}))
        updateMoney()
    }

    phanTramGiamGia.keyup(() => {
        updateTicketTable()

    }).focus(() => phanTramGiamGia.select())

    function exportTicket(type) {
        if (bills.length > 0) {
            let name = bookingPersonName.val() || ''
            // let phone = bookingPersonPhone.val() || ''
            let loaiKhach = loaiKhachSelect.val() || 1

            let form = document.createElement("form");
            document.body.appendChild(form);

            bills.forEach(item => {
                $(form).append("<input type='hidden' name='bills[]' value='" + JSON.stringify(item) + "'>");
            })
            // $(form).append("<input type='hidden' name='bookingPersonName' value='" + name + "'>");
            // $(form).append("<input type='hidden' name='bookingPersonPhone' value='" + phone + "'>");
            $(form).append("<input type='hidden' name='gateId' value='" + gateId + "'>");
            $(form).append("<input type='hidden' name='customerType' value='" + loaiKhach + "'>");
            $(form).append("<input type='hidden' name='paymentMethod' value='" + (paymentMethod.val() || 0) + "'>");
            if (orderId)
                $(form).append("<input type='hidden' name='orderId' value='" + orderId + "'>");

            form.action = host + "/sell-ticket?cmd=export&action=create&type=" + type
            localStorage.setItem("previousUrl", window.location.href);
            form.method = "post";
            form.submit();
            $(form).remove();
        } else {
            alert('Thêm ít nhất 1 loại vé và thử lại')
        }
    }

    $('#btnReturnSell').click(() => {
        // window.history.back();
        backPreviousPage();
        // window.location.href = host + '/sell-ticket?cmd=add'
    })

    const exportTicketDemo = () => {
        let form = document.createElement("form");
        document.body.appendChild(form);
        form.action = "/ticket-print?cmd=export&option=demo";
        form.method = "POST";
        form.submit();
        $(form).remove();
    }

    function backPreviousPage() {
        let backUrl = localStorage.getItem("previousUrl");
        if (!backUrl) {
            window.history.back();
        } else {
            localStorage.removeItem("previousUrl");
            window.location.href = backUrl;
        }
    }

    function updateMoney() {
        let valTongTien = tongTien.val().replace(/[^0-9]+/g, "");
        let valKhachDua = soTienKhachDua.val().replace(/[^0-9]+/g, "");
        let valGiamGia = soTienGiamGia.text().replace(/[^0-9]+/g, "");
        let valDatCoc = datCocSoTienInput.val().replace(/[^0-9]+/g, "");
        thoiLai.val(((parseInt(valKhachDua) || 0) * 1000 - ((parseInt(valTongTien) || 0) - (parseInt(valGiamGia) || 0)) + parseInt(valDatCoc || 0)).toLocaleString())
    }

    function printTicket() {
        let win = window.open()

        win.onafterprint = (event) => {
            backPreviousPage();
            // window.location.href = host + '/sell-ticket?cmd=add'
            win.close()
        }

        $.each($('img.rounded'), (key, img) => {
            img.src = host + '/image/company/ticket-logo.png'
        })

        let ticketContent = $("#ticket-to-print").html()

        $(win.document.head).append('<script type="text/javascript" src="' + host + '/js/jquery-3.6.0.min.js"/>')
        $(win.document.head).append('<script type="text/javascript" src="' + host + '/js/qrcode.min.js"/>')
        $(win.document.head).append('<script type="text/javascript" src="' + host + '/css/bootstrap/bootstrap.bundle.min.js"/>')
        $(win.document.head).append('<link rel="stylesheet" type="text/css" href="' + host + '/css/bootstrap/bootstrap.min.css" media="all">')
        $(win.document.body).html(ticketContent)

        win.print()
    }

    function openChooseModal() {
        $('#chooseGate').modal('show')
    }

    function setCurrentGate(c) {
        let form = document.createElement("form")
        document.body.appendChild(form)

        $(form).append("<input type='hidden' name='gateId' value='" + c.id + "'>")

        form.action = host + "/sell-ticket?cmd=add&action=set-gate"
        form.method = "post"
        form.submit()
        $(form).remove()
    }

    $('#formReserveOrder').submit(function (event) {
        event.preventDefault()
        let orderCode = orderCodeInput.val()
        if (orderCode !== "") {
            $.post({
                url: "/sell-ticket?cmd=query",
                data: {orderCode, type: 'group'},
                success: function (res) {
                    if (res.success) {
                        bills = res.data.bills

                        bookingPersonName.val(res.data.fullName + " - " + res.data.phoneNumber)
                        bookingPersonName.parent().parent().removeClass('d-none')
                        bookingPersonEmail.val(res.data.email)
                        bookingPersonEmail.parent().parent().removeClass("d-none")
                        $("#datepicker").datepicker('update', res.data.usageTime)
                        phanTramGiamGia.val(res.data.discount)
                        datCocHeader.removeClass('d-none')
                        datCocSoTien.removeClass('d-none')
                        datCocSoTienInput.val(parseInt(res.data.preMoney).toLocaleString())
                        paymentMethod.val(res.data.paymentMethod)

                        orderId = res.data.orderId
                        loaiKhachSelect.val(2)
                        phanTramGiamGia.prop("disabled", false)

                        updateTicketTable()
                    } else {
                        alert(res.message)
                    }
                },
                dataType: 'json'
            });
        } else alert("Nhập mã đặt trước khi tìm kiếm!!!")
    })

    loaiKhachSelect.change(() => {
        if (loaiKhachSelect.val() == 2) {
            phanTramGiamGia.prop("disabled", false)
        } else {
            phanTramGiamGia.prop("disabled", true)
        }
    })

    function resetTicket() {
        bills = []
        soTienKhachDua.val('')
        phanTramGiamGia.val('')
        loaiKhachSelect.val(1)
        orderCodeInput.val('')
        orderId = null
        $('#datepicker').datepicker('update', new Date())
        bookingPersonName.parent().parent().addClass("d-none")
        bookingPersonEmail.parent().parent().addClass("d-none")
        datCocHeader.addClass('d-none');
        datCocSoTien.addClass('d-none')
        datCocSoTienInput.val('')
        paymentMethod.val(0)
        phanTramGiamGia.prop("disabled", true)
        updateTicketTable()
    }
</script>
