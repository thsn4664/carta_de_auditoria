/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosLote;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class PedidosLoteDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<PedidosLote> q;
    private Root<PedidosLote> root;
//    private Path pedidosLotePK;
    private Path cdPrf;
    private Path cdRef;
    private Path dtBase;
    private Path cdTipoLote;
    private Path dtPed;
    private Path dtEnvio;
    private Path dtReceb;
    private Path cdStatus;
    private Path cdCarta;
    private Path nmGrupo;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(PedidosLote.class);
        root = q.from(PedidosLote.class);

        this.cdRef = root.get("pedidosLotePK").get("cdRef");
        this.dtBase = root.get("pedidosLotePK").get("dtBase");
        this.cdTipoLote = root.get("pedidosLotePK").get("cdTipoLote");
        this.cdPrf = root.get("cdPrf");
        this.dtPed = root.get("dtPed");
        this.dtEnvio = root.get("dtEnvio");
        this.dtReceb = root.get("dtRetorno");
        this.cdStatus = root.get("cdStatus");
        this.cdCarta = root.get("cdCarta");
        this.nmGrupo = root.get("nmGrupo");

    }
    
    public List<PedidosLote> listaPedidos(int prefDep){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrf);
        select.add(cdRef);
        select.add(dtBase);
        select.add(cdTipoLote);
        select.add(dtPed);
        select.add(dtEnvio);
        select.add(dtReceb);
        select.add(cdStatus);
        select.add(cdCarta);
        select.add(nmGrupo);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdPrf, prefDep));
        List<PedidosLote> lista = new ArrayList<PedidosLote>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<PedidosLote>();
        }
        em.close();
        return lista;
    }
    
    public PedidosLote lePedido(Integer pCdRef, Date pdtBase, Integer pcdTipoLote){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrf);
        select.add(cdRef);
        select.add(dtBase);
        select.add(cdTipoLote);
        select.add(cdStatus);
        select.add(cdCarta);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdRef, pCdRef));
        predicados.add(cb.equal(dtBase, pdtBase));
        predicados.add(cb.equal(cdTipoLote, pcdTipoLote));
        PedidosLote aux = new PedidosLote();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            aux = new PedidosLote();
        }
        em.close();
        return aux;
    }
    
    public void adiciona(PedidosLote p){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sqlInsert = "insert into cartaauditoria.pedidos_lote (cd_prf, cd_ref, dt_base, cd_tipo_lote, cd_status";
            String sqlValues = ") values (" + String.valueOf(p.getCdPrf()) + ", "
                        + String.valueOf(p.getPedidosLotePK().getCdRef()) + ", '"
                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosLotePK().getDtBase()) + "', "
                        + String.valueOf(p.getPedidosLotePK().getCdTipoLote())+ ", "
                        + String.valueOf(p.getCdStatus());
            if(p.getCdCarta() != null){
                sqlInsert = sqlInsert + ", cd_carta";
                sqlValues = sqlValues + ", " + String.valueOf(p.getCdCarta()).replace("L", "");
            }
            if(p.getNmGrupo() != null){
                sqlInsert = sqlInsert + ", nm_grupo";
                sqlValues = sqlValues + ", '" + p.getNmGrupo() + "'";
            }
            if(p.getCdStatus() == 2){
                sqlInsert = sqlInsert + ", dt_retorno";
                sqlValues = sqlValues + ", '" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + "'";
            }
            String sql = sqlInsert + sqlValues + ");";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosLoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualiza(PedidosLote p){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "update cartaauditoria.pedidos_lote set cd_prf = " + String.valueOf(p.getCdPrf()) 
                + ", cd_carta = " + String.valueOf(p.getCdCarta()).replace("L", "")
                + " where cd_ref = " + String.valueOf(p.getPedidosLotePK().getCdRef())
                + " and dt_base = '" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosLotePK().getDtBase())
                + "' and cd_tipo_lote = " + String.valueOf(p.getPedidosLotePK().getCdTipoLote()) + ";";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosLoteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean remove(PedidosLote p){
        boolean saida = true;
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "delete from cartaauditoria.pedidos_lote where cd_ref = " + String.valueOf(p.getPedidosLotePK().getCdRef())
                + " and dt_base = '" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosLotePK().getDtBase())
                + "' and cd_tipo_lote = " + String.valueOf(p.getPedidosLotePK().getCdTipoLote()) + ";";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosLoteDAO.class.getName()).log(Level.SEVERE, null, ex);
            saida = false;
        }
        return saida;
    }
}
