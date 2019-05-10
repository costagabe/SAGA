<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciar Oficina</title>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/includes.js" ></script>
    </head>
    <body>
        
        <div id="menuLateral"></div><br/>
        <table style="float:right;" border="1">
            <thead>
                <th>Nome da Oficina</th>
                <th>Tipo</th>
                <th>Monitor</th>
                <th>Detalhes</th>
                <th>Frequência</th>
                <th>Adicionar</th>
                <th>< ></th>
            </thead>
            <tbody>
                <c:forEach  var="oficina" items="#{oficinas}">
                    <tr>
                        <td>${oficina.nomeOficina}</td>
                        <td>${oficina.tipoOficina}</td>
                        <td>${oficina.monitor}</td>
                        <td><a href="Saga?logica=CadastrarOficina&idoficina=${oficina.idoficina}&acao=carregar"><button>Detalhes</button></a></td>
                        <td><a href="Saga?logica=GerenciarOficina&acao=frequencia&idoficina=${oficina.idoficina}"><button>Frequência</button></a></td>
                        <td><a href="Saga?logica=GerenciarOficina&acao=adicionarApenado&oficina=${oficina.idoficina}&nomeOficina=${oficina.nomeOficina}"><button>Adicinoar/Remover Apenado</button></a></td>
                    </tr>
                </c:forEach>    
            </tbody>
        </table>
        <a href="Saga?logica=GerenciarOficina&acao=cadastrar"><button >Novo Registro</button></a>

    </body>
</html>
