<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 17.08.2017
  Time: 4:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        #greetingText {
            color: black;
            width: 960px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 50px;
            float: none;
            font-size: 18;
        }
    </style>
</head>
<body>
<%@include file="header.jsp" %>
    <div class="content" id="greetingText">
        <p>
            <strong style="color: #2d557f">Developer Management System</strong> is a powerful platform designed to help plan, organize, and manage team developing resources. It makes communication and collaboration on projects really simple.
        </p>
        <strong style="color: #2d557f">Features:</strong>
        <ul>
            <li>Web-based platform</li>
            <li>User roles (administrator, project manager, developer)</li>
            <li>Multitasking</li>
            <li>Project planning and commentaries</li>
            <li>Security (powered by Spring Security 4)</li>
        </ul>
        <p>Developed by Trubkin Nikita under the mentorship of Artem Gudkov at NetCracker summer courses.</p>
    </div>
</body>
</html>
