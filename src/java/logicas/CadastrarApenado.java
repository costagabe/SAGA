package logicas;

import beans.Apenado;
import beans.DadosPenitenciarios;
import beans.DadosPessoais;
import beans.Penitenciaria;
import beans.Registros;
import daos.ApenadoDao;
import daos.DadosPenitenciariosDao;
import daos.DadosPessoaisDao;
import daos.PenitenciariaDao;
import daos.RegistrosDao;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CadastrarApenado implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        String matricula;
        String nome;
        Date dataNascimento;
        String rg;
        String cpf;
        String nomePai;
        String nomeMae;
        String nacionalidade;
        String naturalidade;
        String escolaridade;
        String estadoCivil;
        int penitenciaria;
        String artigo;
        String tempoPrisao;
        List<Penitenciaria> penitenciarias = new PenitenciariaDao(emf).findPenitenciariaEntities();
        req.setAttribute("penitenciarias", penitenciarias);

        //quer retornar os dados de um apenado passando o id dele
        if (req.getParameter("idapenado") != null && req.getParameter("form") == null) {
            int idApenado = Integer.parseInt(req.getParameter("idapenado"));
            Apenado apenadoRet = new ApenadoDao(emf).findApenado(idApenado);
            req.setAttribute("apenado", apenadoRet);
            return "sistema/gerenciar/apenado/cadastrar-apenado.jsp";
        }

        //Cadastro ou alteração de um apenado
        if (req.getParameter("form") != null) {

            SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

            matricula = req.getParameter("matricula");
            nome = req.getParameter("nome");
            dataNascimento = new Date(format.parse(req.getParameter("dataNascimento")).getTime());
            rg = req.getParameter("rg");
            cpf = req.getParameter("cpf");
            nomePai = req.getParameter("nomePai");
            nomeMae = req.getParameter("nomeMae");
            nacionalidade = req.getParameter("nacionalidade");
            naturalidade = req.getParameter("naturalidade");
            escolaridade = req.getParameter("escolaridade");
            estadoCivil = req.getParameter("estadoCivil");
            penitenciaria = Integer.parseInt(req.getParameter("penitenciaria"));
            artigo = req.getParameter("artigo");
            tempoPrisao = req.getParameter("tempoPrisao");

            int idApenado = 0;

            Apenado a;
            DadosPessoais dp;
            DadosPenitenciarios dpn;
            Registros r = new Registros();

            DadosPessoaisDao dpDao = new DadosPessoaisDao(emf);
            DadosPenitenciariosDao dpnDao = new DadosPenitenciariosDao(emf);
            ApenadoDao apenadoDao = new ApenadoDao(emf);
            RegistrosDao rDao = new RegistrosDao(emf);

            if (req.getParameter("idapenado") != null) {
                idApenado = Integer.parseInt(req.getParameter("idapenado"));
                a = apenadoDao.findApenado(idApenado);
                dp = a.getDadosPessoais();
                dpn = a.getDadosPenitenciarios();
            } else {
                a = new Apenado();
                dp = new DadosPessoais();
                dpn = new DadosPenitenciarios();

                
            }

            dp.setCpf(cpf);
            dp.setNome(nome);
            dp.setDataNascimento(dataNascimento);
            dp.setRg(rg);
            dp.setCpf(cpf);
            dp.setNomePai(nomePai);
            dp.setNomeMae(nomeMae);
            dp.setNacionalidade(nacionalidade);
            dp.setNaturalidade(naturalidade);
            dp.setEscolaridade(escolaridade);
            dp.setEstadoCivil(estadoCivil);

            dpn.setPenitenciaria(new Penitenciaria(penitenciaria));
            dpn.setArtigo(artigo);
            dpn.setTempoCadeia(tempoPrisao);

            a.setMatricula(matricula);
            a.setDadosPessoais(dp);
            a.setDadosPenitenciarios(dpn);

            if (idApenado == 0) {
                dpDao.create(dp);
                dpnDao.create(dpn);
                apenadoDao.create(a);
                r.setDataEntrada(Calendar.getInstance().getTime());
                r.setApenado(a);
                r.setMotivoSaida(" ");
                rDao.create(r);
            } else {
                dpDao.edit(dp);
                dpnDao.edit(dpn);
                apenadoDao.edit(a);
            }

            return "Saga?logica=GerenciarApenado";
        }
        return "";
    }

}
