/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxDependencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class AuxDependenciaDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<AuxDependencia> q;
    private Root<AuxDependencia> root;
	
    private Path cdDep;
    private Path nmDep;
    private Path cdSuper;
    private Path cdSureg;
    private Path cdCsa;
    private Path cdTpDep;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(AuxDependencia.class) ;
        root = q.from(AuxDependencia.class) ;

        this.cdDep = root.get("cdDep");
        this.nmDep = root.get("nmDep");
        this.cdSuper = root.get("cdSuper");
        this.cdSureg = root.get("cdSureg");
        this.cdCsa = root.get("cdCsa");
        this.cdTpDep = root.get("cdTpDep");
        
    }

    public void atualiza(AuxDependencia ap){
        init();
        em = new JPAUtil().getEntityManager();
        em.merge(ap);
        em.close();
    }
    
    public AuxDependencia lerDependencia(int prefDep){
        init();
        select = new ArrayList<Selection>();
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSuper);
        select.add(cdSureg);
        select.add(cdCsa);
        select.add(cdTpDep);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdDep, prefDep));
        AuxDependencia aux = new AuxDependencia();
//        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
//        }catch(Exception e){
//            System.out.println("Erro ao executar lerDependencia!");
//            e.printStackTrace();
//        }
        em.close();
        return aux;
    }
    
    public List<AuxDependencia> listaDependencia(int tp, int superCsa, int sureg, int prefDep){
        init();
        select = new ArrayList<Selection>();
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSuper);
        select.add(cdSureg);
        select.add(cdCsa);
        select.add(cdTpDep);
        
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdTpDep, tp));
        predicados.add(cb.greaterThan(cdSuper, 0));
        if (superCsa != 0){predicados.add(cb.or(cb.equal(cdSuper, superCsa),cb.equal(cdCsa, superCsa)));}
        if (sureg != 0){predicados.add(cb.equal(cdSureg, sureg));}
        if (prefDep != 0){predicados.add(cb.equal(cdDep, prefDep));}
        
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(cdDep));
        
        List<AuxDependencia> aux = new ArrayList<AuxDependencia>();
//        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[] {}));
            aux = em.createQuery(q).getResultList();
//        }catch(Exception e){
//            System.out.println("Erro ao executar listaDependencia!");
//            e.printStackTrace();
//        }
        em.close();
        return aux;
    }
}
