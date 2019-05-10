
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Curso</title>
    </head>
    <body>
        <form action="Saga" method="Post">
            Nome da oficina: <input type="text" name="nomeOficina" value="${oficina.nomeOficina}"/><br/><br/>
            Tipo da oficina: <input type="text" name="tipoOficina" value="${oficina.tipoOficina}"/><br/><br/>
            Monitor da oficina: <input type="text" name="monitor" value="${oficina.monitor}"/><br/><br/>
            Tempo de oficina: <input type="text" name="tempo" value="${oficina.tempo}"/><br/><br/>
            <input type="submit" name="form" value="Enviar"/>
            <input type="hidden" name="logica" value="CadastrarOficina"/>
            <input type="hidden" name="acao" value="editar"/>
            <c:if test="${not empty oficina}">
                <input type ="hidden" name="idoficina" value="${oficina.idoficina}"/>
            </c:if>
        </form>
    </body>
</html>
