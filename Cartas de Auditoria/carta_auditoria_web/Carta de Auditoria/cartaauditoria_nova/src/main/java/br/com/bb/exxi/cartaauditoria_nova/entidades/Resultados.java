/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "resultados", schema = "cartaauditoria")
public class Resultados implements Serializable{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResultadosPK resultadosPK;
    @Column(name = "nm_prd")
    private String nmPrd;
    
    @Transient
    private Integer cdPrd;

    public Resultados() {
    }

    public Resultados(Integer cdPrd, String nmPrd) {
        this.resultadosPK = new ResultadosPK(cdPrd);
        this.nmPrd = nmPrd;
    }

    public ResultadosPK getResultadosPK() {
        return resultadosPK;
    }

    public void setResultadosPK(ResultadosPK resultadosPK) {
        this.resultadosPK = resultadosPK;
    }

    public String getNmPrd() {
        return nmPrd;
    }

    public void setNmPrd(String nmPrd) {
        this.nmPrd = nmPrd;
    }

    public Integer getCdPrd() {
        setCdPrd(resultadosPK.getCdPrd());
        return cdPrd;
    }

    public void setCdPrd(Integer cdPrd) {
        this.cdPrd = cdPrd;
    }
    
}


