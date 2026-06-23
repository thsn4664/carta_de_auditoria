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
public class PedidosPK implements Serializable {
    @Column(name = "cd_prf")
    private Integer cdPrf;
    @Column(name = "cd_cli")
    private int cdCli;
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;

    public PedidosPK() {
    }

    public PedidosPK(Integer cdPrf, int cdCli, Date dtBase) {
        this.cdPrf = cdPrf;
        this.cdCli = cdCli;
        this.dtBase = dtBase;
    }

    public Integer getCdPrf() {
        return cdPrf;
    }

    public void setCdPrf(Integer cdPrf) {
        this.cdPrf = cdPrf;
    }

    public int getCdCli() {
        return cdCli;
    }

    public void setCdCli(int cdCli) {
        this.cdCli = cdCli;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) cdPrf;
        hash += (int) cdCli;
        hash += (dtBase != null ? dtBase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidosPK)) {
            return false;
        }
        PedidosPK other = (PedidosPK) object;
        if (this.cdPrf != other.cdPrf) {
            return false;
        }
        if (this.cdCli != other.cdCli) {
            return false;
        }
        if ((this.dtBase == null && other.dtBase != null) || (this.dtBase != null && !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria_nova.entidades.PedidosPK[ cdPrf=" + cdPrf + ", cdCli=" + cdCli + ", dtBase=" + dtBase + " ]";
    }
    
}
