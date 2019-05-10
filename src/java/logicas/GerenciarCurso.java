package logicas;

import beans.Curso;
import daos.CursoDao;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GerenciarCurso implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        //Clicou em algum botão apenas pra ser redirecionado para a página de gerenciamento
        if (req.getParameter("acao") != null) {
            if (req.getParameter("acao").equals("cadastrar")) {
                return "sistema/gerenciar/curso/cadastrar-curso.jsp";
            }
            if(req.getParameter("acao").equals("adicionarApenado")){
                return "sistema/gerenciar/curso/adicionar-apenado.jsp";
            }
            if(req.getParameter("acao").equals("frequencia")){
                Curso c;
                CursoDao cDao = new CursoDao(emf);
                
                int idcurso = Integer.parseInt(req.getParameter("idcurso"));
                
                c = cDao.findCurso(idcurso);
                req.setAttribute("cadastros", c.getCadastroCursoList());
                req.setAttribute("idcurso", idcurso);
                
                return "sistema/gerenciar/curso/frequencia-curso.jsp";
            }
        }
        CursoDao cursoDao = new CursoDao(emf);
        List<Curso> cursos = cursoDao.findCursoEntities();

        req.setAttribute("cursos", cursos);
        return "/sistema/gerenciar/curso/gerenciar-curso.jsp";
    }

}
