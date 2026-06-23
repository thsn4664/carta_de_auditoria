/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.Acesso;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AcessoLog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
public class AcessoDAO {

    private CriteriaBuilder cb;
    private CriteriaQuery<Acesso> q;
    private Root<Acesso> root;
    private Path matricula;
    private Path cdDep;
    private Path dt;
    private Join gestor;
    private Path matrGestor;
    private List<Selection> select;
    private EntityManager em;

    public void init() {

        em = new JPAUtil().getEntityManager();
        cb = em.getCriteriaBuilder();
        q = cb.createQuery(Acesso.class);
        root = q.from(Acesso.class);

        this.matricula = root.get("matricula");
        this.cdDep = root.get("cdDep");
        this.dt = root.get("dt");
        this.gestor = root.join("gestor", JoinType.INNER);
        this.matrGestor = gestor.get("matricula");

    }

    public void atualiza(Acesso ap) {
//        init();
//        em = new JPAUtil().getEntityManager();
//        em.merge(ap);
//        em.close();
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select * from cartaauditoria.acesso where matricula = '"+ ap.getMatricula() +"';");
            if(rs.next()){
                st.execute("update cartaauditoria.acesso set cod_pref_dep = " + String.valueOf(ap.getCdDep()) + ", mat_gestor = '" + ap.getGestor().getMatricula()
                        + "', dt = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ap.getDt()) + "' where matricula = '"+ ap.getMatricula() +"';");
            }else{
                st.execute("insert into cartaauditoria.acesso (matricula, cod_pref_dep, mat_gestor, dt) values ('"
                        + ap.getMatricula() + "'," + String.valueOf(ap.getCdDep()) + ",'" + ap.getGestor().getMatricula() + "','"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ap.getDt()) + "');");
            }
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remove(Acesso ap) {
//        init();
//        em = new JPAUtil().getEntityManager();
//        em.remove(ap);
//        em.close();
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            st.execute("delete from cartaauditoria.acesso where matricula = '"
                    + ap.getMatricula() + "';");
            st.close();
            bd3.close();
        } catch (Exception ex) {
            Logger.getLogger(AcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Acesso leAcesso(String m) {
        init();
        select = new ArrayList<Selection>();
        select.add(matricula);
        select.add(cdDep);
        select.add(dt);
        select.add(matrGestor);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(matricula, m));
        Acesso auxAcesso = new Acesso();
        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            auxAcesso = em.createQuery(q).getSingleResult();
        }catch(NoResultException nre){
            auxAcesso = null;
//        } catch (Exception e) {
//            System.out.println("Erro ao executar leAcesso!");
//            e.printStackTrace();
        }
        em.close();
        return auxAcesso;
    }

    public List<Acesso> listaAcesso(int prefDep) {
        init();
        select = new ArrayList<Selection>();
        select.add(matricula);
        select.add(cdDep);
        select.add(dt);
        select.add(matrGestor);
        List<Predicate> predicados = new ArrayList<Predicate>();
        predicados.add(cb.equal(cdDep, prefDep));
        List<Acesso> lista = new ArrayList<Acesso>();
//        try {
            q.multiselect(select.toArray(new Selection[]{}));
            q.where(predicados.toArray(new Predicate[]{}));
            lista = em.createQuery(q).getResultList();
//        } catch (Exception e) {
//            System.out.println("Erro ao executar listaAcesso!");
//            e.printStackTrace();
//        }
        em.close();
        return lista;
    }

    public void gravaAcesso(Acesso a) {
        a.setGestor(new AuxFuncionarioAtacadoDAO().lerFunci(a.getGestor().getMatricula()));
        a.setDt(new Date());
        atualiza(a);
        
        AcessoLog al = new AcessoLog();
        al.setMatricula(a.getMatricula());
        al.setCdDep(a.getCdDep());
        al.setGestor(a.getGestor());
        al.setDt(new Date());
        new AcessoLogDAO().atualiza(al);
    }

    public void apagaAcesso(String m) {
        Acesso a = new AcessoDAO().leAcesso(m);
        if (a != null) {
            AcessoLog al = new AcessoLog();
            al.setMatricula(a.getMatricula());
            al.setCdDep(0);
            al.setGestor(a.getGestor());
            a.setDt(new Date());
            al.setDt(new Date());
            new AcessoLogDAO().atualiza(al);
            remove(a);
        }
    }
}
