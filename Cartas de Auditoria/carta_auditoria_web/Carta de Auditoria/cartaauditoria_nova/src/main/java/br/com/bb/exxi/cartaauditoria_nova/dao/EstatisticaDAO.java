/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.dao;

import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEstatFunci;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxEstatistica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author F9066619
 */
public class EstatisticaDAO {

    public List<AuxEstatistica> listaWorkFlow() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao2.conexao, ConstantesConexao2.userLogin, ConstantesConexao2.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select WF.cod_csa, count(WF.cod_demanda) as qtde_demanda from (select D.*,PS.cod_servico from workflow_csa.tb_demanda D join"
                    + " workflow_csa.tb_processo_servico PS on D.cod_processo_servico=PS.cod_processo_servico where (D.data_status >= '2014-03-24'"
                    + " and D.data_status < current_date) and D.cod_status_demanda=4 and PS.cod_servico in (22,137)) as WF group by WF.cod_csa;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cod_csa"), rs.getInt("qtde_demanda")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatistica> listaWorkFlow30() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao2.conexao, ConstantesConexao2.userLogin, ConstantesConexao2.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select WF.cod_csa, count(WF.cod_demanda) as qtde_demanda from (select D.*,PS.cod_servico from workflow_csa.tb_demanda D join"
                    + " workflow_csa.tb_processo_servico PS on D.cod_processo_servico=PS.cod_processo_servico where (D.data_status >= (current_date - 30)"
                    + " and D.data_status < current_date) and D.cod_status_demanda=4 and PS.cod_servico in (22,137)) as WF group by WF.cod_csa;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cod_csa"), rs.getInt("qtde_demanda")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatistica> listaMake() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select v.*, d.nm_dependencia from cartaauditoria.vw_estat_make v join cap.tb_dependencias_atacado d on v.cd_prf=d.cod_pref_dep;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cd_prf"), rs.getString("nm_dependencia"), rs.getInt("qtde")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatistica> listaMake30() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select v.*, d.nm_dependencia from cartaauditoria.vw_estat_make_30 v join cap.tb_dependencias_atacado d on v.cd_prf=d.cod_pref_dep;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cd_prf"), rs.getString("nm_dependencia"), rs.getInt("qtde")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatistica> listaAdd() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select v.*, d.nm_dependencia from cartaauditoria.vw_estat_add v join cap.tb_dependencias_atacado d on v.cd_prf=d.cod_pref_dep;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cd_prf"), rs.getString("nm_dependencia"), rs.getInt("qtde")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatistica> listaAdd30() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select v.*, d.nm_dependencia from cartaauditoria.vw_estat_add_30 v join cap.tb_dependencias_atacado d on v.cd_prf=d.cod_pref_dep;");
            List<AuxEstatistica> lista = new ArrayList<AuxEstatistica>();
            while(rs.next()){
                lista.add(new AuxEstatistica(rs.getInt("cd_prf"), rs.getString("nm_dependencia"), rs.getInt("qtde")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<AuxEstatFunci> listaFunci() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select * from cartaauditoria.vw_estat_funci;");
            List<AuxEstatFunci> lista = new ArrayList<AuxEstatFunci>();
            while(rs.next()){
                lista.add(new AuxEstatFunci(rs.getString("nm_chave"), rs.getString("nome"), rs.getString("cargo"), rs.getInt("prefixo"), rs.getInt("qtde_add"),
                        rs.getInt("qtde_del"), rs.getInt("qtde_look"), rs.getInt("qtde_make"), rs.getInt("qtde_err"), rs.getInt("qtde_no_access"), rs.getInt("qtde_fora")));
            }
            st.close();
            bd3.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public AuxEstatFunci sumListaFunci() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection bd3 = DriverManager.getConnection(ConstantesConexao.conexao, ConstantesConexao.userLogin, ConstantesConexao.userPassword);
            Statement st = bd3.createStatement();
            ResultSet rs = st.executeQuery("select sum(qtde_add) as sum_add, sum(qtde_del) as sum_del, sum(qtde_look) as sum_look, sum(qtde_make) as sum_make,"
                    + " sum(qtde_err) as sum_err, sum(qtde_no_access) as sum_no_access, sum(qtde_fora) as sum_fora from cartaauditoria.vw_estat_funci;");
            AuxEstatFunci aux = null;
            if(rs.next()){
                aux = new AuxEstatFunci(rs.getInt("sum_add"), rs.getInt("sum_del"), rs.getInt("sum_look"), rs.getInt("sum_make"), rs.getInt("sum_err"),
                        rs.getInt("sum_no_access"), rs.getInt("sum_fora"));
            }
            st.close();
            bd3.close();
            return aux;
        } catch (Exception ex) {
            Logger.getLogger(EstatisticaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
