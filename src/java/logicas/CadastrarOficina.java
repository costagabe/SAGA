package logicas;

import beans.Oficina;
import daos.OficinaDao;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CadastrarOficina implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        int idOficina = 0;
        String nomeOficina = req.getParameter("nomeOficina");
        String tipoOficina = req.getParameter("tipoOficina");
        String monitor = req.getParameter("monitor");
        Double tempo = req.getParameter("tempo") != null? Double.parseDouble(req.getParameter("tempo")) : 0;

        Oficina o;
        OficinaDao oDao = new OficinaDao(emf);

        if (req.getParameter("idoficina") == null) {
            o = new Oficina();
        } else {
            idOficina = Integer.parseInt(req.getParameter("idoficina"));
            o = oDao.findOficina(idOficina);
            if (req.getParameter("acao").equals("carregar")) {
                req.setAttribute("oficina", o);
                return "sistema/gerenciar/oficina/cadastrar-oficina.jsp";
            }
        }
        
        o.setMonitor(monitor);
        o.setNomeOficina(nomeOficina);
        o.setTempo(tempo);
        o.setTipoOficina(tipoOficina);
        
        if(idOficina == 0){
            oDao.create(o);
        }else{
            oDao.edit(o);
        }

        return "Saga?logica=GerenciarOficina";
    }

}
