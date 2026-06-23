/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Resultados;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class ResultadosDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<Resultados> q;
    private Root<Resultados> root;
    //private Join resultadosPK;
    private Path dtBase;
    private Path cdCli;
    private Path cdPrd;
    private Path nmPrd;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Resultados.class);
        root = q.from(Resultados.class);

//        this.resultadosPK = root.join("resultadosPK");
//        this.dtBase = resultadosPK.get("dtBase");
//        this.cdCli = resultadosPK.get("cdCli");
//        this.cdPrd = resultadosPK.get("cdPrd");
        this.dtBase = root.get("resultadosPK").get("dtBase");
        this.cdCli = root.get("resultadosPK").get("cdCli");
        this.cdPrd = root.get("resultadosPK").get("cdPrd");
        this.nmPrd = root.get("nmPrd");
    }
    
    public List<Resultados> listaProdutos(PedidosPK pk){
        init();
        select = new ArrayList<Selection>();
        select.add(cdPrd);
        select.add(nmPrd);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdCli, pk.getCdCli()));
        predicados.add(cb.equal(dtBase, pk.getDtBase()));
        List<Order> ordem = new ArrayList<Order>();
        ordem.add(cb.asc(cdPrd));
        List<Resultados> lista = new ArrayList<Resultados>();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            q.orderBy(ordem.toArray(new Order[]{}));
            q.distinct(true);
            lista = em.createQuery(q).getResultList();
        }catch(NoResultException nre){
            lista = new ArrayList<Resultados>();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
        }
        em.close();
        return lista;
    }

    public Date menorDataBase() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select min(dt_base) as min_dt_base from cartaauditoria.resultados;");
            Date aux = null;
            if(rs.next()){
                aux = rs.getDate("min_dt_base");
            }
            st.close();
            bd3.close();
            return aux;
        } catch (Exception ex) {
            Logger.getLogger(ResultadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
