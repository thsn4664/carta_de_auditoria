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
@Table(name = "vw_pedidos", schema = "cartaauditoria")
public class VwPedidos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VwPedidosPK vwPedidosPK;

    @Column(name = "cd_prf")
    private Integer cdPrf;
    @Column(name = "dt_ped")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtPed;
    @Column(name = "cd_status_ped")
    private Integer cdStatusPed;
    @Column(name = "cd_carta")
    private Integer cdWorkflow;
    @Column(name = "dt_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEnvio;
    @Column(name = "dt_rcb")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtReceb;
    @Column(name = "cd_status_arq")
    private Integer cdStatusArq;

    public VwPedidos() {
    }

    public VwPedidos(VwPedidosPK vwPedidosPK) {
        this.vwPedidosPK = vwPedidosPK;
    }

    public VwPedidos(VwPedidosPK vwPedidosPK, Integer cdPrf, Date dtPed, Integer cdStatusPed, Integer cdWorkflow, Date dtEnvio, Date dtReceb, Integer cdStatusArq) {
        this.vwPedidosPK = vwPedidosPK;
        this.cdPrf = cdPrf;
        this.dtPed = dtPed;
        this.cdStatusPed = cdStatusPed;
        this.cdWorkflow = cdWorkflow;
        this.dtEnvio = dtEnvio;
        this.dtReceb = dtReceb;
        this.cdStatusArq = cdStatusArq;
    }

    public VwPedidos(Date dtBase, Integer cdCli, Integer cdPrf, Date dtPed, Integer cdStatusPed, Integer cdWorkflow, Date dtEnvio, Date dtReceb, Integer cdStatusArq) {
        this.vwPedidosPK =  new VwPedidosPK(dtBase,cdCli);
        this.cdPrf = cdPrf;
        this.dtPed = dtPed;
        this.cdStatusPed = cdStatusPed;
        this.cdWorkflow = cdWorkflow;
        this.dtEnvio = dtEnvio;
        this.dtReceb = dtReceb;
        this.cdStatusArq = cdStatusArq;
    }

    public VwPedidosPK getVwPedidosPK() {
        return vwPedidosPK;
    }

    public void setVwPedidosPK(VwPedidosPK vwPedidosPK) {
        this.vwPedidosPK = vwPedidosPK;
    }

    public Integer getCdPrf() {
        return cdPrf;
    }

    public void setCdPrf(Integer cdPrf) {
        this.cdPrf = cdPrf;
    }

    public Date getDtPed() {
        return dtPed;
    }

    public void setDtPed(Date dtPed) {
        this.dtPed = dtPed;
    }

    public Integer getCdStatusPed() {
        return cdStatusPed;
    }

    public void setCdStatusPed(Integer cdStatusPed) {
        this.cdStatusPed = cdStatusPed;
    }

    public Integer getCdWorkflow() {
        return cdWorkflow;
    }

    public void setCdWorkflow(Integer cdWorkflow) {
        this.cdWorkflow = cdWorkflow;
    }

    public Date getDtEnvio() {
        return dtEnvio;
    }

    public void setDtEnvio(Date dtEnvio) {
        this.dtEnvio = dtEnvio;
    }

    public Date getDtReceb() {
        return dtReceb;
    }

    public void setDtReceb(Date dtReceb) {
        this.dtReceb = dtReceb;
    }

    public Integer getCdStatusArq() {
        return cdStatusArq;
    }

    public void setCdStatusArq(Integer cdStatusArq) {
        this.cdStatusArq = cdStatusArq;
    }
    
}
