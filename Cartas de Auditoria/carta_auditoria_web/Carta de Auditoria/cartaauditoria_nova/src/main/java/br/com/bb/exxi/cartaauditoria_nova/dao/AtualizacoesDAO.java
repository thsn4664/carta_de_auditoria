/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.Atualizacoes;
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
public class AtualizacoesDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<Atualizacoes> q;
    private Root<Atualizacoes> root;
    private Path nmAcao;
    private Path tsUltExec;
    private Path tsPxExec;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Atualizacoes.class);
        root = q.from(Atualizacoes.class);

        this.nmAcao = root.get("nmAcao");
        this.tsUltExec = root.get("tsUltExec");
        this.tsPxExec = root.get("tsPxExec");

    }
    
    public Atualizacoes leAtualizacao(String nmAcao){
        init();
        select = new ArrayList<Selection>();
        select.add(this.nmAcao);
        select.add(tsUltExec);
        select.add(tsPxExec);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(this.nmAcao, nmAcao));
        Atualizacoes aux = new Atualizacoes();
//        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
//        }
        em.close();
        return aux;
    }
    
}
