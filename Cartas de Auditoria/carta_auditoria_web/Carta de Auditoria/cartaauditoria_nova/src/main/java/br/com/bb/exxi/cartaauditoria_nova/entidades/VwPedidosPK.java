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
public class VwPedidosPK implements Serializable{
    
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_cli")
    private Integer cdCli;

    public VwPedidosPK() {
    }

    public VwPedidosPK(Date dtBase, Integer cdCli) {
        this.dtBase = dtBase;
        this.cdCli = cdCli;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.dtBase != null ? this.dtBase.hashCode() : 0);
        hash = 53 * hash + (this.cdCli != null ? this.cdCli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VwPedidosPK other = (VwPedidosPK) obj;
        if (this.dtBase != other.dtBase && (this.dtBase == null || !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        if (this.cdCli != other.cdCli && (this.cdCli == null || !this.cdCli.equals(other.cdCli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VwPedidosPK{" + "dtBase=" + dtBase + ", cdCli=" + cdCli + "}";
    }

}
