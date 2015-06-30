<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter chat</title>
    </head>
    <body>
        <h1>Enter Chat</h1>
        <form method="POST" action="${param.roomID}/enter">
            <label for="nick">Nick</label><input type="text" size="20" name="user.nick" id="nick"><br>
            <input type="submit" value="enter">
        </form>
    </body>
</html>
