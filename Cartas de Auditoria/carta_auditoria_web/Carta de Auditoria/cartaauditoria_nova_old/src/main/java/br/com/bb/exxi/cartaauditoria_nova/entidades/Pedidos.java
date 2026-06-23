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

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "pedidos", schema = "cartaauditoria")
public class Pedidos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PedidosPK pedidosPK;
    @Column(name = "dt_ped")
    @Temporal(TemporalType.DATE)
    private Date dtPed;
    @Column(name = "nm_arq_up")
    private String nmArqUp;
    @Column(name = "nm_arq_dw")
    private String nmArqDw;
    @Column(name = "cd_cnpj")
    private Integer cdCnpj;
    @Column(name = "nm_cli")
    private String nmCli;
    @Column(name = "cd_status")
    private Integer cdStatus;
    @Column(name = "cd_carta")
    private Integer cdCarta;
    @Column(name = "cd_outorgado")
    private boolean cdOutorgado;
    @Column(name = "cd_grupo")
    private Integer cdGrupo;

    public Pedidos() {
    }

    public Pedidos(PedidosPK pedidosPK) {
        this.pedidosPK = pedidosPK;
    }

    public Pedidos(PedidosPK pedidosPK, Date dtPed, Integer cdCarta) {
        this.pedidosPK = pedidosPK;
        this.dtPed = dtPed;
        this.cdCarta = cdCarta;
    }

    public Pedidos(Integer cdPrf, int cdCli, Date dtBase) {
        this.pedidosPK = new PedidosPK(cdPrf, cdCli, dtBase);
    }
    
    public Pedidos(Integer cdPrf, int cdCli, Date dtBase, boolean cdOutorgado) {
        this.pedidosPK = new PedidosPK(cdPrf, cdCli, dtBase);
        this.cdOutorgado = cdOutorgado;
    }
    
    public Pedidos(Integer cdPrf, int cdCli, Date dtBase, Integer cdStatus){
        this.pedidosPK = new PedidosPK(cdPrf, cdCli, dtBase);
        this.cdStatus = cdStatus;
    }

    public Pedidos(Integer cdPrf, int cdCli, Date dtBase, Integer cdStatus, Integer cdCarta, boolean cdOutorgado){
        this.pedidosPK = new PedidosPK(cdPrf, cdCli, dtBase);
        this.cdStatus = cdStatus;
        this.cdCarta = cdCarta;
        this.cdOutorgado = cdOutorgado;
    }

    public PedidosPK getPedidosPK() {
        return pedidosPK;
    }

    public void setPedidosPK(PedidosPK pedidosPK) {
        this.pedidosPK = pedidosPK;
    }

    public Date getDtPed() {
        return dtPed;
    }

    public void setDtPed(Date dtPed) {
        this.dtPed = dtPed;
    }

    public String getNmArqUp() {
        return nmArqUp;
    }

    public void setNmArqUp(String nmArqUp) {
        this.nmArqUp = nmArqUp;
    }

    public String getNmArqDw() {
        return nmArqDw;
    }

    public void setNmArqDw(String nmArqDw) {
        this.nmArqDw = nmArqDw;
    }

    public Integer getCdCnpj() {
        return cdCnpj;
    }

    public void setCdCnpj(Integer cdCnpj) {
        this.cdCnpj = cdCnpj;
    }

    public String getNmCli() {
        return nmCli;
    }

    public void setNmCli(String nmCli) {
        this.nmCli = nmCli;
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

    public boolean isCdOutorgado() {
        return cdOutorgado;
    }

    public void setCdOutorgado(boolean cdOutorgado) {
        this.cdOutorgado = cdOutorgado;
    }

    public Integer getCdGrupo() {
        return cdGrupo;
    }

    public void setCdGrupo(Integer cdGrupo) {
        this.cdGrupo = cdGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidosPK != null ? pedidosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
        if ((this.pedidosPK == null && other.pedidosPK != null) || (this.pedidosPK != null && !this.pedidosPK.equals(other.pedidosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria_nova.entidades.Pedidos[ pedidosPK=" + pedidosPK + " ]";
    }
    
}
