package logicas;

import beans.Apenado;
import beans.CadastroCurso;
import beans.Curso;
import daos.ApenadoDao;
import daos.CadastroCursoDao;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdicionarApenadoCurso implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {
        String matriculaApenado = req.getParameter("matriculaApenado");
        int idcurso = Integer.parseInt(req.getParameter("idcurso"));

        Apenado a = new ApenadoDao(emf).findApenadoByMatricula(matriculaApenado);

        if (a == null) {
            req.setAttribute("mensagem", "Apenado com matricula " + matriculaApenado + " não encontrado!");
            return "sistema/gerenciar/curso/mensagem-adicionar.jsp";
        }
        Curso c = new Curso(idcurso);
        CadastroCurso cc = new CadastroCurso();

        cc.setApenado(a);
        cc.setCurso(c);

        CadastroCursoDao ccDao = new CadastroCursoDao(emf);

        ccDao.create(cc);
        
        req.setAttribute("mensagem", "Apenado cadastrado com sucesso no curso!");

        return "Saga?logica=GerenciarCurso";
    }

}
