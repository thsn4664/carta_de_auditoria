/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "atualizacoes", schema = "cartaauditoria")
public class Atualizacoes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "nm_acao")
    private String nmAcao;
    @Column(name = "ts_ult_exec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsUltExec;
    @Column(name = "ts_px_exec")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tsPxExec;

    public Atualizacoes() {
    }

    public Atualizacoes(String nmAcao) {
        this.nmAcao = nmAcao;
    }

    public Atualizacoes(String nmAcao, Date tsUltExec, Date tsPxExec) {
        this.nmAcao = nmAcao;
        this.tsUltExec = tsUltExec;
        this.tsPxExec = tsPxExec;
    }

    public String getNmAcao() {
        return nmAcao;
    }

    public void setNmAcao(String nmAcao) {
        this.nmAcao = nmAcao;
    }

    public Date getTsUltExec() {
        return tsUltExec;
    }

    public void setTsUltExec(Date tsUltExec) {
        this.tsUltExec = tsUltExec;
    }

    public Date getTsPxExec() {
        return tsPxExec;
    }

    public void setTsPxExec(Date tsPxExec) {
        this.tsPxExec = tsPxExec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nmAcao != null ? nmAcao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atualizacoes)) {
            return false;
        }
        Atualizacoes other = (Atualizacoes) object;
        if ((this.nmAcao == null && other.nmAcao != null) || (this.nmAcao != null && !this.nmAcao.equals(other.nmAcao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.bb.exxi.cartaauditoria.entidade.Atualizacoes[ nmAcao=" + nmAcao + " ]";
    }
    
}
