<%@ page contentType="text/html;charset=UTF-8" %>
<div class="bg-white btn-group d-flex flex-column h-100 shadow-sm" style="width: 8rem;">
    <ul class="list-group list-group-flush">
        <li class="list-group-item p-0">
            <div class="d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/sell-ticket?cmd=add&type=single" class="w-100 px-2 py-4 text-center text-dark text-decoration-none"><i
                        class="fa fa-plus" aria-hidden="true"></i> Bán vé</a>
            </div>
        </li>
        <li class="list-group-item p-0">
            <div class="d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/check-ticket" class="w-100 px-2 py-4 text-center text-dark text-decoration-none"><i
                        class="fa fa-qrcode" aria-hidden="true"></i> Quét vé</a>
            </div>
        </li>
        <li class="list-group-item p-0">
            <div class="d-flex align-items-center">
                <a href="${pageContext.request.contextPath}/ticket-report" class="w-100 px-2 py-4 text-center text-dark text-decoration-none"><i
                        class="fa fa-file" aria-hidden="true"></i> Báo cáo vé</a>
            </div>
        </li>
    </ul>


</div>
