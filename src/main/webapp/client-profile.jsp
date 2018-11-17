<%@ page import="entity.Client" %><%--
  Created by IntelliJ IDEA.
  User: danse
  Date: 17.11.2018
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CLIENT PROFILE</title>
</head>
<body>
        CLIENT PROFILE DATA
        <% Client client = (Client) session.getAttribute("user"); %>
        <label style="color: darkcyan"><%=client.getFirstName() + " " + client.getLastName()%></label>
</body>
</html>
