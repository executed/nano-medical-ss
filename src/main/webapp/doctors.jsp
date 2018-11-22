<%@ page import="entity.IUser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.Doctor" %>
<%@ page import="java.util.HashSet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en" xmlns:fb="http://www.w3.org/1999/xhtml" xmlns:g="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport"    content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author"      content="Sergey Pozhilov (GetTemplate.com)">

    <title>Nano Medical - Progressive Medical Center</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/gt_favicon.png">

    <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-theme.css" media="screen" >
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/assets/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>
    <![endif]-->

    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>

<body class="home">
<jsp:include page="wrapper/nav-bar.jsp"/>

<header>

</header>
<%
    HashSet<Doctor> doctors = null;
    if (request.getAttribute("doctors") == null)
        response.sendRedirect("/status/404.jsp");
    else doctors = (HashSet<Doctor>) request.getAttribute("doctors");
%>
<div class="container" style="padding-top: 110px;">
    <h3 style="font-style: italic">Doctors</h3>
    <div class="row">
        <div class="span12">
            <ul class="thumbnails">
                <%for (Doctor doctor: doctors){
                    %>
                <li class="span5 clearfix">
                    <div class="thumbnail clearfix">
                        <div class="caption" class="pull-left">
                            <a href="${pageContext.request.contextPath}/nano-medical/appointment?id=<%=doctor.getId()%>" style="background-color: black" class="btn btn-primary icon  pull-right">Appointment</a>
                            <h3>
                                <a href="#" ><%=doctor.getFullName()%></a>
                            </h3>
                            <h4><b>Speciality: </b><%= doctor.getSpeciality()%></h4>
                        </div>
                    </div>
                </li>
                    <%
                }%>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="wrapper/footer.jsp"/>

<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jQuery.headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/template.js"></script>
</body>
</html>
