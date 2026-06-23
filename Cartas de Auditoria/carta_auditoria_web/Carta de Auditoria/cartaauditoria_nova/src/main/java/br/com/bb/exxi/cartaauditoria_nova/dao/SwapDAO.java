/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Swap;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class SwapDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<Swap> q;
    private Root<Swap> root;
    //private Join swapPK;
    private Path dtBase;
    private Path cdCli;
    private Path cdPrd;
    private Path cdMdld;
    private Path cdDepe;
    private Path nmCtr;
    private Path nmMdld;
    private Path vlContratado;
    private Path vlMtm;
    private Path vlAtualizado;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Swap.class);
        root = q.from(Swap.class);

//        this.swapPK = root.join("swapPK");
//        this.dtBase = swapPK.get("dtBase");
//        this.cdCli = swapPK.get("cdCli");
//        this.cdPrd = swapPK.get("cdPrd");
        this.dtBase = root.get("swapPK").get("dtBase");
        this.cdCli = root.get("swapPK").get("cdCli");
        this.cdPrd = root.get("swapPK").get("cdPrd");
        this.nmCtr = root.get("swapPK").get("nmCtr");
        this.cdMdld = root.get("cdMdld");
        this.nmMdld = root.get("nmMdld");
        this.cdDepe = root.get("cdDepe");
//        this.nmCtr = swapPK.get("nmCtr");
        this.vlContratado = root.get("vlContratado");
        this.vlMtm = root.get("vlMtm");
        this.vlAtualizado = root.get("vlAtualizado");

    }
    
    public List<Swap> listaSwap(PedidosPK pk, Integer prd){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMdld);
        select.add(nmMdld);
        select.add(cdDepe);
        select.add(nmCtr);
        select.add(vlContratado);
        select.add(vlMtm);
        select.add(vlAtualizado);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        predicados.add(cb.equal(cdPrd, prd));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(cdMdld));
        ordem.add(cb.asc(nmCtr));
        List<Swap> lista = new ArrayList<Swap>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Swap>();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
        }
        em.close();
        return lista;
    }
}
