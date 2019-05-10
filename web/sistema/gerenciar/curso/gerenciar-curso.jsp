<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciar Cruso</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/includes.js" ></script>
    </head>
    <body>
        
        <div id="menuLateral"></div><br/>
        <table style="float:right;" border="1">
            <thead>
                <th>Nome do Curso</th>
                <th>Instrutor</th>
                <th>Vagas Disponíveis</th>
                <th>Detalhes</th>
                <th>Frequência</th>
                <th>Adicionar</th>
                <th>< ></th>
            </thead>
            <tbody>
                <c:forEach  var="curso" items="#{cursos}">
                    <tr>
                        <td>${curso.nomeCurso}</td>
                        <td>${curso.instrutor}</td>
                        <td>${curso.vagasDisponiveis}</td>
                        <td><a href="Saga?logica=CadastrarCurso&idcurso=${curso.idcurso}&acao=carregar"><button>Detalhes</button></a></td>
                        <td><a href="Saga?logica=GerenciarCurso&acao=frequencia&idcurso=${curso.idcurso}"><button>Frequência</button></a></td>
                        <td><a href="Saga?logica=GerenciarCurso&acao=adicionarApenado&curso=${curso.idcurso}&nomeCurso=${curso.nomeCurso}"><button>Adicinoar Apenado</button></a></td>
                    </tr>
                </c:forEach>    
            </tbody>
        </table>
        <a href="Saga?logica=GerenciarCurso&acao=cadastrar"><button >Novo Registro</button></a>

    </body>
</html>
