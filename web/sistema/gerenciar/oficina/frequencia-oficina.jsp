<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Frequência na oficina</title>
    </head>
    <body>
        <form action="Saga" method="POST" style="float: right" id="freq">
            <table style="float:right">
                <thead>
                <th>Matrícula</th>
                <th>Nome</th>
                <th>Presença</th>
                <th>Justificativa</th>
                </thead>
                <tbody>
                    <c:forEach  var="cadastro" items="#{cadastros}">
                        <tr>
                            <td>${cadastro.apenado.matricula}</td>
                            <td>${cadastro.apenado.dadosPessoais.nome}</td>
                            <td><input type="checkbox" name="presenca${cadastro.apenado.idapenado}" checked="true" form="freq"/></td>
                            <td><input type="input" placeholder="Justificativa" name="justificativa${cadastro.apenado.idapenado}" form="freq"/></td>
                        </tr>
                    </c:forEach>    
                </tbody>
            </table>
            <br/>
            <input type="submit" value="Enviar"/>
            <input type="hidden" name="logica" value="FrequenciaOficina"/>
            <input type="hidden" name="idoficina" value="${idoficina}"/>
        </form>
    </body>
</html>
