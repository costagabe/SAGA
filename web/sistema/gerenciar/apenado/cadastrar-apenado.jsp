<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de apenado</title>
    </head>
    <body>
        <form action="Saga" method="POST">
            Matrícula:
            <input type="text" name="matricula" value="${apenado.matricula}"/><br/><br/>
            Nome:
            <input type="text" name="nome" value="${apenado.dadosPessoais.nome}"/><br/><br/>
            Data de nascimento:
            <input type="date" name="dataNascimento" value="${apenado.dataNascimento}"/><br/><br/>
            RG:
            <input type="text" name="rg" value="${apenado.dadosPessoais.rg}"/><br/><br/>
            CPF:
            <input type="text" name="cpf" value="${apenado.dadosPessoais.cpf}"/><br/><br/>
            Nome do pai:
            <input type="text" name="nomePai" value="${apenado.dadosPessoais.nomePai}"/><br/><br/>
            Nome da Mãe:
            <input type="text" name="nomeMae" value="${apenado.dadosPessoais.nomeMae}"/><br/><br/>
            Nacionalidade:
            <input type="text" name="nacionalidade" value="${apenado.dadosPessoais.nacionalidade}"/><br/><br/>
            Naturalidade:
            <input type="text" name="naturalidade" value="${apenado.dadosPessoais.naturalidade}"/><br/><br/>
            Escolaridade:
            <input type="text" name="escolaridade" value="${apenado.dadosPessoais.escolaridade}"/><br/><br/>
            Estado Civil:
            <input type="text" name="estadoCivil" value="${apenado.dadosPessoais.estadoCivil}"/><br/><br/>
            Penitenciária:
            <select name="penitenciaria">
                <c:if test="${not empty apenado.dadosPenitenciarios}">
                    <option value="${apenado.dadosPenitenciarios.penitenciaria.idpenitenciaria}">${apenado.dadosPenitenciarios.penitenciaria.nome}</option>
                </c:if>
                <c:forEach var="penitenciaria" items="#{penitenciarias}"  >
                    <option value="${penitenciaria.idpenitenciaria}">${penitenciaria.nome}</option>
                </c:forEach>

            </select><br/><br/>
            Artigo:
            <input type="text" name="artigo" value="${apenado.dadosPenitenciarios.artigo}"/><br/><br/>
            Tempo de prisão:
            <input type="text" name="tempoPrisao" value="${apenado.dadosPenitenciarios.tempoCadeia}"/><br/><br/>
            <c:if test="${not empty apenado}">
                <input type ="hidden" name="idapenado" value="${apenado.idapenado}"/>
                <input type ="hidden" name="iddadospessoais" value="${apenado.dadosPessoais.iddadosPessoais}"/>
                <input type ="hidden" name="iddadospenitenciarios" value="${apenado.dadosPenitenciarios.iddadosPenitenciarios}"/>

            </c:if>
            <input type="hidden" name="logica" value="CadastrarApenado"/>
            <input type="submit" name="form" value="Enviar"/>

        </form>
    </body>
</html>
