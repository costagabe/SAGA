package logicas;

import beans.Usuario;
import daos.UsuarioDao;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Connection;


public class Login implements Logica{

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res,EntityManagerFactory emf ) throws Exception {
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");
        
        UsuarioDao userDao = new UsuarioDao(emf);
        Usuario user = userDao.findUserByLoginAndSenha(login, senha);
        if(user == null){
            return "login.jsp?msg=Usuario nao encontrado";
        }else{
            req.getSession().setAttribute("logado", user);
            return "sistema/index.jsp";
        }
        
    }
    
}
