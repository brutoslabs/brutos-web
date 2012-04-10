<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Room</title>
        <script type="text/javascript" src="../js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript">
            
            function init(){
                reloadUsers();
                readMessage()
            }
            
            function reloadUsers(){
                $.ajax({
                    type: "POST",
                    url: "listUsers.jbrs",
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
                    url: "message.jbrs",
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
                    url: "send.jbrs",
                    cache: false,
                    data: form.getMessage()
                });
                return false;
            };
            
            function exit(){
                $.ajax({
                    type: "POST",
                    url: "exit.jbrs",
                    cache: false,
                    success: function(){
                        location.href = "login.jbrs";
                    },
                    error: function(){
                        location.href = "login.jbrs";
                    }
                });
            }
        </script>
    </head>
    <frameset onload="javascript:init()" rows=*,100>
        <frame name="text" src="messagePart.jbrs"></frame>
        <frame name="form" src="sendPart.jbrs"></frame>
    </frameset>
</html>
