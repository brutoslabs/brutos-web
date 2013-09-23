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
        <form action="index.jbrs" method="POST">
            <input type="hidden" name="invoke" value="show">
            <table cellpadding="2" cellspacing="2" width="300">
                <tr>
                    <td width="50%"><b>Id</b></td>
                    <td width="50%"><b>First Name</b></td>
                </tr>
                <tr>
                    <td width="50%"><input type="text" name="id" value="0" style="width: 90%"></td>
                    <td width="50%"><input type="text" name="first-name" style="width: 90%"></td>
                </tr>
                <tr>
                    <td width="50%"><b>Last Name</b></td>
                    <td width="50%"><b>Age</b></td>
                </tr>
                <tr>
                    <td width="50%"><input type="text" name="last-name" style="width: 90%"></td>
                    <td width="50%"><input type="text" name="age" value="0" style="width: 90%"></td>
                </tr>
                <tr>
                    <td width="100%" colspan="2" align="center">
                        <input type="submit" value="show" name="show">
                        <input type="reset" value="clear" name="clear">
                    </td>
                </tr>
            </table>
            <hr>
            <h1>Show</h1>
            <table cellpadding="2" cellspacing="2" width="300">
                <tr>
                    <td width="50%"><b>Id</b></td>
                    <td width="50%"><b>First Name</b></td>
                </tr>
                <tr>
                    <td width="50%" style="color: #FF0000">${Controller.bean.id}</td>
                    <td width="50%" style="color: #FF0000">${Controller.bean.firstName}</td>
                </tr>
                <tr>
                    <td width="50%"><b>Last Name</b></td>
                    <td width="50%"><b>Age</b></td>
                </tr>
                <tr>
                    <td width="50%" style="color: #FF0000">${Controller.bean.lastName}</td>
                    <td width="50%" style="color: #FF0000">${Controller.bean.age}</td>
                </tr>
            </table>
        </form>
    </body>
</html>
