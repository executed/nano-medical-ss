<%@ page import="entity.IUser" %>
<%@ page import="entity.Client" %>
<%@ page import="entity.TimeSlot" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html lang="en" xmlns:fb="http://www.w3.org/1999/xhtml" xmlns:g="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta name="viewport"    content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author"      content="Sergey Pozhilov (GetTemplate.com)">

    <title>Nano Medical - Progressive Medical Center</title>

    <link rel="shortcut icon" href="../assets/images/gt_favicon.png">

    <link rel="stylesheet" media="screen" href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
    <link rel="stylesheet" href="../assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="../assets/css/font-awesome.min.css">

    <!-- Custom styles for our template -->
    <link rel="stylesheet" href="../assets/css/bootstrap-theme.css" media="screen" >
    <link rel="stylesheet" href="../assets/css/main.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="../assets/js/html5shiv.js"></script>
    <script src="../assets/js/respond.min.js"></script>
    <![endif]-->

    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<!-- Fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top headroom" >
    <div class="container">
        <div class="navbar-header">
            <!-- Button for smallest screens -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
            <a class="navbar-brand" href="../index.jsp"><img src="../assets/images/logo.png" alt="Progressus HTML5 template"></a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav pull-right">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="../about.jsp">About</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">More Pages <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="../sidebar-left.jsp">Left Sidebar</a></li>
                        <li class="active"><a href="../sidebar-right.jsp">Right Sidebar</a></li>
                    </ul>
                </li>
                <li><a href="../contact.jsp">Contact</a></li>
                <% if (((IUser) session.getAttribute("user")) != null) {
                %> <li><a class="btn" href="${pageContext.request.contextPath}/nano-medical/signout">Sign Out</a></li> <%
            } else {
            %> <jsp:forward page="status/404.jsp"/> <%
                }%>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>
<!-- /.navbar -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="text-left" style="padding-top:100px;">Your Profile</h3>

    </div>
    <div class="panel-body">

        <% Client client = (Client) session.getAttribute("user"); %>
        <table class="table table-bordered">
            <tbody>

            <tr>
                <td>First Name</td>
                <td><%= client.getFirstName() %></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><%= client.getLastName() %></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><%= client.getConfiguration().getEmail() %></td>
            </tr>
            <tr>
                <td>Username</td>
                <td><%= client.getConfiguration().getUsername() %> </td>
            </tr>
            <tr>
                <td>My Appointments</td>
                <td>
                    <table>
                        <tr>
                            <th>Doctor</th>
                            <th>Speciality</th>
                            <th>Time</th>
                        </tr>
                        <%--<%for(TimeSlot slot: client.getTimeSlots()){%>
                        <tr>
                            <td> <%= "DoctorName" %> </td>
                            <td> <%= "Doctor speciality" %> </td>
                            <td> <%= slot.getStartTime().getHourOfDay() + ":" + slot.getEndTime() %> </td>
                        </tr>
                        <%}%>--%>
                    </table>
                </td>
            </tr>

            </tbody>
        </table>
    </div>
</div>
</body>
<footer id="footer" class="top-space">

    <div class="footer1">
        <div class="container">
            <div class="row">

                <div class="col-md-3 widget">
                    <h3 class="widget-title">Contact</h3>
                    <div class="widget-body">
                        <p>+380663384033<br>
                            <a href="mailto:#">dev.serbyn@gmail.com</a><br>
                            <br>
                            49 Golovna St., Chernivtsi City, UA 58000
                        </p>
                    </div>
                </div>

                <div class="col-md-3 widget">
                    <h3 class="widget-title">Follow me</h3>
                    <div class="widget-body">
                        <p class="follow-me-icons">
                            <a href=""><i class="fa fa-twitter fa-2"></i></a>
                            <a href=""><i class="fa fa-dribbble fa-2"></i></a>
                            <a href=""><i class="fa fa-github fa-2"></i></a>
                            <a href=""><i class="fa fa-facebook fa-2"></i></a>
                        </p>
                    </div>
                </div>

                <div class="col-md-6 widget">
                    <h3 class="widget-title">Text widget</h3>
                    <div class="widget-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Excepturi, dolores, quibusdam architecto voluptatem amet fugiat nesciunt placeat provident cumque accusamus itaque voluptate modi quidem dolore optio velit hic iusto vero praesentium repellat commodi ad id expedita cupiditate repellendus possimus unde?</p>
                        <p>Eius consequatur nihil quibusdam! Laborum, rerum, quis, inventore ipsa autem repellat provident assumenda labore soluta minima alias temporibus facere distinctio quas adipisci nam sunt explicabo officia tenetur at ea quos doloribus dolorum voluptate reprehenderit architecto sint libero illo et hic.</p>
                    </div>
                </div>

            </div> <!-- /row of widgets -->
        </div>
    </div>

    <div class="footer2">
        <div class="container">
            <div class="row">

                <div class="col-md-6 widget">
                    <div class="widget-body">
                        <p class="simplenav">
                            <a href="#">Home</a> |
                            <a href="../about.jsp">About</a> |
                            <a href="../sidebar-right.html">Sidebar</a> |
                            <a href="../contact.jsp">Contact</a> |
                            <b><a href="../signup.jsp">Sign up</a></b>
                        </p>
                    </div>
                </div>

                <div class="col-md-6 widget">
                    <div class="widget-body">
                        <p class="text-right">
                            Copyright &copy; 2014, Your name. Designed by <a href="http://gettemplate.com/" rel="designer">gettemplate</a>
                        </p>
                    </div>
                </div>

            </div> <!-- /row of widgets -->
        </div>
    </div>

</footer>



<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="../assets/js/headroom.min.js"></script>
<script src="../assets/js/jQuery.headroom.min.js"></script>
<script src="../assets/js/template.js"></script>


</html>