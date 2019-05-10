<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciar Apenado</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/includes.js" ></script>
    </head>
    <body>
        
        <div id="menuLateral"></div><br/>
        <table style="float:right">
            <thead>
                <th>Matrícula</th>
                <th>Nome</th>
                <th>Curso</th>
                <th>Oficina</th>
                <th>Unidade prisional</th>
                <th>< ></th>
            </thead>
            <tbody>
                <c:forEach  var="apenado" items="#{apenados}">
                    <tr>
                        <td>${apenado.matricula}</td>
                        <td>${apenado.dadosPessoais.nome}</td>
                        <td>${apenado.curso}</td>
                        <td>${apenado.oficina}</td>
                        <td>${apenado.dadosPenitenciarios.penitenciaria.nome}</td>
                        <td><a href="Saga?logica=CadastrarApenado&idapenado=${apenado.idapenado}"><button>Detalhes</button></a></td>
                    </tr>
                </c:forEach>    
            </tbody>
        </table>
        <a href="Saga?logica=GerenciarApenado&acao=cadastrar"><button >Novo Registro</button></a><br/><br/>
        <a href="Saga?logica=GerenciarApenado&acao=desligar"><button >Desligar Apenado</button></a>

    </body>
</html>
