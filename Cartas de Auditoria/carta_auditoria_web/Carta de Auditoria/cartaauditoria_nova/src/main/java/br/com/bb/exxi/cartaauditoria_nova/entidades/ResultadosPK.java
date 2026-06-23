/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author F9066619
 */
@Embeddable
public class ResultadosPK implements Serializable{
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_cli")
    private Integer cdCli;
    @Column(name = "cd_prd")
    private Integer cdPrd;
    @Column(name = "nm_ctr")
    private String nmCtr;

    public ResultadosPK() {
    }

    public ResultadosPK(Integer cdPrd) {
        this.cdPrd = cdPrd;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public Integer getCdCli() {
        return cdCli;
    }

    public void setCdCli(Integer cdCli) {
        this.cdCli = cdCli;
    }

    public Integer getCdPrd() {
        return cdPrd;
    }

    public void setCdPrd(Integer cdPrd) {
        this.cdPrd = cdPrd;
    }

    public String getNmCtr() {
        return nmCtr;
    }

    public void setNmCtr(String nmCtr) {
        this.nmCtr = nmCtr;
    }
    
    
}
