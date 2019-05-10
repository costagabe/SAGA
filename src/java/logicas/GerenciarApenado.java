package logicas;

import beans.Apenado;
import beans.Penitenciaria;
import daos.ApenadoDao;
import daos.PenitenciariaDao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GerenciarApenado implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        if (req.getParameter("acao") != null && req.getParameter("acao").equals("cadastrar")) {
            List<Penitenciaria> penitenciarias = new PenitenciariaDao(emf).findPenitenciariaEntities();
            req.setAttribute("penitenciarias", penitenciarias);
            return "sistema/gerenciar/apenado/cadastrar-apenado.jsp";
        }
        if (req.getParameter("acao") != null && req.getParameter("acao").equals("desligar")) {
            return "sistema/gerenciar/apenado/desligar-apenado.jsp";
        }
        int pgAtual = 0;
        if (req.getParameter("pgAtual") != null) {
            pgAtual = Integer.parseInt(req.getParameter("pgAtual"));
        }
        ApenadoDao apenadoDao = new ApenadoDao(emf);
        List<Apenado> apenados = apenadoDao.findApenadosAtivos();
        req.setAttribute("apenados", apenados);

        return "/sistema/gerenciar/apenado/gerenciar-apenado.jsp";
    }

}
