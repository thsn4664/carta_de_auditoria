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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "acesso", schema="cartaauditoria")
public class Acesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "matricula")
    private String matricula;
    @Column(name = "cod_pref_dep")
    private int cdDep;
    @Column(name = "dt")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dt;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "mat_gestor", referencedColumnName = "matricula")
    @ManyToOne
    private AuxFuncionarioAtacado gestor;

    public Acesso() {
    }

    public Acesso(String matricula, int cdDep, Date dt, String matrGestor) {
        super();
        this.matricula = matricula;
        this.cdDep = cdDep;
        this.dt = dt;
        this.gestor = new AuxFuncionarioAtacado(matrGestor, "");
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getCdDep() {
        return cdDep;
    }

    public void setCdDep(int cdDep) {
        this.cdDep = cdDep;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public AuxFuncionarioAtacado getGestor() {
        return gestor;
    }

    public void setGestor(AuxFuncionarioAtacado gestor) {
        this.gestor = gestor;
    }

}
