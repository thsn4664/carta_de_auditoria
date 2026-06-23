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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "pedidos_lote", schema = "cartaauditoria")
public class PedidosLote implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PedidosLotePK pedidosLotePK;
    @Column(name = "cd_prf")
    private Integer cdPrf;
    @Column(name = "dt_ped")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtPed;
    @Column(name = "dt_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEnvio;
    @Column(name = "dt_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtRetorno;
    @Column(name = "cd_status")
    private Integer cdStatus;
    @Column(name = "cd_carta")
    private Integer cdCarta;
    @Column(name = "nm_grupo")
    private String nmGrupo;
    
    @Transient
    private String strTipoLote;
    private String strRef;

    public PedidosLote() {
    }

    public PedidosLote(PedidosLotePK pedidosLotePK) {
        this.pedidosLotePK = pedidosLotePK;
    }

    public PedidosLote(PedidosLotePK pedidosLotePK, Integer cdPrf, Date dtPed, Integer cdCarta) {
        this.pedidosLotePK = pedidosLotePK;
        this.cdPrf = cdPrf;
        this.dtPed = dtPed;
        this.cdCarta = cdCarta;
    }

    public PedidosLote(Integer cdRef, Date dtBase, Integer cdTipoLote) {
        this.pedidosLotePK = new PedidosLotePK(cdRef, dtBase, cdTipoLote);
    }
    
    public PedidosLote(Integer cdPrf, Integer cdRef, Date dtBase, Integer cdTipoLote,  Integer cdStatus){
        this.pedidosLotePK = new PedidosLotePK(cdRef, dtBase, cdTipoLote);
        this.cdPrf = cdPrf;
        this.cdStatus = cdStatus;
    }

    public PedidosLote(Integer cdPrf, Integer cdRef, Date dtBase, Integer cdTipoLote, Integer cdStatus, Integer cdCarta){
        this.pedidosLotePK = new PedidosLotePK(cdRef, dtBase, cdTipoLote);
        this.cdPrf = cdPrf;
        this.cdStatus = cdStatus;
        this.cdCarta = cdCarta;
    }

    public PedidosLote(Integer cdPrf, Integer cdRef, Date dtBase, Integer cdTipoLote, Date dtPed, Date dtEnvio, Date dtReceb, Integer cdStatus, Integer cdCarta, String nmGrupo){
        this.pedidosLotePK = new PedidosLotePK(cdRef, dtBase, cdTipoLote);
        this.cdPrf = cdPrf;
        this.cdStatus = cdStatus;
        this.cdCarta = cdCarta;
        this.dtPed = dtPed;
        this.dtEnvio = dtEnvio;
        this.dtRetorno = dtReceb;
        this.nmGrupo = nmGrupo;
    }

    public PedidosLotePK getPedidosLotePK() {
        return pedidosLotePK;
    }

    public void setPedidosLotePK(PedidosLotePK pedidosLotePK) {
        this.pedidosLotePK = pedidosLotePK;
    }

    public Date getDtPed() {
        return dtPed;
    }

    public void setDtPed(Date dtPed) {
        this.dtPed = dtPed;
    }

    public Integer getCdStatus() {
        return cdStatus;
    }

    public void setCdStatus(Integer cdStatus) {
        this.cdStatus = cdStatus;
    }

    public Integer getCdCarta() {
        return cdCarta;
    }

    public void setCdCarta(Integer cdCarta) {
        this.cdCarta = cdCarta;
    }

    public Integer getCdPrf() {
        return cdPrf;
    }

    public void setCdPrf(Integer cdPrf) {
        this.cdPrf = cdPrf;
    }

    public Date getDtEnvio() {
        return dtEnvio;
    }

    public void setDtEnvio(Date dtEnvio) {
        this.dtEnvio = dtEnvio;
    }

    public Date getDtRetorno() {
        return dtRetorno;
    }

    public void setDtRetorno(Date dtRetorno) {
        this.dtRetorno = dtRetorno;
    }

    public String getNmGrupo() {
        return nmGrupo;
    }

    public void setNmGrupo(String nmGrupo) {
        this.nmGrupo = nmGrupo;
    }

    public String getStrTipoLote() {
        if(pedidosLotePK.getCdTipoLote() == 1){
            return "Radical CNPJ";
        }else{
            return "Grupo";
        }
    }

    public String getStrRef() {
        if(pedidosLotePK.getCdTipoLote() == 1){
            return String.valueOf(pedidosLotePK.getCdRef());
        }else{
            return nmGrupo;
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidosLotePK != null ? pedidosLotePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PedidosLote)) {
            return false;
        }
        PedidosLote other = (PedidosLote) object;
        if ((this.pedidosLotePK == null && other.pedidosLotePK != null) || (this.pedidosLotePK != null && !this.pedidosLotePK.equals(other.pedidosLotePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria_nova.entidades.Pedidos[ pedidosLotePK=" + pedidosLotePK + " ]";
    }
    
}
