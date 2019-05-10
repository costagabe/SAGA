package logicas;

import beans.CadastroOficina;
import beans.Oficina;
import beans.PresencaOficina;
import daos.OficinaDao;
import daos.PresencaOficinaDao;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrequenciaOficina implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        int idOficina = Integer.parseInt(req.getParameter("idoficina"));

        OficinaDao oDao = new OficinaDao(emf);
        Oficina o = oDao.findOficina(idOficina);

        PresencaOficinaDao poDao = new PresencaOficinaDao(emf);

        for (CadastroOficina co : o.getCadastroOficinaList()) {
            String presenca = req.getParameter("presenca" + co.getApenado().getIdapenado());
            String justificativa = req.getParameter("justificativa" + co.getApenado().getIdapenado());

            PresencaOficina po = new PresencaOficina();
            po.setApenado(co.getApenado());
            po.setOficina(o);
            po.setData(Calendar.getInstance().getTime());
            po.setJustificativaFalta(justificativa);
            po.setSituacao(presenca != null ? 1 : 0);

            poDao.create(po);

        }

        return "Saga?logica=GerenciarOficina";
    }

}
