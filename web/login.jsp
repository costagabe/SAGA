<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
       
        <form action="Saga" method="POST">
            <input type="text" name="login" placeholder="Login"/>
            <br/>
            <input type="password" name="senha" placeholder="Senha"/>
            <br/>
            <input type="submit" name="Enviar" value="Entrar"/>
            <input type="hidden" name="logica" value="Login"/>
        </form>
    </body>
</html>
