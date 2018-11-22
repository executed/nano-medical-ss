<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport"    content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author"      content="Sergey Pozhilov (GetTemplate.com)">

    <title>Sign up - Progressus Bootstrap template</title>

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
</head>

<body>
<!-- Fixed navbar -->
<jsp:include page="wrapper/nav-bar.jsp"/>
<!-- /.navbar -->

<header id="head" class="secondary"></header>

<!-- container -->
<div class="container">

    <ol class="breadcrumb">
        <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
        <li class="active">Registration</li>
    </ol>

    <div class="row">

        <!-- Article main content -->
        <article class="col-xs-12 maincontent">
            <header class="page-header">
                <h1 class="page-title">Registration</h1>
            </header>

            <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h3 class="thin text-center">Register a new account</h3>
                        <p class="text-center text-muted">Lorem ipsum dolor sit amet, <a href="${pageContext.request.contextPath}/signin.jsp">Login</a> adipisicing elit. Quo nulla quibusdam cum doloremque incidunt nemo sunt a tenetur omnis odio. </p>
                        <hr>
                        <%  HashMap<String, String> errors = new HashMap<>();
                            if (request.getAttribute("errors") != null)
                                errors = (HashMap<String, String>) request.getAttribute("errors"); %>

                        <form action="${pageContext.request.contextPath}/nano-medical/signup.jsp" method="post">
                            <div class="top-margin">
                                <label>First Name</label>
                                <input id = "firstName_id" type="text" name="first_name" class="form-control" pattern="^[a-zA-Zа-яА-Я'][a-zA-Zа-яА-Я-' ]+[a-zA-Zа-яА-Я']?$" required>
                            </div>
                            <div class="top-margin">
                                <label>Last Name</label>
                                <input id = "lastName_id" type="text" name="last_name" class="form-control"  required>
                            </div>
                            <div class="top-margin">
                                <label>Email Address <span class="text-danger">*</span></label>
                                <input id = "email_id" type="email" name="email" class="form-control" required>
                            </div>
                            <% if (errors.get("email") != null) { %>
                            <p style="color:#a20e00"><%=errors.get("email")%></p>
                            <% } %>
                            <div class="top-margin">
                                <label>Username <span class="text-danger">*</span></label>
                                <input id = "username_id" type="text" name="username" class="form-control" pattern="^[a-z0-9._]{5,32}$" required>
                            </div>
                            <% if (errors.get("username") != null) { %>
                            <p style="color:#a20e00"><%=errors.get("username")%></p>
                            <% } %>

                            <div class="row top-margin">
                                <div class="col-sm-6">
                                    <label>Password <span class="text-danger">*</span></label>
                                    <input id="password" type="password" name="password" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}" class="form-control" required>
                                </div>
                                <% if (errors.get("password") != null) { %>
                                <p style="color:#a20e00"><%=errors.get("password")%></p>
                                <% } %>
                                <div class="col-sm-6">
                                    <label>Confirm Password <span class="text-danger">*</span></label>
                                    <input id="password2" onkeyup="validatePassword()" type="password" class="form-control" required>
                                </div>
                            </div>
                            <!-- error message in case of bad parameters -->
                            <%  if (errors.size() != 0){
                                if (errors.get("unknown") != null) { %>
                            <p style="color:#a20e00"><%=errors.get("unknown")%></p>
                            <% }} %>

                            <hr>

                            <div class="row">
                                <div class="col-lg-4 text-right">
                                    <button class="btn btn-action" type="submit">Register</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>

        </article>
        <!-- /Article -->

    </div>
</div>	<!-- /container -->


<jsp:include page="wrapper/footer.jsp"/>




<script>
    function validatePassword(){
        var password = document.getElementById("password")
            , confirm_password = document.getElementById("password2");
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else{
            confirm_password.setCustomValidity('');
        }
    }
</script>
<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jQuery.headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/template.js"></script>
</body>
</html>