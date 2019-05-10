package logicas;

import beans.Penitenciaria;
import daos.PenitenciariaDao;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CadastrarPenitenciaria implements Logica {

    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res, EntityManagerFactory emf) throws Exception {

        String nome = req.getParameter("nome");
        String endereco = req.getParameter("endereco");
        String telefone = req.getParameter("telefone");
        int idpenitenciaria = 0;

        Penitenciaria p;

        PenitenciariaDao pDao = new PenitenciariaDao(emf);

        if (req.getParameter("idpenitenciaria") == null) {
            p = new Penitenciaria();
        } else {
            idpenitenciaria = Integer.parseInt(req.getParameter("idpenitenciaria"));
            p = pDao.findPenitenciaria(idpenitenciaria);
        }

        p.setNome(nome);
        p.setEndereco(endereco);
        p.setTelefone(telefone);
        
        if(idpenitenciaria == 0){
            pDao.create(p);
        }else{
            pDao.edit(p);
        }
        return "Saga?logica=GerenciarPenitenciaria";
    }

}
