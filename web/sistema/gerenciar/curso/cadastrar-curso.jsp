
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
            Nome do curso: <input type="text" name="nomeCurso" value="${curso.nomeCurso}"/><br/><br/>
            Tempo diário do curso: <input type="text" name="tempoDiario" value="${curso.tempoDiario}"/><br/><br/>
            Quantidade de dias de curso: <input type="text" name="quantidadeDiasCurso" value="${curso.quantidadeDiasCurso}"/><br/><br/>
            Intrutor do curso: <input type="text" name="instrutor" value="${curso.instrutor}"/><br/><br/>
            Vagas disponíveis: <input type="text" name="vagasDisponiveis" value="${curso.vagasDisponiveis}"/><br/><br/>
            Data de Inicio: <input type="date" name="dataInicio" value="${curso.dataInicioString}"/><br/><br/>
            Data de término: <input type="date" name="dataFim" value="${curso.dataFimString}"/><br/><br/>
            <input type="submit" name="form" value="Enviar"/>
            <input type="hidden" name="logica" value="CadastrarCurso"/>
            <input type="hidden" name="acao" value="editar"/>
            <c:if test="${not empty curso}">
                <input type ="hidden" name="idcurso" value="${curso.idcurso}"/>
                
            </c:if>
        </form>
    </body>
</html>
