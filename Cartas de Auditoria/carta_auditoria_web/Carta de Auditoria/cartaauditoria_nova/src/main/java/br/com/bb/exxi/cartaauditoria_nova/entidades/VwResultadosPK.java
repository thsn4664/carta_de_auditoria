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
public class VwResultadosPK implements Serializable{
    
    @Column(name = "dt_base")
    @Temporal(TemporalType.DATE)
    private Date dtBase;
    @Column(name = "cd_cli")
    private Integer cdCli;
    @Column(name = "cd_prd")
    private Integer cdPrd;
    @Column(name = "cd_mdld")
    private Integer cdMdld;
    @Column(name = "cd_depe")
    private Integer cdDepe;
    @Column(name = "nm_ctr")
    private String nmCtr;

    public VwResultadosPK() {
    }

    public VwResultadosPK(Date dtBase, Integer cdCli, Integer cdPrd, Integer cdMdld, Integer cdDepe, String nmCtr) {
        this.dtBase = dtBase;
        this.cdCli = cdCli;
        this.cdPrd = cdPrd;
        this.cdMdld = cdMdld;
        this.cdDepe = cdDepe;
        this.nmCtr = nmCtr;
    }

    public VwResultadosPK(Integer cdMdld, Integer cdDepe, String nmCtr) {
        this.cdMdld = cdMdld;
        this.cdDepe = cdDepe;
        this.nmCtr = nmCtr;
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

    public Integer getCdMdld() {
        return cdMdld;
    }

    public void setCdMdld(Integer cdMdld) {
        this.cdMdld = cdMdld;
    }

    public Integer getCdDepe() {
        return cdDepe;
    }

    public void setCdDepe(Integer cdDepe) {
        this.cdDepe = cdDepe;
    }

    public String getNmCtr() {
        return nmCtr;
    }

    public void setNmCtr(String nmCtr) {
        this.nmCtr = nmCtr;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.dtBase != null ? this.dtBase.hashCode() : 0);
        hash = 53 * hash + (this.cdCli != null ? this.cdCli.hashCode() : 0);
        hash = 53 * hash + (this.cdPrd != null ? this.cdPrd.hashCode() : 0);
        hash = 53 * hash + (this.cdMdld != null ? this.cdMdld.hashCode() : 0);
        hash = 53 * hash + (this.cdDepe != null ? this.cdDepe.hashCode() : 0);
        hash = 53 * hash + (this.nmCtr != null ? this.nmCtr.hashCode() : 0);
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
        final VwResultadosPK other = (VwResultadosPK) obj;
        if (this.dtBase != other.dtBase && (this.dtBase == null || !this.dtBase.equals(other.dtBase))) {
            return false;
        }
        if (this.cdCli != other.cdCli && (this.cdCli == null || !this.cdCli.equals(other.cdCli))) {
            return false;
        }
        if (this.cdPrd != other.cdPrd && (this.cdPrd == null || !this.cdPrd.equals(other.cdPrd))) {
            return false;
        }
        if (this.cdMdld != other.cdMdld && (this.cdMdld == null || !this.cdMdld.equals(other.cdMdld))) {
            return false;
        }
        if (this.cdDepe != other.cdDepe && (this.cdDepe == null || !this.cdDepe.equals(other.cdDepe))) {
            return false;
        }
        if ((this.nmCtr == null) ? (other.nmCtr != null) : !this.nmCtr.equals(other.nmCtr)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VwResultadosPK{" + "dtBase=" + dtBase + ", cdCli=" + cdCli + ", cdPrd=" + cdPrd + ", cdMdld=" + cdMdld + ", cdDepe=" + cdDepe + ", nmCtr=" + nmCtr + '}';
    }

}
