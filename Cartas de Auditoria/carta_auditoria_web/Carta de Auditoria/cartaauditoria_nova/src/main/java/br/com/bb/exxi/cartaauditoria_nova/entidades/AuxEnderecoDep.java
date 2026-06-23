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
@Table(name = "endereco_dep_atacado", schema = "cartaauditoria")
public class AuxEnderecoDep implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_pref_dep")
    private Integer cdPrefDep;
    
    @Column(name = "dv_pref_dep")
    private String dvPrefDep;
    @Column(name = "nm_dependencia")
    private String nmDep;
    @Column(name = "cep")
    private Integer nrCep;
    @Column(name = "endereco")
    private String txLgr;
    @Column(name = "complemento")
    private String txCmpt;
    @Column(name = "bairro")
    private String txBai;
    @Column(name = "municipio")
    private String txMun;
    @Column(name = "uf")
    private String txUf;

    public AuxEnderecoDep() {
    }

    public AuxEnderecoDep(Integer cdPrefDep, String dvPrefDep, String nmDep, Integer nrCep, String txLgr, String txCmpt, String txBai, String txMun, String txUf) {
        this.cdPrefDep = cdPrefDep;
        this.dvPrefDep = dvPrefDep;
        this.nmDep = nmDep;
        this.nrCep = nrCep;
        this.txLgr = txLgr;
        this.txCmpt = txCmpt;
        this.txBai = txBai;
        this.txMun = txMun;
        this.txUf = txUf;
    }

    public Integer getCdPrefDep() {
        return cdPrefDep;
    }

    public void setCdPrefDep(Integer cdPrefDep) {
        this.cdPrefDep = cdPrefDep;
    }

    public String getDvPrefDep() {
        return dvPrefDep;
    }

    public void setDvPrefDep(String dvPrefDep) {
        this.dvPrefDep = dvPrefDep;
    }

    public String getNmDep() {
        return nmDep;
    }

    public void setNmDep(String nmDep) {
        this.nmDep = nmDep;
    }

    public Integer getNrCep() {
        return nrCep;
    }

    public void setNrCep(Integer nrCep) {
        this.nrCep = nrCep;
    }

    public String getTxLgr() {
        return txLgr;
    }

    public void setTxLgr(String txLgr) {
        this.txLgr = txLgr;
    }

    public String getTxBai() {
        return txBai;
    }

    public void setTxBai(String txBai) {
        this.txBai = txBai;
    }

    public String getTxCmpt() {
        return txCmpt;
    }

    public void setTxCmpt(String txCmpt) {
        this.txCmpt = txCmpt;
    }

    public String getTxMun() {
        return txMun;
    }

    public void setTxMun(String txMun) {
        this.txMun = txMun;
    }

    public String getTxUf() {
        return txUf;
    }

    public void setTxUf(String txUf) {
        this.txUf = txUf;
    }

}
