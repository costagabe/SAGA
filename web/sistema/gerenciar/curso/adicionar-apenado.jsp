<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Adicionar Apenado</title>
    </head>
    <body>
        <h3>Adicionar apenado no curso: ${param.nomeCurso}</h3>
        <form action="Saga" method="POST">
           Matrícula: <input name="matriculaApenado" type="text"/>
            <input type="hidden" name="logica" value="AdicionarApenadoCurso"/>
            <input type="hidden" name="idcurso" value="${param.curso}"/>
            <input type="submit" value="Enviar"/>
        </form>
        
    </body>
</html>
