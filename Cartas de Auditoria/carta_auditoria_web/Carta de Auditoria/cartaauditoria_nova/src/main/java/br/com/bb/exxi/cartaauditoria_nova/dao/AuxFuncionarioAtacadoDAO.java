/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncionarioAtacado;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author F9066619
 */
public class AuxFuncionarioAtacadoDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<AuxFuncionarioAtacado> q;
    private Root<AuxFuncionarioAtacado> root;
	
    private Path matricula;
    private Path nmFunci;
    private Path dtNasc;
    
    private Join dependencia;
    private Path cdDep;
    private Path nmDep;

    private Join funcao;
    private Path codFuncao;
    private Path nmFuncao;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(AuxFuncionarioAtacado.class) ;
        root = q.from(AuxFuncionarioAtacado.class) ;

        this.matricula = root.get("matricula");
        this.nmFunci = root.get("nmFunci");
//        this.telefone = root.get("telefone");
        this.dtNasc = root.get("dtNasc");
//        this.ativo = root.get("ativo");
        
        this.dependencia = root.join("dependencia",JoinType.INNER);
        this.cdDep = dependencia.get("cdDep");
        this.nmDep = dependencia.get("nmDep");
        
        this.funcao = root.join("funcao",JoinType.INNER);
        this.codFuncao = funcao.get("id");
        this.nmFuncao = funcao.get("nmFuncao");
        
    }

    public void atualiza(AuxFuncionarioAtacado ap){
        init();
        em = new JPAUtil().getEntityManager();
        em.merge(ap);
        em.close();
    }
    
    public AuxFuncionarioAtacado lerFunci(String m){
        init();
        select = new ArrayList<Selection>();
        select.add(matricula);
        select.add(nmFunci);
//        select.add(telefone);
        select.add(dtNasc);
//        select.add(ativo);
        select.add(cdDep);
        select.add(nmDep);
        select.add(codFuncao);
        select.add(nmFuncao);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(matricula, m));
        AuxFuncionarioAtacado aux = new AuxFuncionarioAtacado();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            HttpServletResponse r = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            try {
                //r.sendRedirect("https://dicom3.intranet.bb.com.br/cartaauditoria4011/acessoNegado.disem");
                r.sendRedirect("https://cartaauditoria.intranet.bb.com.br/cartaauditoria4011/acessoNegado.disem");
            } catch (IOException ex) {
                Logger.getLogger(AuxFuncionarioAtacadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(Exception e){
            System.out.println("Erro ao executar lerFunci!");
            e.printStackTrace();
        }
        em.close();
        return aux;
        
    }

    public List<AuxFuncionarioAtacado> listaFunci(int prefDep, String cargo, String busca){
        init();
        select = new ArrayList<Selection>();
        select.add(matricula);
        select.add(nmFunci);
//        select.add(telefone);
        select.add(dtNasc);
//        select.add(ativo);
        select.add(cdDep);
        select.add(nmDep);
        select.add(codFuncao);
        select.add(nmFuncao);
        List<Predicate> predicados = new ArrayList<Predicate>();
        if(prefDep != 0){
            predicados.add(cb.equal(cdDep, prefDep));
        }
        if(!cargo.trim().equals("")){
            predicados.add(cb.like(nmFuncao, "%"+cargo.trim()+"%"));
        }
        if(!busca.trim().equals("")){
            predicados.add(cb.like(nmFunci,"%"+busca.trim()+"%"));
        }
//        predicados.add(cb.equal(ativo, 1));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmFunci));
        List<AuxFuncionarioAtacado> lista = new ArrayList<AuxFuncionarioAtacado>();
//        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            lista = em.createQuery(q).getResultList();
//        }catch(Exception e){
//            System.out.println("Erro ao executar listaFunci!");
//            e.printStackTrace();
//        }
        em.close();
        return lista;
    }

}
