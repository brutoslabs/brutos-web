<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room</title>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript">
            
            function init(){
                reloadUsers();
                readMessage()
            }
            
            function reloadUsers(){
                $.ajax({
                    type: "POST",
                    url: "listUsers",
                    cache: false,
                    success: function(users){
                        form.reloadUsers(users);
                    },
                    error: function(){
                        alert("erro!");
                    }
                });
            }
            
            function readMessage(){
                $.ajax({
                    type: "POST",
                    url: "message",
                    cache: false,
                    success: function(msg){
                        readMessage();
                        
                        if(msg == null)
                            return;
                        
                        var origin = msg.origin;
                        
                        if(origin == null)
                            reloadUsers();
                        
                        text.addMessage(msg)
                        
                    }
                });
                
            };
            
            function sendMessage(){
                $.ajax({
                    type: "POST",
                    url: "send",
                    cache: false,
                    data: form.getMessage()
                });
                return false;
            };
            
            function exit(){
                $.ajax({
                    type: "POST",
                    url: "exit",
                    cache: false,
                    success: function(){
                        location.href = "../${param.roomID}";
                    },
                    error: function(){
                        location.href = "../${param.roomID}";
                    }
                });
            }
        </script>
    </head>
    <frameset onload="javascript:init()" rows=*,100>
        <frame name="text" src="messagePart"></frame>
        <frame name="form" src="sendPart"></frame>
    </frameset>
</html>
