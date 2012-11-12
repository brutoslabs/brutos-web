<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript">
            
            function reloadUsers(users){
                var selected = $("#dest").val();
                $("#dest").children().remove();
                
                for(var i=0;i<users.length;i++){
                    $("#dest").append( new Option(users[i].nick, users[i].nick, true, true) );
                }
                $("#dest").val(selected);
            };
            
            function selectMessage(){
                $("#msg").focus();
            }
            
             function getMessage(){
                 var destino = $("#dest").val();
                 var message = $("#msg").val();
                 $("#msg").val('')
                 $("#msg").focus();
                 
                 return {
                     "dest.nick"  : destino,
                     "message": message
                 };
                 /*return {
                     "dest"  : {
                         "nick" : destino
                     },
                     "message": message
                 };*/
             }
        </script>
    </head>
    <body>
        <input type="text" name="msg" id="msg" size="100"> 
        <select name="dest" id="dest" onchange="javascript:selectMessage()"></select>
        <input type="button" name="send" value="  send  " onclick="javascript:parent.sendMessage()">
        <input type="button" name="exit" value="  exit  " onclick="javascript:parent.exit()">
    </body>
</html>
