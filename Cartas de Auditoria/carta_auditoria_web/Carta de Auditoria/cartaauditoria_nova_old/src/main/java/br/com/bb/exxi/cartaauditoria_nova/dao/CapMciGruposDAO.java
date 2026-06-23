/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.CapMciGrupos;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class CapMciGruposDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<CapMciGrupos> q;
    private Root<CapMciGrupos> root;
	
    private Path cdMci;
    private Path rzSocial;
    private Path cnpj;
    private Path cnpjRad;
    private Path ancora;
    private Path cdGrupo;
    private Path nmGrupo;
    
    private Join agencia;
    private Path cdDep;
    private Path nmDep;
    private Path cdCsa;
    private Path cdSureg;
    private Path cdSuper;
    
    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(CapMciGrupos.class) ;
        root = q.from(CapMciGrupos.class) ;

        this.cdMci = root.get("cdMci");
        this.rzSocial = root.get("rzSocial");
        this.cnpj = root.get("cnpj");
        this.cnpjRad = root.get("cnpjRad");
        this.ancora = root.get("ancora");
        this.cdGrupo = root.get("cdGrupo");
        this.nmGrupo = root.get("nmGrupo");
        
        this.agencia = root.join("agencia", JoinType.LEFT);
        this.cdDep = agencia.get("cdDep");
        this.nmDep = agencia.get("nmDep");
        this.cdCsa = agencia.get("cdCsa");
        this.cdSureg = agencia.get("cdSureg");
        this.cdSuper = agencia.get("cdSuper");
        
    }

    public void atualiza(CapMciGrupos ap){
        init();
        em = new JPAUtil().getEntityManager();
        em.merge(ap);
        em.close();
    }
    
    public CapMciGrupos lerClienteAncora(Integer radCnpj){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMci);
        select.add(rzSocial);
        select.add(cnpj);
        select.add(cnpjRad);
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSureg);
        select.add(cdSuper);
        select.add(cdCsa);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cnpjRad, radCnpj));
        predicados.add(cb.equal(ancora, "S"));
        CapMciGrupos aux = new CapMciGrupos();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
        }catch(NonUniqueResultException nure){
            List<CapMciGrupos> aux2 = new ArrayList<CapMciGrupos>();
            aux2 = em.createQuery(q).getResultList();
            aux = aux2.get(0);
        }catch(NoResultException nre){
            aux = null;
        }
        em.close();
        return aux;
    }

    public CapMciGrupos leCliente(int pcdCli){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMci);
        select.add(rzSocial);
        select.add(cnpj);
        select.add(cnpjRad);
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSureg);
        select.add(cdSuper);
        select.add(cdCsa);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdMci, pcdCli));
        CapMciGrupos aux = new CapMciGrupos();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            aux = null;
        }
        em.close();
        return aux;
    }

    public List<CapMciGrupos> listaClientes(Integer cdRef, Integer tipoLote){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMci);
        select.add(rzSocial);
        select.add(cnpj);
        select.add(cnpjRad);
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSureg);
        select.add(cdSuper);
        select.add(cdCsa);
        List<Predicate> predicados = new ArrayList<Predicate>();
        if(tipoLote == 1){
            predicados.add(cb.equal(cnpjRad, cdRef));
        }else{
            predicados.add(cb.equal(cdGrupo, cdRef));
        }
        List<CapMciGrupos> aux = new ArrayList<CapMciGrupos>();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            aux = new ArrayList<CapMciGrupos>();
        }
        em.close();
        return aux;
    }

    public List<CapMciGrupos> listaGrupos(int prefUsuario) {
        init();
        select = new ArrayList<Selection>();
        select.add(cdGrupo);
        select.add(nmGrupo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        if(prefUsuario != 0){
            predicados.add(cb.or(cb.equal(cdDep, prefUsuario), cb.equal(cdSureg, prefUsuario), cb.equal(cdSuper, prefUsuario), cb.equal(cdCsa, prefUsuario)));
            predicados.add(cb.equal(ancora, "S"));
        }
        predicados.add(cb.isNotNull(cdGrupo));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmGrupo));
        List<CapMciGrupos> aux = new ArrayList<CapMciGrupos>();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            aux = new ArrayList<CapMciGrupos>();
        }
        em.close();
        return aux;
    }

    public Map<String, Integer> mapBusca(String campoBusca, int prefUsuario) {
        init();
        select = new ArrayList<Selection>();
        select.add(cdGrupo);
        select.add(nmGrupo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.like(nmGrupo, "%" + campoBusca + "%"));
        if(prefUsuario != 0){
            predicados.add(cb.or(cb.equal(cdDep, prefUsuario), cb.equal(cdSureg, prefUsuario), cb.equal(cdSuper, prefUsuario), cb.equal(cdCsa, prefUsuario)));
            predicados.add(cb.equal(ancora, "S"));
        }
        //predicados.add(cb.isNotNull(cdGrupo));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmGrupo));
        List<CapMciGrupos> aux = new ArrayList<CapMciGrupos>();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            aux = new ArrayList<CapMciGrupos>();
        }
        em.close();

//        List<CapMciGrupos> listaAfinPesquisa = listaGrupos(campoBusca, prefUsuario);

        Map<String, Integer> mapa = new LinkedHashMap<String, Integer>();

        for (CapMciGrupos ap : aux) {
            mapa.put(ap.getNmGrupo().trim(), ap.getCdGrupo());
        }

        return mapa;

    }


}
