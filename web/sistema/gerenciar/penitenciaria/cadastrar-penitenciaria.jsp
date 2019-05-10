
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastrar Penitenciária</title>
    </head>
    <body>
        <form action="Saga" method="Post">
            Nome da penitenciária: <input type="text" name="nome" value="${penitenciaria.nome}"/><br/><br/>
            Endereço:  <input type="text" name="endereco" value="${penitenciaria.endereco}"/><br/><br/>
            Telefone: <input type="text" name="telefone" value="${penitenciaria.telefone}"/><br/><br/>
            <input type="submit" name="form" value="Enviar"/>
            <input type="hidden" name="logica" value="CadastrarPenitenciaria"/>
            <c:if test="${not empty penitenciaria}">
                <input type ="hidden" name="idpenitenciaria" value="${penitenciaria.idpenitenciaria}"/>
            </c:if>
        </form>
    </body>
</html>
