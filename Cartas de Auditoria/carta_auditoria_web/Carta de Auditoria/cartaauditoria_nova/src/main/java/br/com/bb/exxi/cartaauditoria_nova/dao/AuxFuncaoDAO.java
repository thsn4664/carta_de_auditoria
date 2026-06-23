/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
public class AuxFuncaoDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<AuxFuncao> q;
    private Root<AuxFuncao> root;
	
    private Path id;
    private Path nmFuncao;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(AuxFuncao.class) ;
        root = q.from(AuxFuncao.class) ;

        this.id = root.get("id");
        this.nmFuncao = root.get("nmFuncao");
        
    }

    public void atualiza(AuxFuncao ap){
        init();
        em = new JPAUtil().getEntityManager();
        em.merge(ap);
        em.close();
    }
    
    public AuxFuncao lerFuncao(int codFuncao){
        init();
        select = new ArrayList<Selection>();
        select.add(id);
        select.add(nmFuncao);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(id, codFuncao));
        AuxFuncao aux = new AuxFuncao();
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
    
}
