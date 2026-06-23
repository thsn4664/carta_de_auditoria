/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.jdbc_base.SQLs;
import br.com.bb.exxi.cartaauditoria_nova.jdbc_base.Sas_libraries;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Outorgados;
import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class OutorgadosDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<Outorgados> q;
    private Root<Outorgados> root;
    private Path dtBase;
    private Path cdCli;
    private Path cdCliOtgdo;
    private Path nmOtgdo;
    private Path tpPessoa;
    private Path cpfCnpjOtgdo;
    private Path sumTipoDoc;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Outorgados.class);
        root = q.from(Outorgados.class);

        this.dtBase = root.get("dtBase");
        this.cdCli = root.get("cdCli");
        this.cdCliOtgdo = root.get("cdCliOtgdo");
        this.nmOtgdo = root.get("nmOtgdo");
        this.tpPessoa = root.get("tpPessoa");
        this.cpfCnpjOtgdo = root.get("cpfCnpjOtgdo");
        this.sumTipoDoc = root.get("sumTipoDoc");
    }
    
    public List<Outorgados> listaProcuradores(PedidosPK pk){
        init();
        select = new ArrayList<Selection>();
        select.add(dtBase);
        select.add(cdCli);
        select.add(cdCliOtgdo);
        select.add(nmOtgdo);
        select.add(tpPessoa);
        select.add(cpfCnpjOtgdo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        predicados.add(cb.greaterThan(sumTipoDoc, 0));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmOtgdo));
        List<Outorgados> lista = new ArrayList<Outorgados>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Outorgados>();
        }
        em.close();
        return lista;
    }
    
    public List<Outorgados> listaSaldoExtrato(PedidosPK pk){
        init();
        select = new ArrayList<Selection>();
        select.add(dtBase);
        select.add(cdCli);
        select.add(cdCliOtgdo);
        select.add(nmOtgdo);
        select.add(tpPessoa);
        select.add(cpfCnpjOtgdo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        predicados.add(cb.equal(sumTipoDoc, 0));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmOtgdo));
        List<Outorgados> lista = new ArrayList<Outorgados>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Outorgados>();
        }
        em.close();
        return lista;
    }
    
    public List<Outorgados> listaOutorgados(PedidosPK pk){
        init();
        select = new ArrayList<Selection>();
        select.add(dtBase);
        select.add(cdCli);
        select.add(cdCliOtgdo);
        select.add(nmOtgdo);
        select.add(tpPessoa);
        select.add(cpfCnpjOtgdo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(nmOtgdo));
        List<Outorgados> lista = new ArrayList<Outorgados>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Outorgados>();
        }
        em.close();
        return lista;
    }
    
    public boolean testa(PedidosPK pk){
        List<Outorgados> lista = new ArrayList<Outorgados>();
        lista = listaOutorgados(pk);
        if(lista.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    public void grava(PedidosPK pk){
        /*String libname = SQLs.libname_mci;
        String sql = SQLs.SAS_carta_auditoria_outorgado;
        sql = sql.replace("$where01", String.valueOf(pk.getCdCli())); //passando parâmetro na execução...
        sql = sql.replace("$where02", new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH).format(pk.getDtBase())); //passando parâmetro na execução...
        */
        String sql = "";
        try {
            List<Outorgados> c1 = new Sas_libraries().executeQuery(pk.getCdCli(), sql, pk.getDtBase());
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            for(Outorgados c : c1){
                String sqle = "insert into cartaauditoria.outorgados (dt_base, cd_cli, cd_cli_otgdo, nm_otgdo, tp_pessoa, cpf_cnpj_otgdo, sum_tipo_doc) values (" + c.toString() + ");";
                st.execute(sqle);
            }
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(OutorgadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
