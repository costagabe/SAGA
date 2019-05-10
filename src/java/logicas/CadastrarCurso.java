package logicas;

import beans.Curso;
import daos.CursoDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CadastrarCurso implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

        int idcurso = 0;
        String nomeCurso = req.getParameter("nomeCurso");
        String tempoDiario = req.getParameter("tempoDiario");
        String quantidadeDiasCurso = req.getParameter("quantidadeDiasCurso");
        String instrutor = req.getParameter("instrutor");
        String vagasDisponiveis = req.getParameter("vagasDisponiveis");
        Date dataInicio = null;
        Date dataFim = null;
        if (req.getParameter("dataInicio") != null) {
            dataInicio = new Date(format.parse(req.getParameter("dataInicio")).getTime());
        }
        if (req.getParameter("dataFim") != null) {
            dataFim = new Date(format.parse(req.getParameter("dataFim")).getTime());
        }

        Curso c;
        CursoDao cDao = new CursoDao(emf);
        //cadastro
        if (req.getParameter("idcurso") == null) {
            c = new Curso();
        } else {
            idcurso = Integer.parseInt(req.getParameter("idcurso"));
            c = cDao.findCurso(idcurso);
            //carregar para editar
            if (req.getParameter("acao").equals("carregar")) {
                req.setAttribute("curso", c);
                return "sistema/gerenciar/curso/cadastrar-curso.jsp";
            }
        }

        c.setNomeCurso(nomeCurso);
        c.setTempoDiario(Double.parseDouble(tempoDiario));
        c.setQuantidadeDiasCurso(Integer.parseInt(quantidadeDiasCurso));
        c.setInstrutor(instrutor);
        c.setVagasDisponiveis(Integer.parseInt(vagasDisponiveis));
        c.setDataFim(dataFim);
        c.setDataInicio(dataInicio);

        if (idcurso == 0) {
            cDao.create(c);
        } else {
            cDao.edit(c);
        }

        return "Saga?logica=GerenciarCurso";
    }
}
