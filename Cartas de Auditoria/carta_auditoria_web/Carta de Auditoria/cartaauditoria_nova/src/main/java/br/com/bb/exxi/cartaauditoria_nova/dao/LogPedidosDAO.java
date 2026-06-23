/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.LogPedidos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 *
 * @author F9066619
 */
public class LogPedidosDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<LogPedidos> q;
    private Root<LogPedidos> root;
    private Path nmTipo;
    private Path cdPrf;
    private Path nmChave;
    private Path cdCli;
    private Path dtBase;
    private Path cdFlow;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(LogPedidos.class);
        root = q.from(LogPedidos.class);

        this.cdPrf = root.get("cdPrf");
        this.cdCli = root.get("cdCli");
        this.dtBase = root.get("dtBase");
        this.nmTipo = root.get("nmTipo");
        this.nmChave = root.get("nmChave");
        this.cdFlow = root.get("cdFlow");
    }

    public void adiciona(LogPedidos lp){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            String sql = "insert into cartaauditoria.logpedidos (nm_tipo, nm_chave, cd_prf, cd_cli, dt_base, cd_flow) values ('"
                    + lp.getNmTipo() + "', '" + lp.getNmChave() + "', " + String.valueOf(lp.getCdPrf()) + ", "
                    + String.valueOf(lp.getCdCli()) + ", '" + new SimpleDateFormat("yyyy-MM-dd").format(lp.getDtBase()) + "', "
                    + String.valueOf(lp.getCdFlow()).replace("L", "") + ");";
            st.execute(sql);
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(LogPedidosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
