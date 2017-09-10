<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 07.09.2017
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>DMS: Developer: All projects</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <spring:url value="/resources/js/developer.js" var="developerJS"/>
    <script src="${developerJS}"></script>
    <spring:url value="/resources/css/table.css" var="tableCSS"/>
    <link href="${tableCSS}" rel="stylesheet" type="text/css">
</head>
<body onload="formAllProjectsTable();">
<%@include file="../header.jsp" %>
<%@include file="menu.jsp" %>

<div class="content">
    <table id="projects" class="table">
        <tr>
            <th>ID</th>
            <th>Nickname</th>
            <th>Start</th>
            <th>Estimated end</th>
            <th>End</th>
            <th>Complete</th>
        </tr>
    </table>
</div>

</body>
</html>
