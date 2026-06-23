/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.ClientesEndereco;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class ClientesEnderecoDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<ClientesEndereco> q;
    private Root<ClientesEndereco> root;
	
    private Path cdCli;
    private Path nrCep;
    private Path txLgr;
    private Path txBai;
    private Path txCmpt;
    
    private Join municipio;
    private Path nmMun;
    private Path sgUf;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(ClientesEndereco.class) ;
        root = q.from(ClientesEndereco.class) ;

        this.cdCli = root.get("cdCli");
        this.nrCep = root.get("nrCep");
        this.txLgr = root.get("txLgr");
        this.txBai = root.get("txBai");
        this.txCmpt = root.get("txCmpt");
        
        this.municipio = root.join("municipio",JoinType.INNER);
        this.nmMun = municipio.get("nmMun");
        this.sgUf = municipio.get("sgUf");
        
    }
    
    public ClientesEndereco leEndereco(Integer mci){
        init();
        select = new ArrayList<Selection>();
        select.add(nrCep);
        select.add(txLgr);
        select.add(txBai);
        select.add(txCmpt);
        select.add(nmMun);
        select.add(sgUf);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, mci));
        ClientesEndereco aux = new ClientesEndereco();
        try{
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.distinct(true);
            aux = em.createQuery(q).getSingleResult();
        }catch(NonUniqueResultException nure){
            List<ClientesEndereco> auxLista = em.createQuery(q).getResultList();
            aux = auxLista.get(0);
        }catch(NoResultException nre){
            aux = null;
        }catch(Exception e){
            System.out.println("Erro ao executar lerFunci!");
            e.printStackTrace();
        }
        em.close();
        return aux;
        
    }

    
}
