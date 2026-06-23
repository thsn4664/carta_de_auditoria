/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.CapMciGrupos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Pedidos;
import br.com.bb.exxi.cartaauditoria_nova.entidades.VwPedidos;
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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class PedidosDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<Pedidos> q;
    private Root<Pedidos> root;
    private Path pedidosPK;
    private Path cdPrf;
    private Path cdCli;
    private Path dtBase;
    private Path dtPed;
    private Path nmArqUp;
    private Path nmArqDw;
    private Path cdCnpj;
    private Path nmCli;
    private Path cdStatus;
    private Path cdCarta;
    private Path cdOutorgado;
    private Path cdGrupo;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Pedidos.class);
        root = q.from(Pedidos.class);

        this.cdPrf = root.get("pedidosPK").get("cdPrf");
        this.cdCli = root.get("pedidosPK").get("cdCli");
        this.dtBase = root.get("pedidosPK").get("dtBase");
        this.dtPed = root.get("dtPed");
        this.nmArqUp = root.get("nmArqUp");
        this.nmArqDw = root.get("nmArqDw");
        this.cdCnpj = root.get("cdCnpj");
        this.nmCli = root.get("nmCli");
        this.cdStatus = root.get("cdStatus");
        this.cdCarta = root.get("cdCarta");
        this.cdOutorgado = root.get("cdOutorgado");
        this.cdGrupo = root.get("cdGrupo");

    }
    
    public List<Pedidos> listaPedidosAProcessar(int prefDep){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrf);
        select.add(cdCli);
        select.add(dtBase);
        select.add(cdStatus);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdPrf, prefDep));
        predicados.add(cb.or(cb.equal(cdStatus, 0),cb.equal(cdStatus, 1)));
        List<Pedidos> lista = new ArrayList<Pedidos>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Pedidos>();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
        }
        em.close();
        return lista;
    }
    
    public List<VwPedidos> listaPedidos(int prefDep){
        em = new JPAUtil().getEntityManager();
        CriteriaBuilder cb1 = em.getCriteriaBuilder();
        CriteriaQuery q1 = cb1.createQuery(VwPedidos.class);
        Root root1 = q1.from(VwPedidos.class);

        this.dtBase = root1.get("vwPedidosPK").get("dtBase");
        this.cdCli = root1.get("vwPedidosPK").get("cdCli");
        this.cdPrf = root1.get("cdPrf");
        this.dtPed = root1.get("dtPed");
        this.cdStatus = root1.get("cdStatusPed");
        this.cdCarta = root1.get("cdWorkflow");
        Path dtEnvio = root1.get("dtEnvio");
        Path dtReceb = root1.get("dtReceb");
        Path cdStatusArq = root1.get("cdStatusArq");

        select = new ArrayList<Selection>();
        select.add(dtBase);
        select.add(cdCli);
        select.add(cdPrf);
        select.add(dtPed);
        select.add(cdStatus);
        select.add(cdCarta);
        select.add(dtEnvio);
        select.add(dtReceb);
        select.add(cdStatusArq);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb1.equal(cdPrf, prefDep));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb1.asc(dtPed));
        ordem.add(cb1.asc(cdCarta));
        ordem.add(cb1.asc(cdCli));
        ordem.add(cb1.asc(dtBase));
        List<VwPedidos> lista = new ArrayList<VwPedidos>();
        try {
            q1.multiselect(select.toArray(new Selection[]{}));
            q1.where(predicados.toArray(new Predicate[]{}));
            q1.orderBy(ordem.toArray(new Order[]{}));
            lista = em.createQuery(q1).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<VwPedidos>();
        }
        em.close();
        return lista;
    }
    
    public Pedidos lePedido(int pcdCli, Date pdtBase){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrf);
        select.add(cdCli);
        select.add(dtBase);
        select.add(cdStatus);
        select.add(cdCarta);
        select.add(cdOutorgado);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pcdCli));
        predicados.add(cb.equal(dtBase, pdtBase));
        Pedidos aux = new Pedidos();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            aux = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            aux = new Pedidos();
        }
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
//        }
        em.close();
        return aux;
    }
    
    public List<Pedidos> listaPedidosLote(Integer pCdRef, Date pdtBase, Integer pTipoLote){
        init();
        
        Root root2 = q.from(CapMciGrupos.class);
        Path cdCli2 = root2.get("cdMci");
        Path cnpj = root2.get("cnpj");
        Path ancora = root2.get("ancora");
        
        select = new ArrayList<Selection>();
        select.add(cdPrf);
        select.add(cdCli);
        select.add(dtBase);
        select.add(cdStatus);
        select.add(cdCarta);
        select.add(cdOutorgado);
        List<Predicate> predicados = new ArrayList<Predicate>();
        if(pTipoLote == 1){
            predicados.add(cb.equal(cdCnpj, pCdRef));
        }else{
            predicados.add(cb.equal(cdGrupo, pCdRef));
        }
        predicados.add(cb.equal(dtBase, pdtBase));
        predicados.add(cb.equal(cdCli, cdCli2));
        List<Expression<?>> grupo = new ArrayList<Expression<?>>();
        grupo.add(cdPrf);
        grupo.add(cdCli);
        grupo.add(dtBase);
        grupo.add(cdStatus);
        grupo.add(cdCarta);
        grupo.add(cdOutorgado);
        grupo.add(ancora);
        grupo.add(cnpj);
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.desc(ancora));
        ordem.add(cb.asc(cnpj));
        List<Pedidos> aux = new ArrayList<Pedidos>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.groupBy(grupo);
            q.orderBy(ordem.toArray(new Order[]{}));
            aux = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            aux = new ArrayList<Pedidos>();
        }
        em.close();
        return aux;
    }
    
    public void adiciona(Pedidos p){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "";
            String sqlInsert = "insert into cartaauditoria.pedidos (cd_prf, cd_cli, dt_base, cd_outorgado";
            String sqlValues = ") values (" + String.valueOf(p.getPedidosPK().getCdPrf()) + ", "
                        + String.valueOf(p.getPedidosPK().getCdCli()) + ", '"
                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "', "
                        + (p.isCdOutorgado() ? "TRUE" : "FALSE");
            if(p.getCdCarta() != null){
                sqlInsert += ", cd_carta";
                sqlValues = sqlValues + ", " + String.valueOf(p.getCdCarta()).replace("L", "");
            }
            if(p.getCdCnpj() != null){
                sqlInsert += ", cd_cnpj";
                sqlValues = sqlValues + ", " + String.valueOf(p.getCdCnpj());
            }
            if(p.getCdGrupo() != null){
                sqlInsert += ", cd_grupo";
                sqlValues = sqlValues + ", " + String.valueOf(p.getCdGrupo());
            }
//            if(p.getCdCarta() != null && p.getCdCnpj() != null){
//                sql = "insert into cartaauditoria.pedidos (cd_prf, cd_cli, dt_base, cd_cnpj, cd_carta, cd_outorgado) values (" + String.valueOf(p.getPedidosPK().getCdPrf()) + ", "
//                        + String.valueOf(p.getPedidosPK().getCdCli()) + ", '"
//                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "', "
//                        + String.valueOf(p.getCdCnpj()) + ", "
//                        + String.valueOf(p.getCdCarta()) + ", "
//                        + (p.isCdOutorgado() ? "TRUE" : "FALSE") + ");";
//            }else if(p.getCdCnpj() != null){
//                sql = "insert into cartaauditoria.pedidos (cd_prf, cd_cli, dt_base, cd_cnpj, cd_outorgado) values (" + String.valueOf(p.getPedidosPK().getCdPrf()) + ", "
//                        + String.valueOf(p.getPedidosPK().getCdCli()) + ", '"
//                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "', "
//                        + String.valueOf(p.getCdCnpj()) + ", "
//                        + (p.isCdOutorgado() ? "TRUE" : "FALSE") + ");";
//            }else if(p.getCdCarta() != null){
//                sql = "insert into cartaauditoria.pedidos (cd_prf, cd_cli, dt_base, cd_carta, cd_outorgado) values (" + String.valueOf(p.getPedidosPK().getCdPrf()) + ", "
//                        + String.valueOf(p.getPedidosPK().getCdCli()) + ", '"
//                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "', " + String.valueOf(p.getCdCarta()) + ", "
//                        + (p.isCdOutorgado() ? "TRUE" : "FALSE") + ");";
//            }else{
//                sql = "insert into cartaauditoria.pedidos (cd_prf, cd_cli, dt_base, cd_outorgado) values (" + String.valueOf(p.getPedidosPK().getCdPrf()) + ", " 
//                        + String.valueOf(p.getPedidosPK().getCdCli()) + ", '"
//                        + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "', "
//                        + (p.isCdOutorgado() ? "TRUE" : "FALSE")+ ");";
//            }
            sql = sqlInsert + sqlValues + ");";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atualiza(Pedidos p){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "update cartaauditoria.pedidos set cd_prf = " + String.valueOf(p.getPedidosPK().getCdPrf()) 
                + ", cd_carta = " + String.valueOf(p.getCdCarta()).replace("L", "")
                + ", cd_outorgado = " + (p.isCdOutorgado() ? "TRUE" : "FALSE");
            if(p.getCdCnpj() != null){
                sql = sql + ", cd_cnpj = " + String.valueOf(p.getCdCnpj());
            }
            if(p.getCdGrupo() != null){
                sql = sql + ", cd_grupo = " + String.valueOf(p.getCdGrupo());
            }
            sql = sql + " where cd_cli = " + String.valueOf(p.getPedidosPK().getCdCli())
                + " and dt_base = '" + new SimpleDateFormat("yyyy-MM-dd").format(p.getPedidosPK().getDtBase()) + "';";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean remove(VwPedidos p){
        boolean saida = true;
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "delete from cartaauditoria.pedidos where cd_cli = " + String.valueOf(p.getVwPedidosPK().getCdCli())
                + " and dt_base = '" + new SimpleDateFormat("yyyy-MM-dd").format(p.getVwPedidosPK().getDtBase()) + "';";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(PedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
            saida = false;
        }
        return saida;
    }
}
