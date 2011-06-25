<%-- 
    Document   : index
    Created on : 07/12/2009, 13:44:51
    Author     : Neto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <style>
            .fileName { font-size: 19px; color: #009900}
        </style>
    </head>
    <body>
        <h1>${text}</h1>
        <f:view>
            <form method="POST" enctype="multipart/form-data" action="index.jbrs">
                <table width="100%" cellpadding="0" cellspacing="0" >
                    <tr>
                        <td width="100%">File:</td>
                    </tr>
                    <tr>
                        <td width="100%"><input type="file" name="arq" size="20"></td>
                    </tr>
                    <tr>
                        <td width="100%"><input type="submit" name="action" value="upload"></td>
                    </tr>
                    <tr>
                        <td width="100%"><font color="#FF0000"><h:outputText value="#{mensagem}"/></font></td>
                    </tr>
                    <tr>
                        <td width="100%">
                            <h:dataTable border="1"
                              cellpadding="4" cellspacing="0"
                               value="#{Controller.files}" var="name">
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Arquivo"/>
                                    </f:facet>
                                    <h:outputText styleClass="fileName" value="#{name}"/>
                               </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Ação"/>
                                    </f:facet>
                                    <h:outputLink value="index.jbrs?action=delete&name=#{name}">
                                        <f:verbatim>Delete</f:verbatim>
                                    </h:outputLink>
                                    |
                                    <h:outputLink value="index.jbrs?action=download&name=#{name}">
                                        <f:verbatim>Download</f:verbatim>
                                    </h:outputLink>
                               </h:column>
                            </h:dataTable>
                        </td>
                    </tr>
                </table>
            </form>
        </f:view>
    </body>
</html>
