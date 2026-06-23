/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

import br.com.bb.exxi.cartaauditoria_nova.dao.AcessoDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AcessoLogDAO;
import br.com.bb.exxi.cartaauditoria_nova.dao.AuxFuncionarioAtacadoDAO;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Acesso;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AcessoLog;
import br.com.bb.exxi.cartaauditoria_nova.entidades.AuxFuncionarioAtacado;
//import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author F9066619
 */
@ManagedBean
@ViewScoped
public class AcessoBean {

    private AuxFuncionarioAtacado gestor;
    private List<AuxFuncionarioAtacado> assessores;
    private AuxFuncionarioAtacado funci;
    private List<AcessoLog> logs;

    @PostConstruct
    public void init() {        
        assessores = new ArrayList<AuxFuncionarioAtacado>();
    }

    //public void autenticacao(Usuario u) {
    public void autenticacao(AuxFuncionarioAtacado f) {
//        if (u != null) {
        if (f != null) {
//            gestor = new AuxFuncionarioAtacadoDAO().lerFunci(u.getChave());
            gestor = f;
//            if (gestor != null) {
                assessores = new AuxFuncionarioAtacadoDAO().listaFunci(gestor.getDependencia().getCdDep(), "ASS", "");
                for (Acesso a : new AcessoDAO().listaAcesso(gestor.getDependencia().getCdDep())) {
                    for (int i = 0; i < assessores.size(); i++) {
                        if (assessores.get(i).getMatricula().equals(a.getMatricula())) {
                            assessores.get(i).setAcessoOk(true);
                        }
                    }
                }
//            }
        }
    }

    public void autorizacao(AuxFuncionarioAtacado f) {
        Acesso acesso = new Acesso();
        for (int i = 0; i < assessores.size(); i++) {
            if (assessores.get(i).getMatricula().equals(f.getMatricula())) {
                if (assessores.get(i).isAcessoOk()) {
                    new AcessoDAO().apagaAcesso(f.getMatricula());
                    assessores.get(i).setAcessoOk(false);
                } else {
                    acesso.setCdDep(gestor.getDependencia().getCdDep());
                    acesso.setMatricula(f.getMatricula());
                    acesso.setGestor(gestor);
                    new AcessoDAO().gravaAcesso(acesso);
                    assessores.get(i).setAcessoOk(true);
                }
            }
        }
    }

    public void leLog(String matricula){
            funci = new AuxFuncionarioAtacadoDAO().lerFunci(matricula);
            logs = new AcessoLogDAO().listaLog(matricula);
    }
    
    /* GETTERS AND SETTERS */
    public AuxFuncionarioAtacado getGestor() {
        return gestor;
    }

    public void setGestor(AuxFuncionarioAtacado gestor) {
        this.gestor = gestor;
    }

    public List<AuxFuncionarioAtacado> getAssessores() {
        return assessores;
    }

    public void setAssessores(List<AuxFuncionarioAtacado> assessores) {
        this.assessores = assessores;
    }

    public AuxFuncionarioAtacado getFunci() {
        return funci;
    }

    public void setFunci(AuxFuncionarioAtacado funci) {
        this.funci = funci;
    }

    public List<AcessoLog> getLogs() {
        return logs;
    }

    public void setLogs(List<AcessoLog> logs) {
        this.logs = logs;
    }
}
