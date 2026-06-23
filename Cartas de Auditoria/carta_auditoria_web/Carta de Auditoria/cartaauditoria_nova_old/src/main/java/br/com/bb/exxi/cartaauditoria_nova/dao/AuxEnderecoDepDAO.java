/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEnderecoDep;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class AuxEnderecoDepDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<AuxEnderecoDep> q;
    private Root<AuxEnderecoDep> root;
	
    private Path cdPrefDep;
    private Path dvPrefDep;
    private Path nmDep;
    private Path nrCep;
    private Path txLgr;
    private Path txBai;
    private Path txCmpt;
    private Path txMun;
    private Path txUf;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(AuxEnderecoDep.class) ;
        root = q.from(AuxEnderecoDep.class) ;

        this.cdPrefDep = root.get("cdPrefDep");
        this.dvPrefDep = root.get("dvPrefDep");
        this.nmDep = root.get("nmDep");
        this.nrCep = root.get("nrCep");
        this.txLgr = root.get("txLgr");
        this.txBai = root.get("txBai");
        this.txCmpt = root.get("txCmpt");
        this.txMun = root.get("txMun");
        this.txUf = root.get("txUf");
    }
    
    public AuxEnderecoDep leEndereco(Integer cdDep){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrefDep);
        select.add(dvPrefDep);
        select.add(nmDep);
        select.add(nrCep);
        select.add(txLgr);
        select.add(txCmpt);
        select.add(txBai);
        select.add(txMun);
        select.add(txUf);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdPrefDep, cdDep));
        AuxEnderecoDep aux = new AuxEnderecoDep();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getSingleResult();
        }catch(NonUniqueResultException nure){
            List<AuxEnderecoDep> auxLista = em.createQuery(q).getResultList();
            aux = auxLista.get(0);
        }catch(NoResultException nre){
            aux = null;
        }catch(Exception e){
            System.out.println("Erro ao executar leEndereco!");
            e.printStackTrace();
        }
        em.close();
        return aux;
        
    }

    
}
