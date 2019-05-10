<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Apenado</title>
    </head>
    <body>
        <h3>Adicionar apenado no curso: ${param.nomeOficina}</h3>
        <form action="Saga" method="POST">
           Matrícula: <input name="matriculaApenado" type="text"/><br/><br/>
            <input type="hidden" name="logica" value="AdicionarApenadoOficina"/>
            <input type="hidden" name="idoficina" value="${param.oficina}"/>
            Data de Entrada/Saída: <input type="date" name="data"/><br/>
            Entrada: <input name="entrada" type="radio" value="entrada"/><br/>
            Saída: <input name="entrada" type="radio" value="saida"/><br/>
            <input type="submit" value="Enviar"/>
        </form>
        
    </body>
</html>
