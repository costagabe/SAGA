<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Desligar Apenado</title>
    </head>
    <body>
        <h3>Desligar apenado do programa</h3>
        <form action="Saga" method="POST">
            Matrícula: <input name="matriculaApenado" type="text"/><br/><br/>
            Motivo: <input type="text" name="motivoSaida"/><br/><br/>
            <input type="hidden" name="logica" value="DesligarApenado"/>
            
            <input type="submit" value="Enviar"/>
        </form>

    </body>
</html>
