package logicas;

import beans.CadastroCurso;
import beans.Curso;
import beans.PresencaCurso;
import daos.CursoDao;
import daos.PresencaCursoDao;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrequenciaCurso implements Logica{

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        
        int idCurso = Integer.parseInt(req.getParameter("idcurso"));
        
        CursoDao cDao = new CursoDao(emf);
        Curso c = cDao.findCurso(idCurso);
        
        PresencaCursoDao pcDao = new PresencaCursoDao(emf);
        
        for(CadastroCurso cc : c.getCadastroCursoList()){
            String presenca = req.getParameter("presenca"+cc.getApenado().getIdapenado());
            String justificativa = req.getParameter("justificativa"+cc.getApenado().getIdapenado());
            
            PresencaCurso pc = new PresencaCurso();
            pc.setApenado(cc.getApenado());
            pc.setCurso(c);
            pc.setData(Calendar.getInstance().getTime());
            pc.setJustificativaFalta(justificativa);
            pc.setSituacao(presenca!= null ? 1 : 0);
            
            
            pcDao.create(pc);
            
        }
        
        return "Saga?logica=GerenciarCurso";
    }
    
}
