<%@ page import="entity.Doctor" %>
<%@ page import="entity.TimeSlot" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="utility.SessionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/appointment.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/assets/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/respond.min.js"></script>

    <style>
        .bigicon {
            font-size: 35px;
            color: #36A0FF;
        }
    </style>
    <![endif]-->
</head>

<%
    TreeSet<TimeSlot> freeSlots = (TreeSet<TimeSlot>) request.getAttribute("freeSlots");
    Doctor doctor = (Doctor) request.getAttribute("doctor");
    String startOfWork = null, endOfWork = null;
    if (doctor != null) {
        startOfWork = doctor.getStartOfWork().getHourOfDay() + ":" +
                (((doctor.getStartOfWork().getMinuteOfHour() + "").length() < 2) ? doctor.getStartOfWork().getMinuteOfHour() + "0" : doctor.getStartOfWork().getMinuteOfHour());
        endOfWork = doctor.getEndOfWork().getHourOfDay() + ":" +
                (((doctor.getEndOfWork().getMinuteOfHour() + "").length() < 2) ? doctor.getEndOfWork().getMinuteOfHour() + "0" : doctor.getEndOfWork().getMinuteOfHour());
    }%>

<% if (freeSlots == null) {
    %> <body class="home" style="background-color: white; background-image: url('${pageContext.request.contextPath}/assets/images/bg_header.jpg')"> <%
} else{
    %> <body onload="switchPanes()" class="home" style="background-color: white; background-image: url('${pageContext.request.contextPath}/assets/images/bg_header.jpg')"> <%
}%>

<jsp:include page="wrapper/nav-bar.jsp"/>

<div class="container" style="padding-top: 120px;">
    <div class="row">
        <div class="col-md-12">
            <div id="main_pane" class="well well-sm" style=" display: block; background-color: #e0e3e5; height: 640px; border-color: white; border-width: 6px; width: 800px; margin: 0px auto">
                        <%--fcwfcewr--%>
                    <hr>

                        <% if (doctor != null) {
                            %> <input id = "doctor_id" type="text" name="id" value="<%=doctor.getId()%>" hidden> <%
                        }%>
                    <table class="table-fill" style="width: 700px; border-color: white;">
                        <thead>
                        <tr>
                            <th style="font-weight: bold; height: 15px; font-size: 20px"> Book an appointment</th>
                            <th class="text-left"> </th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                        <tr>
                            <td class="text-left" style="font-weight: bold">Full name</td>
                            <% if (doctor != null) {
                            %> <td class="text-left" style="font-style: italic"><p><%= doctor.getFullName() %></p></td> <%
                            }%>
                        </tr>
                        <tr>
                            <td class="text-left" style="font-weight: bold">Work time</td>
                            <td class="text-left" style="font-style: italic"><p><%= startOfWork + " - " + endOfWork %></p></td>
                        </tr>
                        <tr>
                            <td class="text-left" style="font-weight: bold">Date of appointment</td>
                            <td class="text-left">
                                <input id = "date" type="date" name="date" required>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="font-weight: bold">Time of day</td>
                            <td class="text-left">
                                    <% if (doctor != null) {
                                        %> <input id = "time" type="time" name="time" min="<%=startOfWork.split(":")[0]%>" max="<%=endOfWork.split(":")[0]%>" required></td> <%
                                    }%>

                            </td>
                        </tr>
                        <tr>
                            <td class="text-left" style="font-weight: bold">Needed duration</td>
                            <td class="text-left">
                                <% if (doctor != null && doctor.isMaxDurationChangeable()){
                                    %><input id = "duration" type="number" name="duration" min="1" required><%
                                }else if(doctor != null){
                                    %><input id = "duration" type="number" name="duration" min="1" max="<%=doctor.getMaxDurationOfAppointment()%>" required><%
                                }%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                        <hr>
                        <div class="form-group">
                            <div class="col-md-12 text-center">
                                <button type="submit" class="btn btn-primary btn-lg" onclick="checkTimeSlot()">Check </button>
                            </div>
                        </div>
                        <hr>
                    <hr>
            </div>
            <%--iojfejfeoij;re;jgeotigjt;oigjt;ojg--%>
            <div id="free_slot_pane" class="well well-sm" style=" display: none; background-color: #e0e3e5; height: 640px; border-color: white; border-width: 6px; width: 800px; margin: 0px auto">
                <%--fcwfcewr--%>
                <hr>
                    <table class="table-fill" style="width: 700px; border-color: white;">
                        <thead>
                        <tr>
                            <th style="font-weight: bold; height: 15px; font-size: 20px"> Available Slots</th>
                        </tr>
                        </thead>
                        <tbody class="table-hover">
                            <% if (freeSlots != null){
                                for (TimeSlot freeSlot: (TreeSet<TimeSlot>) freeSlots){
                                String startTime = freeSlot.getStartTime().getHourOfDay()+":"+(((String.valueOf(freeSlot.getStartTime().getMinuteOfHour())).length() < 2) ? ("0" + freeSlot.getStartTime().getMinuteOfHour()) : freeSlot.getStartTime().getMinuteOfHour());
                                String endTime = freeSlot.getEndTime().getHourOfDay()+":"+(((String.valueOf(freeSlot.getEndTime().getMinuteOfHour())).length() < 2) ? ("0" + freeSlot.getEndTime().getMinuteOfHour()) : freeSlot.getEndTime().getMinuteOfHour());
                                String year = String.valueOf(freeSlot.getEndTime().getYear());
                                String month = String.valueOf(freeSlot.getEndTime().getMonthOfYear());
                                String day = String.valueOf(freeSlot.getEndTime().getDayOfMonth());
                                String dateString = year+"-"+month+"-"+day;
                            %>
                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/nano-medical/appointment?freeSlot=<%=dateString+" "+startTime+"//"+dateString+" "+endTime%>&doctor_id=<%=doctor.getId()%>"><%=startTime + " - " + endTime%></a></td>
                                    </tr>
                                <%
                            }}%>
                        </tbody>
                    </table>
                <hr>
            </div>
            <%--enddddddddddddddddddddddd--%>
        </div>
    </div>
</div>

<jsp:include page="wrapper/footer.jsp"/>

<script type="text/javascript">
    function checkTimeSlot() {
        const Url = '${pageContext.request.contextPath}/nano-medical/appointment';
        var params = "doctor_id="+$("#doctor_id")[0].value+"&date="+$("#date")[0].value+"&time="+$("#time")[0].value+"&duration="+$("#duration")[0].value;
        var xhr = new XMLHttpRequest();
        xhr.open("POST", Url, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(params);
        xhr.onreadystatechange=function () {
            if (this.readyState==4){
                if (this.status == 235){
                    alert("Success");
                }
                if(this.status == 532) {
                    alert("This time is closed, take other");
                    window.location.replace('${pageContext.request.contextPath}/nano-medical/appointment?status=free_slots&doctor_id='+$("#doctor_id")[0].value+'&time='+$("#date")[0].value+" "+$("#time")[0].value+"&dif="+$("#duration")[0].value);
                }
                if (this.status == 600){
                    alert("Bad time");
                }
            }
        }
    }
</script>

<script type="text/javascript">
    function switchPanes() {
        document.getElementById("main_pane").style.display = 'none';
        document.getElementById("free_slot_pane").style.display = 'block';
    }
</script>

<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jQuery.headroom.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/template.js"></script>

</body>
</html>