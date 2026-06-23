/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "tbaux_funci_atacado", schema="cap")
public class AuxFuncionarioAtacado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "matricula")
    private String matricula;
    @Column(name = "nm_funcionario")
    private String nmFunci;
    @Column(name = "dt_nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtNasc;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "cod_pref_dep", referencedColumnName = "cod_pref_dep")
    @ManyToOne
    private AuxDependencia dependencia;

    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "cod_funcao", referencedColumnName = "cod_funcao")
    @ManyToOne
    private AuxFuncao funcao;

    @Transient
    private boolean acessoOk;

    public AuxFuncionarioAtacado() {
        this.acessoOk = false;
    }

    public AuxFuncionarioAtacado(String nmFunci) {
        super();
        this.nmFunci = nmFunci;
    }

    public AuxFuncionarioAtacado(String matricula, String nmFunci) {
        super();
        this.matricula = matricula;
        this.nmFunci = nmFunci;
    }

    public AuxFuncionarioAtacado(String matricula, String nmFunci, Date dtNasc, Integer cdDep, String nmDep, Integer idFuncao, String nmFuncao) {
        super();
        this.matricula = matricula;
        this.nmFunci = nmFunci;
        this.dtNasc = dtNasc;
        this.dependencia = new AuxDependencia(cdDep, nmDep);
        this.funcao = new AuxFuncao(idFuncao, nmFuncao);
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNmFunci() {
        return nmFunci;
    }

    public void setNmFunci(String nmFunci) {
        this.nmFunci = nmFunci;
    }

//    public String getTelefone() {
//        return telefone;
//    }
//
//    public void setTelefone(String telefone) {
//        this.telefone = telefone;
//    }
//
    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

//    public int getAtivo() {
//        return ativo;
//    }
//
//    public void setAtivo(int ativo) {
//        this.ativo = ativo;
//    }
//
    public AuxDependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(AuxDependencia dependencia) {
        this.dependencia = dependencia;
    }

    public AuxFuncao getFuncao() {
        return funcao;
    }

    public void setFuncao(AuxFuncao funcao) {
        this.funcao = funcao;
    }

    public boolean isAcessoOk() {
        return acessoOk;
    }

    public void setAcessoOk(boolean acessoOk) {
        this.acessoOk = acessoOk;
    }

}
