/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AcessoLog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
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
public class AcessoLogDAO {
    
    private CriteriaBuilder cb;
    private CriteriaQuery<AcessoLog> q;
    private Root<AcessoLog> root;
	
    private Path id;
    private Path matricula;
    private Path cdDep;
    private Path dt;
    private Join gestor;
    private Path matrGestor;
    private Path nmGestor;

    private List<Selection> select;
	
    private EntityManager em;
	
    public void init(){
        
    	em = new JPAUtil().getEntityManager();
    	cb = em.getCriteriaBuilder();
        q = cb.createQuery(AcessoLog.class) ;
        root = q.from(AcessoLog.class) ;

        this.id = root.get("id");
        this.matricula = root.get("matricula");
        this.cdDep = root.get("cdDep");
        this.dt = root.get("dt");
        this.gestor = root.join("gestor",JoinType.INNER);
        this.matrGestor = gestor.get("matricula");
        this.nmGestor = gestor.get("nmFunci");
        
    }

    public void atualiza(AcessoLog ap){
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            st.execute("insert into cartaauditoria.acesso_log (matricula, cod_pref_dep, mat_gestor, dt) values ('"
                    + ap.getMatricula() + "'," + String.valueOf(ap.getCdDep()) + ",'" + ap.getGestor().getMatricula() + "','"
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ap.getDt()) + "');");
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(AcessoLogDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<AcessoLog> listaLog(String m){
        init();
        select = new ArrayList<Selection>();
        select.add(matricula);
        select.add(cdDep);
        select.add(dt);
        select.add(matrGestor);
        select.add(nmGestor);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(matricula, m));
        List<AcessoLog> lista = new ArrayList<AcessoLog>();
//        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            lista = em.createQuery(q).getResultList();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaLog!");
//            e.printStackTrace();
//        }
        em.close();
        return lista;
    }

}
