package logicas;

import beans.Penitenciaria;
import daos.PenitenciariaDao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GerenciarPenitenciaria implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        PenitenciariaDao pDao = new PenitenciariaDao(emf);
        if (req.getParameter("acao") != null) {
            String acao = req.getParameter("acao");
            if (acao.equals("cadastrar")) {
                return "sistema/gerenciar/penitenciaria/cadastrar-penitenciaria.jsp";
            }
            if (acao.equals("editar")) {
                int idpenitenciaria = Integer.parseInt(req.getParameter("idpenitenciaria"));
                Penitenciaria p = pDao.findPenitenciaria(idpenitenciaria);
                req.setAttribute("penitenciaria", p);
                return "sistema/gerenciar/penitenciaria/cadastrar-penitenciaria.jsp";
            }
        }
        System.out.println("aaaa");
        List<Penitenciaria> penitenciarias = pDao.findPenitenciariaEntities();
        req.setAttribute("penitenciarias", penitenciarias);

        return "sistema/gerenciar/penitenciaria/gerenciar-penitenciaria.jsp";
    }

}
