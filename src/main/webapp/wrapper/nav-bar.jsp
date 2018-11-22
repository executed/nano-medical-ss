<%@ page import="entity.IUser" %>

<div class="navbar navbar-inverse navbar-fixed-top headroom" >
    <div class="container">
        <div class="navbar-header">

            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Nano Medical</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav pull-right">
                <li class="active"><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/nano-medical/about.jsp">About</a></li>
                <li><a href="${pageContext.request.contextPath}/nano-medical/doctors">Doctors</a></li>
                <li><a href="${pageContext.request.contextPath}/nano-medical/contact.jsp">Contact</a></li>
                <% if (((IUser) session.getAttribute("user")) != null) {
                %> <li><a class="btn" href="${pageContext.request.contextPath}/nano-medical/signout">Sign Out</a></li> <%
            } else {
            %> <li><a class="btn" href="${pageContext.request.contextPath}/signin.jsp">SIGN IN / SIGN UP</a></li> <%
                }%>
            </ul>
        </div>
    </div>
</div>
