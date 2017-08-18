<%--
  Created by IntelliJ IDEA.
  User: TrubkinN
  Date: 17.08.2017
  Time: 4:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}"/>
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}"/>
<html>
<head>
    <style>
        #header {
            width: 100%;
            background: #E0F7FA;
        }

        #title {
            display: inline-block;
        }

        #nav {
            display: inline-block;

        }

        #login {
            display: inline-block;
            margin-left: 100;
        }

        #nav_bar li {
            display: inline;
            margin-left: 5;
            margin-right: 5;
        }

        #nav_bar a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<header id="header">
    <div id="title">Developer Management Service</div>
    <div id="nav">
        <ul id="nav_bar">
            <li><a href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/admin">Admin</a>
            </li>
            <li><a href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/manager">Manager</a>
            </li>
            <li>
                <a href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/developer">Developer</a>
            </li>
        </ul>
    </div>
    <div id="login">
        <a href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/login">Login</a>
    </div>
</header>
</body>
</html>