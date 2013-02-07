<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.4.4.min.js"></script>
        <script type="text/javascript">
            function addMessage(msg){
                var origin = msg.origin;
                var dest = msg.dest;
                var message = msg.message;
                
                var msgCreated = 
                    (origin == null)?
                        message :
                        "<b>" + origin.nick + "</b> fala para <b>" + dest.nick + "</b>: " + message;
                    
                $("#tableMessage")
                    .find('tbody:last')
                        .append($('<tr'+ (msg.forMe? ' style="background-color:#FAFAFA;"' : '') + '>')
                            .append($('<td>'))
                                .html(msgCreated));
                
                
                window.scrollTo( 0, document.body.scrollHeight);
            }
            
        </script>
        <title></title>
    </head>
    <body>
        <table id="tableMessage" width="100%" cellspacing="6" cellpadding="3">
            <tbody>
            </tbody>
        </table>
    </body>
</html>
