/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "vw_swap", schema = "cartaauditoria")
public class Swap implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SwapPK swapPK;
    @Column(name = "cd_mdld")
    private Integer cdMdld;
    @Column(name = "nm_mdld")
    private String nmMdld;
    @Column(name = "cd_depe")
    private Integer cdDepe;
    @Column(name = "vl_contratado")
    private Double vlContratado;
    @Column(name = "vl_mtm")
    private Double vlMtm;
    @Column(name = "vl_atualizado")
    private Double vlAtualizado;

    public Swap() {
    }

    public Swap(SwapPK swapPK) {
        this.swapPK = swapPK;
    }

    public Swap(Date dtBase, int cdCli, int cdPrd, String nmCtr) {
        this.swapPK = new SwapPK(dtBase, cdCli, cdPrd, nmCtr);
    }

    public Swap(Integer cdMdld, String nmMdld, Integer cdDepe, String nmCtr, Double vlContratado, Double vlMtm, Double vlAtualizado) {
        this.swapPK = new SwapPK(nmCtr);
        this.cdMdld = cdMdld;
        this.nmMdld = nmMdld;
        this.cdDepe = cdDepe;
        this.vlContratado = vlContratado;
        this.vlMtm = vlMtm;
        this.vlAtualizado = vlAtualizado;
    }

    public SwapPK getSwapPK() {
        return swapPK;
    }

    public void setSwapPK(SwapPK swapPK) {
        this.swapPK = swapPK;
    }

    public Double getVlContratado() {
        return vlContratado;
    }

    public void setVlContratado(Double vlContratado) {
        this.vlContratado = vlContratado;
    }

    public Double getVlMtm() {
        return vlMtm;
    }

    public void setVlMtm(Double vlMtm) {
        this.vlMtm = vlMtm;
    }

    public Double getVlAtualizado() {
        return vlAtualizado;
    }

    public void setVlAtualizado(Double vlAtualizado) {
        this.vlAtualizado = vlAtualizado;
    }

    public Integer getCdMdld() {
        return cdMdld;
    }

    public void setCdMdld(Integer cdMdld) {
        this.cdMdld = cdMdld;
    }

    public String getNmMdld() {
        return nmMdld;
    }

    public void setNmMdld(String nmMdld) {
        this.nmMdld = nmMdld;
    }

    public Integer getCdDepe() {
        return cdDepe;
    }

    public void setCdDepe(Integer cdDepe) {
        this.cdDepe = cdDepe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (swapPK != null ? swapPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Swap)) {
            return false;
        }
        Swap other = (Swap) object;
        if ((this.swapPK == null && other.swapPK != null) || (this.swapPK != null && !this.swapPK.equals(other.swapPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria_nova.entidades.Swap[ swapPK=" + swapPK + " ]";
    }
    
}
