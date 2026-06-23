/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.CapMci;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class CapMciDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<CapMci> q;
    private Root<CapMci> root;
	
    private Path cdMci;
    private Path rzSocial;
    private Path cnpj;
    private Path tpCadastro;
    private Path sitCadastro;
    private Path natJur;
    private Path sitFunc;
    private Path okBaixaCdc;
    private Path okScr;
    private Path okAnotImped;
    private Path dtRef;
    private Path cdGrupo;
    private Path nmGrupo;
    private Path cdAtvEcon;
    private Path nmAtvEcon;
    private Path vlFat;
    private Path mesFat;
    private Path cdCarteira;
    private Path cdRestrito;
    private Path tpPessoa;
    
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
        q = cb.createQuery(CapMci.class) ;
        root = q.from(CapMci.class) ;

        this.cdMci = root.get("cdMci");
        this.rzSocial = root.get("rzSocial");
        this.tpCadastro = root.get("tpCadastro");
        this.sitCadastro = root.get("sitCadastro");
        this.natJur = root.get("natJur");
        this.sitFunc = root.get("sitFunc");
        this.okBaixaCdc = root.get("okBaixaCdc");
        this.okScr = root.get("okScr");
        this.okAnotImped = root.get("okAnotImped");
        this.cnpj = root.get("cnpj");
        this.dtRef = root.get("dtRef");
        this.cdGrupo = root.get("cdGrupo");
        this.nmGrupo = root.get("nmGrupo");
        this.cdAtvEcon = root.get("cdAtvEcon");
        this.nmAtvEcon = root.get("nmAtvEcon");
        this.vlFat = root.get("vlFat");
        this.mesFat = root.get("mesFat");
        this.cdCarteira = root.get("cdCarteira");
        this.cdRestrito = root.get("cdRestrito");
        this.tpPessoa = root.get("tpPessoa");
        
        this.agencia = root.join("agencia", JoinType.INNER);
        this.cdDep = agencia.get("cdDep");
        this.nmDep = agencia.get("nmDep");
        this.cdCsa = agencia.get("cdCsa");
        this.cdSureg = agencia.get("cdSureg");
        this.cdSuper = agencia.get("cdSuper");
        
    }

    public void atualiza(CapMci ap){
        init();
        em = new JPAUtil().getEntityManager();
        em.merge(ap);
        em.close();
    }
    
    public CapMci lerCliente(int mci){
        init();
        select = new ArrayList<Selection>();
        select.add(cdMci);
        select.add(rzSocial);
        select.add(cnpj);
        select.add(cdDep);
        select.add(nmDep);
        select.add(cdSureg);
        select.add(cdSuper);
        select.add(cdCsa);
        select.add(cdRestrito);
        select.add(tpPessoa);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdMci, mci));
        CapMci aux = new CapMci();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            aux = null;
//        }catch(Exception e){
//            aux = new CapMci();
        }
        em.close();
        return aux;
    }

}
