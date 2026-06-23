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
public class PedidosLotePK implements Serializable {
    @Column(name = "cd_ref")
    private Integer cdRef;
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_tipo_lote")
    private Integer cdTipoLote;

    public PedidosLotePK() {
    }

    public PedidosLotePK(Integer cdRef, Date dtBase, Integer cdTipoLote) {
        this.cdRef = cdRef;
        this.dtBase = dtBase;
        this.cdTipoLote = cdTipoLote;
    }

    public Integer getCdRef() {
        return cdRef;
    }

    public void setCdRef(Integer cdRef) {
        this.cdRef = cdRef;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public Integer getCdTipoLote() {
        return cdTipoLote;
    }

    public void setCdTipoLote(Integer cdTipoLote) {
        this.cdTipoLote = cdTipoLote;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.cdRef != null ? this.cdRef.hashCode() : 0);
        hash = 47 * hash + (this.dtBase != null ? this.dtBase.hashCode() : 0);
        hash = 47 * hash + (this.cdTipoLote != null ? this.cdTipoLote.hashCode() : 0);
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
        final PedidosLotePK other = (PedidosLotePK) obj;
        if (this.cdRef != other.cdRef && (this.cdRef == null || !this.cdRef.equals(other.cdRef))) {
            return false;
        }
        if (this.dtBase != other.dtBase && (this.dtBase == null || !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        if (this.cdTipoLote != other.cdTipoLote && (this.cdTipoLote == null || !this.cdTipoLote.equals(other.cdTipoLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PedidosLotePK{" + "cdRef=" + cdRef + ", dtBase=" + dtBase + ", cdTipoLote=" + cdTipoLote + '}';
    }

}
