<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciar Penitenciária</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/includes.js" ></script>
    </head>
    <body>
        
        <div id="menuLateral"></div><br/>
        <table style="float:right">
            <thead>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Endereço</th>
                <th>Editar</th>
                <th>< ></th>
            </thead>
            <tbody>
                <c:forEach  var="penitenciaria" items="#{penitenciarias}">
                    <tr>
                        <td>${penitenciaria.nome}</td>
                        <td>${penitenciaria.telefone}</td>
                        <td>${penitenciaria.endereco}</td>
                        <td><a href="Saga?logica=GerenciarPenitenciaria&idpenitenciaria=${penitenciaria.idpenitenciaria}&acao=editar"><button>Editar</button></a></td>
                    </tr>
                </c:forEach>    
            </tbody>
        </table>
        <a href="Saga?logica=GerenciarPenitenciaria&acao=cadastrar"><button >Novo Registro</button></a><br/><br/>
    </body>
</html>
