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
                    url: "${pageContext.servletContext.contextPath}/Room/listUsers?roomService=${param.roomService}",
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
                    url: "${pageContext.servletContext.contextPath}/Room/message?roomService=${param.roomService}",
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
                    url: "${pageContext.servletContext.contextPath}/Room/send?roomService=${param.roomService}",
                    cache: false,
                    data: form.getMessage()
                });
                return false;
            };
            
            function exit(){
                $.ajax({
                    type: "POST",
                    url: "${pageContext.servletContext.contextPath}/Room/exit?roomService=${param.roomService}",
                    cache: false,
                    success: function(){
                        location.href = "${pageContext.servletContext.contextPath}/Room?roomService=${param.roomService}";
                    },
                    error: function(){
                        location.href = "${pageContext.servletContext.contextPath}/Room?roomService=${param.roomService}";
                    }
                });
            }
        </script>
    </head>
    <frameset onload="javascript:init()" rows=*,100>
        <frame name="text" src="${pageContext.servletContext.contextPath}/Room/messagePart?roomService=${param.roomService}"></frame>
        <frame name="form" src="${pageContext.servletContext.contextPath}/Room/sendPart?roomService=${param.roomService}"></frame>
    </frameset>
</html>
