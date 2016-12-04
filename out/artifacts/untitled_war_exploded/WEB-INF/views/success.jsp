<%--
  Created by IntelliJ IDEA.
  User: hliu
  Date: 16/9/16
  Time: 下午12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>success page</h1>
    time: ${requestScope.time }<br/>
    names: ${requestScope.names }<br/>

    user in request:${requestScope.user}<br/>
    user in session:${sessionScope.user}<br/>

    school in request:${requestScope.school}<br/>
    school in session:${sessionScope.school}<br/>
</body>
</html>
