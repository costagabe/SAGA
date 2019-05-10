package logicas;

import beans.Apenado;
import beans.CadastroCurso;
import beans.CadastroOficina;
import beans.Curso;
import beans.Oficina;
import daos.ApenadoDao;
import daos.CadastroCursoDao;
import daos.CadastroOficinaDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdicionarApenadoOficina implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

        Date data = new Date(format.parse(req.getParameter("data")).getTime());
        String matriculaApenado = req.getParameter("matriculaApenado");
        int idoficina = Integer.parseInt(req.getParameter("idoficina"));
        String entrada = req.getParameter("entrada");

        if (entrada == null) {
            req.setAttribute("mensagem", "Você não selecionou se deseja fazer a entrada ou retirada do apenado na oficina");
            return "sistema/gerenciar/curso/mensagem-adicionar.jsp";
        }

        Apenado a = new ApenadoDao(emf).findApenadoByMatricula(matriculaApenado);

        if (a == null) {
            req.setAttribute("mensagem", "Apenado com matricula " + matriculaApenado + " não encontrado!");
            return "sistema/gerenciar/curso/mensagem-adicionar.jsp";
        }

        Oficina o = new Oficina(idoficina);
        CadastroOficina co = new CadastroOficina();

        co.setApenado(a);
        co.setOficina(o);

        CadastroOficinaDao coDao = new CadastroOficinaDao(emf);
        if (entrada.equals("entrada")) {
            co.setDataEntradaOficina(data);
            coDao.create(co);
        } else {
            co = coDao.findCadastroOficinaByMatriculaApenado(matriculaApenado);
            if (co != null) {
                co.setDataSaidaOficina(data);
                coDao.edit(co);
            } else {
                req.setAttribute("mensagem", "Apenado com matricula " + matriculaApenado + " não está cadastrado na oficina para ser removido!");
                return "sistema/gerenciar/curso/mensagem-adicionar.jsp";
            }
        }

        return "Saga?logica=GerenciarOficina";
    }

}
