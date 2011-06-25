<%-- 
  Brutos Web MVC http://brutos.sourceforge.net/
  Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
    </head>
    <body>
        <h1>${text}</h1>
        <hr><br>
        <h2>${invoked}</h2><%-- incluido pelo interceptador --%>
        <a href="index.jbrs?invoke=method1">IndexController.method1()</a><br>
        <a href="index.jbrs?invoke=method2">IndexController.method2()</a><br>
        <a href="index.jbrs?invoke=method3">IndexController.method3()</a><br>
        <a href="index.jbrs?invoke=method4">IndexController.method4()</a><br>
    </body>
</html>
