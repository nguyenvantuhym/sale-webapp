<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Emddi
  Date: 12/14/2022
  Time: 2:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<html>
<head>
    <title>Quét vé</title>
    <%@include file="../commons/Includes.jsp" %>
</head>
<body>
<div class="d-flex flex-column vh-100 overflow-hidden">
    <%@include file="../commons/Nav.jsp" %>

    <div class="container-fluid d-flex flex-grow-1 p-0 w-100 overflow-hidden bg-light">
        <div class="row m-0 d-flex flex-row h-auto flex-nowrap w-100">
            <%@include file="../commons/SideBar.jsp" %>
            <div class="w-100 h-100">
                <div class="container-fluid pt-2 overflow-auto d-flex flex-column h-100" id="scan-content">
                    <div class="d-flex flex-row align-items-baseline">
                        <i class="fa fa-file-text fa-lg" aria-hidden="true">&nbsp;</i>
                        <h4>Quét vé</h4>
                    </div>

                    <div class="d-flex flex-row flex-wrap">
                        <div class="mr-auto align-items-baseline my-1">
                            <form id="formScan" method="post"
                                  class="d-flex flex-row">
                                <div class="mr-1">Mã vé</div>
                                <input type="text" name="ticketCode" id="ticketCodeScan" class="mr-1">
                                <button type="submit" class="btn btn-primary btn-sm"><i class="fa fa-search"
                                                                                        aria-hidden="true"></i>
                                    Tìm
                                </button>
                            </form>
                        </div>
                    </div>

                    <div class="d-flex flex-row align-items-baseline mt-2" id="statusLabel">
                        <i class="fa fa-cube fa-lg" aria-hidden="true">&nbsp;&nbsp;</i>
                        <h3 class="text-uppercase">
                            Hãy quét vé
                        </h3>
                    </div>

                    <div class="border border-info rounded p-4 mb-4 flex-grow-1 overflow-auto">
                        <div class="row mt-2">
                            <div class="col-6">
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100 text-center text-white"
                                               style="font-size: large; font-weight: bold;"
                                               id="available">
                                    </div>
                                    <label for="available"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Trạng thái</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="cardNo">
                                    </div>
                                    <label for="cardNo"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Mã vé</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="type">
                                    </div>
                                    <label for="type"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Loại vé</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="source">
                                    </div>
                                    <label for="source"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Nguồn</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="gateName">
                                    </div>
                                    <label for="gateName"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Quầy bán</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="staffName">
                                    </div>
                                    <label for="staffName"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Người bán</label>
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="createdAt">
                                    </div>
                                    <label for="createdAt"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Thời gian bán</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <input type="text" disabled
                                               class="input-underline form-control form-control-sm w-100"
                                               style="font-size: large; font-weight: bold;"
                                               id="usageTime">
                                    </div>
                                    <label for="usageTime"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Ngày hiệu lực</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <textarea type="text" disabled rows="4"
                                                  class="input-underline form-control form-control-sm w-100 overflow-auto"
                                                  style="font-size: large; font-weight: bold;"
                                                  id="printTime"></textarea>
                                    </div>
                                    <label for="printTime"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Thời gian in</label>
                                </div>
                                <div class="form-group row flex-row-reverse">
                                    <div class="col-7 col-sm-8 col-xl-7">
                                        <textarea type="text" disabled rows="4"
                                                  class="input-underline form-control form-control-sm w-100 overflow-auto"
                                                  style="font-size: large; font-weight: bold;"
                                                  id="scanTime"></textarea>
                                    </div>
                                    <label for="scanTime"
                                           style="font-size: large; font-weight: bold;"
                                           class="col-5 col-sm-4 col-xl-5 col-form-label col-form-label-sm text-right">
                                        Thời gian quét</label>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

<script type="text/javascript">
    $().ready(function () {
        $("#ticketCodeScan").focus().empty()
        $(window).focus(function () {
            $("#ticketCodeScan").focus().empty()
        });
    })


    $('#formScan').submit(function (e) {
        e.preventDefault()

        let ticketCode = $("#ticketCodeScan").val()
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/check-ticket",
            data: {
                ticketCode: ticketCode,
                cmd: "scan"
            },
            dataType: "json",
            success: function (result) {
                let statusLabel = $('#statusLabel')
                if (result.success) {
                    statusLabel.children('h3').text('Thông tin vé: ' + result.queryCode)
                    if (result.data.available) {
                        statusLabel.addClass('text-info').removeClass('text-danger')
                        $('#available').val('Còn hiệu lực').addClass('bg-info').removeClass('bg-danger')
                    } else {
                        statusLabel.addClass('text-danger').removeClass('text-info')
                        $('#available').val('Hết hiệu lực').addClass('bg-danger').removeClass('bg-info')
                    }

                    $('#cardNo').val(result.data.cardNo)
                    $('#type').val(result.data.ticketType)
                    $('#createdAt').val(result.data.createdAt)
                    $('#gateName').val(result.data.gateName)
                    $('#staffName').val(result.data.staffName)
                    $('#usageTime').val(result.data.usageTime)
                    $('#source').val(result.data.source)

                    let checkinLogs = result.data.checkinLogs
                    checkinLogs.forEach((logs, index) => {
                        // let way = '❓'
                        let way = ""
                        switch (logs.way) {
                            case 1:
                                way = '↗️'
                                break;
                            case 2:
                                way = '↘️'
                                break;
                        }
                        checkinLogs[index] = (index + 1) + ' - ' + logs.time + ' - ' + (logs.success ? '✔' : '❌') + ' - ' + way
                    })
                    $('#scanTime').val(checkinLogs.join('\r\n'))

                    let printTimes = result.data.printTime;
                    printTimes.forEach((time, index) => printTimes[index] = (index + 1) + ' - ' + time.time)
                    $('#printTime').val(printTimes.join('\r\n'))

                    $("#ticketCodeScan").focus().val('')
                } else {
                    statusLabel.children('h3').text('Không tìm thấy vé: ' + result.queryCode)
                    statusLabel.addClass('text-danger').removeClass('text-info')
                    resetData()
                }
            }
        })
    })

    function resetData() {
        $('#available').val('').removeClass('bg-info').remove('bg-danger')
        $('#cardNo').val('')
        $('#type').val('')
        $('#createdAt').val('')
        $('#gateName').val('')
        $('#staffName').val('')
        $('#usageTime').val('')
        $('#printTime').val('')
        $('#scanTime').val('')
        $('#source').val('')
    }

</script>
