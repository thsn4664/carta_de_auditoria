/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "clientes_atacado_endereco_sede", schema = "cartaauditoria")
public class ClientesEndereco implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cd_cli")
    private Integer cdCli;

    @Column(name = "nr_cep")
    private Integer nrCep;
    @Column(name = "tx_lgr")
    private String txLgr;
    @Column(name = "tx_bai")
    private String txBai;
    @Column(name = "tx_cmpt")
    private String txCmpt;
    
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "cd_mun", referencedColumnName = "cd_mun")
    @ManyToOne
    private Municipios municipio;

    public ClientesEndereco() {
    }

    public ClientesEndereco(Integer nrCep, String txLgr, String txBai, String txCmpt, String nmMun, String sgUf) {
        this.nrCep = nrCep;
        this.txLgr = txLgr;
        this.txBai = txBai;
        this.txCmpt = txCmpt;
        this.municipio = new Municipios(nmMun, sgUf);
    }

    public Integer getCdCli() {
        return cdCli;
    }

    public void setCdCli(Integer cdCli) {
        this.cdCli = cdCli;
    }

    public Integer getNrCep() {
        return nrCep;
    }

    public void setNrCep(Integer nrCep) {
        this.nrCep = nrCep;
    }

    public String getTxLgr() {
        return txLgr;
    }

    public void setTxLgr(String txLgr) {
        this.txLgr = txLgr;
    }

    public String getTxBai() {
        return txBai;
    }

    public void setTxBai(String txBai) {
        this.txBai = txBai;
    }

    public String getTxCmpt() {
        return txCmpt;
    }

    public void setTxCmpt(String txCmpt) {
        this.txCmpt = txCmpt;
    }

    public Municipios getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipios municipio) {
        this.municipio = municipio;
    }
    
}
