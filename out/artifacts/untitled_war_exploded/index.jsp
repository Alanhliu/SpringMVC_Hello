<%--
  Created by IntelliJ IDEA.
  User: hliu
  Date: 16/9/16
  Time: 上午9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>

  <a href="/mapping/helloworld">helloworld</a>
  <br/>

  <form action="/mapping/testMethod" method="post">
    <input type="submit" value="testMethod">
  </form>
  <br/>

  <a href="/mapping/testPathVariable/1">testPathVariable</a>
  <br/>

  <%--rest--%>
  <a href="/mapping/testRestGet/1">testRestGet</a>
  <br/>
    <form action="/mapping/testRestPost/" method="post">
      <input type="submit" value="rest post"/>
    </form>
  <br/>
    <form action="/mapping/testRestPut/1" method="post">
      <input type="hidden" name="_method" value="PUT"/>
      <input type="submit" value="rest put"/>
    </form>
  <br/>
    <form action="/mapping/testRestDelete/1" method="post">
      <input type="hidden" name="_method" value="DELETE"/>
      <input type="submit" value="rest delete"/>
    </form>
  <br/>
  <a href="/mapping/testRequestParam?username=liuhao&age=25">testRequestParam</a>
  <br/>
  <form action="/mapping/testPojo" method="post">
    username:<input type="text" name="username" /><br/>
    passworld:<input type="text" name="password"><br/>
    age:<input type="text" name="age"><br/>
    email:<input type="text" name="email"><br/>
    city:<input type="text" name="address.city"><br/>
    province:<input type="text" name="address.province"><br/>
    <input type="submit" value="submit"><br/>
  </form><br/>

  <a href="/mapping/testModelAndView">testModelAndView</a><br/>

  <a href="/mapping/testMap">testMap</a><br/>

  <a href="/mapping/testSessionAttributes">testSessionAttributes</a>

  <%--模拟修改操作--%>
  <%--原始数据 tom 123456 liuhao257@163.com 25--%>
  <form action="/mapping/testModelAttributes" method="post">
    <input type="hidden" name="id" value="1">
    username:<input type="text" name="username" value="tom"><br/>
    email:<input type="text" name="email" value="liuhao257@163.com"><br/>
    age:<input type="text" name="age" value="25"><br/>
    <input type="submit" value="submit"/>
  </form>
  </body>
</html>
