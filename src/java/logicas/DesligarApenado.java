package logicas;

import beans.Registros;
import daos.RegistrosDao;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DesligarApenado implements Logica{

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        String matricula = req.getParameter("matriculaApenado");
        String motivo = req.getParameter("motivoSaida");
        
        RegistrosDao rDao = new RegistrosDao(emf);
        
        Registros r = rDao.findRegistrosByMatriculaApenado(matricula);
        r.setMotivoSaida(motivo);
        r.setDataSaida(Calendar.getInstance().getTime());
        
        rDao.edit(r);
        
        return "Saga?logica=GerenciarApenado";
    }
    
}
