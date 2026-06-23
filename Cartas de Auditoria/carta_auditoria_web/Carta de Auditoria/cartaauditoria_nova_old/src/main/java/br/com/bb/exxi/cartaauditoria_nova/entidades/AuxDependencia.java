/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "tb_dependencias_atacado", schema="cap")
public class AuxDependencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_pref_dep")
    private Integer cdDep;
    @Column(name = "nm_dependencia")
    private String nmDep;
    @Column(name = "pref_super")
    private Integer cdSuper;
    @Column(name = "cod_regional")
    private Integer cdSureg;
    @Column(name = "cod_csa")
    private Integer cdCsa;
    @Column(name = "cod_tp_dependencia")
    private int cdTpDep;

    public AuxDependencia() {
    }

    public AuxDependencia(Integer cdDep, String nmDep) {
        super();
        this.cdDep = cdDep;
        this.nmDep = nmDep;
    }

    public AuxDependencia(Integer cdDep, String nmDep, Integer cdCsa) {
        super();
        this.cdDep = cdDep;
        this.nmDep = nmDep;
        this.cdCsa = cdCsa;
    }

    public AuxDependencia(Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa, int cdTpDep) {
        super();
        this.cdDep = cdDep;
        this.nmDep = nmDep;
        this.cdSuper = cdSuper;
        this.cdSureg = cdSureg;
        this.cdCsa = cdCsa;
        this.cdTpDep = cdTpDep;
    }

    public AuxDependencia(Integer cdDep, String nmDep, Integer cdSuper, Integer cdSureg, Integer cdCsa) {
        super();
        this.cdDep = cdDep;
        this.nmDep = nmDep;
        this.cdSuper = cdSuper;
        this.cdSureg = cdSureg;
        this.cdCsa = cdCsa;
    }

    public Integer getCdDep() {
        return cdDep;
    }

    public void setCdDep(Integer cdDep) {
        this.cdDep = cdDep;
    }

    public String getNmDep() {
        return nmDep;
    }

    public void setNmDep(String nmDep) {
        this.nmDep = nmDep;
    }

    public Integer getCdSuper() {
        return cdSuper;
    }

    public void setCdSuper(Integer cdSuper) {
        this.cdSuper = cdSuper;
    }

    public Integer getCdSureg() {
        return cdSureg;
    }

    public void setCdSureg(Integer cdSureg) {
        this.cdSureg = cdSureg;
    }

    public Integer getCdCsa() {
        return cdCsa;
    }

    public void setCdCsa(Integer cdCsa) {
        this.cdCsa = cdCsa;
    }

    public int getCdTpDep() {
        return cdTpDep;
    }

    public void setCdTpDep(int cdTpDep) {
        this.cdTpDep = cdTpDep;
    }

}
