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
public class SwapPK implements Serializable {
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_cli")
    private int cdCli;
    @Column(name = "cd_prd")
    private int cdPrd;
    @Column(name = "nm_ctr")
    private String nmCtr;

    public SwapPK() {
    }

    public SwapPK(String nmCtr) {
        this.nmCtr = nmCtr;
    }

    public SwapPK(Date dtBase, int cdCli, int cdPrd, String nmCtr) {
        this.dtBase = dtBase;
        this.cdCli = cdCli;
        this.cdPrd = cdPrd;
        this.nmCtr = nmCtr;
    }

    public Date getDtBase() {
        return dtBase;
    }

    public void setDtBase(Date dtBase) {
        this.dtBase = dtBase;
    }

    public int getCdCli() {
        return cdCli;
    }

    public void setCdCli(int cdCli) {
        this.cdCli = cdCli;
    }

    public int getCdPrd() {
        return cdPrd;
    }

    public void setCdPrd(int cdPrd) {
        this.cdPrd = cdPrd;
    }

    public String getNmCtr() {
        return nmCtr;
    }

    public void setNmCtr(String nmCtr) {
        this.nmCtr = nmCtr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dtBase != null ? dtBase.hashCode() : 0);
        hash += (int) cdCli;
        hash += (int) cdPrd;
        hash += (nmCtr != null ? nmCtr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SwapPK)) {
            return false;
        }
        SwapPK other = (SwapPK) object;
        if ((this.dtBase == null && other.dtBase != null) || (this.dtBase != null && !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        if (this.cdCli != other.cdCli) {
            return false;
        }
        if (this.cdPrd != other.cdPrd) {
            return false;
        }
        if ((this.nmCtr == null && other.nmCtr != null) || (this.nmCtr != null && !this.nmCtr.equals(other.nmCtr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria.entidade.SwapPK[ dtBase=" + dtBase + ", cdCli=" + cdCli + ", cdPrd=" + cdPrd + ", nmCtr=" + nmCtr + " ]";
    }
    
}
