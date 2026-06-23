/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name="logpedidos", schema = "cartaauditoria")
public class LogPedidos implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_log")
    private int id;

    @Column(name = "ts_log")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsLog;
    @Column(name = "nm_tipo")
    private String nmTipo;
    @Column(name = "cd_prf")
    private Integer cdPrf;
    @Column(name = "nm_chave")
    private String nmChave;
    @Column(name = "cd_cli")
    private Integer cdCli;
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_flow")
    private Integer cdFlow;
    
    @Transient
    private String nmDep;
    
    public LogPedidos(){
        nmDep = "";
    }

    public LogPedidos(Integer cdPrf, String nmDep, Date dtBase, Integer cdCli) {
        this.cdPrf = cdPrf;
        this.cdCli = cdCli;
        this.dtBase = dtBase;
        this.nmDep = nmDep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNmTipo() {
        return nmTipo;
    }

    public void setNmTipo(String nmTipo) {
        this.nmTipo = nmTipo;
    }

    public Integer getCdPrf() {
        return cdPrf;
    }

    public void setCdPrf(Integer cdPrf) {
        this.cdPrf = cdPrf;
    }

    public String getNmChave() {
        return nmChave;
    }

    public void setNmChave(String nmChave) {
        this.nmChave = nmChave;
    }

    public Integer getCdCli() {
        return cdCli;
    }

    public void setCdCli(Integer cdCli) {
        this.cdCli = cdCli;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public Integer getCdFlow() {
        return cdFlow;
    }

    public void setCdFlow(Integer cdFlow) {
        this.cdFlow = cdFlow;
    }

    public Date getTsLog() {
        return tsLog;
    }

    public void setTsLog(Date tsLog) {
        this.tsLog = tsLog;
    }

    public String getNmDep() {
        return nmDep;
    }

    public void setNmDep(String nmDep) {
        this.nmDep = nmDep;
    }
    
}
