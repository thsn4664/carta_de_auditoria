/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK;
import br.com.bb.exxi.cartaauditoria_nova.entidades.VwResultados;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class VwResultadosDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<VwResultados> q;
    private Root<VwResultados> root;
    //private Join vwResultadosPK;
    private Path dtBase;
    private Path cdCli;
    private Path cdPrd;
    private Path cdMdld;
    private Path cdDepe;
    private Path nmCtr;
    private Path nmMdld;
    private Path vlSld1;
    private Path vlSld2;
    private Path vlSld3;
    private Path vlSld4;
    private Path cdColSld;
    /*private Path vlEncargos;*/
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(VwResultados.class);
        root = q.from(VwResultados.class);

//        this.vwResultadosPK = root.join("vwResultadosPK");
//        this.dtBase = vwResultadosPK.get("dtBase");
//        this.cdCli = vwResultadosPK.get("cdCli");
//        this.cdPrd = vwResultadosPK.get("cdPrd");
//        this.cdMdld = vwResultadosPK.get("cdMdld");
//        this.cdDepe = vwResultadosPK.get("cdDepe");
//        this.nmCtr = vwResultadosPK.get("nmCtr");
        this.dtBase = root.get("vwResultadosPK").get("dtBase");
        this.cdCli = root.get("vwResultadosPK").get("cdCli");
        this.cdPrd = root.get("vwResultadosPK").get("cdPrd");
        this.cdMdld = root.get("vwResultadosPK").get("cdMdld");
        this.cdDepe = root.get("vwResultadosPK").get("cdDepe");
        this.nmCtr = root.get("vwResultadosPK").get("nmCtr");
        this.nmMdld = root.get("nmMdld");
        this.vlSld1 = root.get("vlSld1");
        this.vlSld2 = root.get("vlSld2");
        this.vlSld3 = root.get("vlSld3");
        this.vlSld4 = root.get("vlSld4");
        this.cdColSld = root.get("cdColSld");
        /*this.vlEncargos = root.get("vlEncargos");*/

    }
    
    public List<VwResultados> listaResultados(PedidosPK pk, Integer prd){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMdld);
        select.add(nmMdld);
        select.add(cdDepe);
        select.add(nmCtr);
        select.add(vlSld1);
        select.add(vlSld2);
        select.add(vlSld3);
        select.add(vlSld4);
        select.add(cdColSld);
        /*select.add(vlEncargos);*/
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        predicados.add(cb.equal(cdPrd, prd));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(cdMdld));
        ordem.add(cb.asc(nmCtr));
        List<VwResultados> lista = new ArrayList<VwResultados>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<VwResultados>();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
        }
        em.close();
        return lista;
    }
}
