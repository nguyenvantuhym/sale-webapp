<%--
  Created by IntelliJ IDEA.
  User: nguye
  Date: 02/02/2023
  Time: 01:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sync manager</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <style>
        body {
            padding-top: 0px;
        }
        .form-control {
            margin-bottom: 10px;
        }
        .login-screen-bg {
            background-color: #EFEFEF;
        }
    </style>

</head>
<body>

<c:set var="name" value="Manisha" scope="page" />
Local Variable :
<c:out value="${name}" />


<div class="container">
    <h1 class="text-center">Sync management</h1>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 well well-sm col-md-offset-3">

            <c:forEach items="${requestScope.tasks}" var="task">
                <form action="?task=${task.getClass().getSimpleName()}" method="post" class="form" role="form" id="${task.getClass().getSimpleName()}">
                    <legend><a href="">
                        <i class="glyphicon glyphicon-globe"></i>
                    </a> ${task.name}
                    </legend>
                    <div class="row">
                        <div class="col-xs-3 col-md-3">
                            <label for="" >Init delay:</label>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            <input class="form-control" name="initDelay" value="${task.getInitDelay()}" placeholder="init delay" required="" autofocus="" type="number">

                        </div>
                        <div class="col-xs-3 col-md-3" >
                            <label for="" class="unit-label"><c:out value='${task.getUnit().toString()}' /></label></label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-3 col-md-3">
                            <label for="">Period:</label>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            <input class="form-control" name="period" value="${task.getPeriod()}" placeholder="period" required="" autofocus="" type="number">
                        </div>
                        <div class="col-xs-3 col-md-3">
                            <label for="" class="unit-label"><c:out value='${task.getUnit().toString()}' /></label>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-xs-3 col-md-3">
                            <label for="">Unit:</label>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            <select class="form-control" name="unit" onchange="changeUnit(this.value,'${task.getClass().getSimpleName()}')">
                                <option value="SECONDS" <c:out value='${task.getUnit().toString().equals("SECONDS")? "selected": ""}' /> >SECONDS</option>
                                <option value="MINUTES" <c:out value='${task.getUnit().toString().equals("MINUTES")? "selected": ""}' />>MINUTES</option>
                                <option value="HOURS" <c:out value='${task.getUnit().toString().equals("HOURS")? "selected": ""}' />>HOURS</option>
                                <option value="DAYS" <c:out value='${task.getUnit().toString().equals("DAYS")? "selected": ""}' />>DAYS</option>
                            </select>
                        </div>

                    </div>

                    <button class="btn btn-primary btn-block" type="submit"> Set and restart task</button>
                </form>
            </c:forEach>



        </div>
    </div>
</div>


<script>
    function changeUnit(value, taskName) {
        console.log(value, taskName);
        $("#"+taskName+" .unit-label").each(function(index, obj) {
            $(this).text(value);
        });

    }
</script>

</body>
</html>
