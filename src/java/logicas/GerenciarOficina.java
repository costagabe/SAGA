package logicas;

import beans.Oficina;
import daos.OficinaDao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GerenciarOficina implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        OficinaDao oDao = new OficinaDao(emf);
        
        if (req.getParameter("acao") != null) {
            String acao = req.getParameter("acao");

            if (acao.equals("cadastrar")) {
                return "sistema/gerenciar/oficina/cadastrar-oficina.jsp";
            }
            if (acao.equals("carregar")) {
                int idOficina = Integer.parseInt(req.getParameter("idoficina"));
                Oficina o = oDao.findOficina(idOficina);
                req.setAttribute("oficina", o);
                return "sistema/gerenciar/oficina/gerenciar-oficina.jsp";
            }
            if(req.getParameter("acao").equals("adicionarApenado")){
                return "sistema/gerenciar/oficina/adicionar-apenado.jsp";
            }
            if(req.getParameter("acao").equals("frequencia")){
                Oficina o;
                
                int idoficina = Integer.parseInt(req.getParameter("idoficina"));
                
                o = oDao.findOficina(idoficina);
                req.setAttribute("cadastros", o.getCadastroAtivoOficinaList());
                req.setAttribute("idoficina", idoficina);
                
                return "sistema/gerenciar/oficina/frequencia-oficina.jsp";
            }
        }
        List<Oficina> oficinas = oDao.findOficinaEntities();
        req.setAttribute("oficinas", oficinas);
        return "sistema/gerenciar/oficina/gerenciar-oficina.jsp";
    }

}
