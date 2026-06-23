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
@Table(name = "tb_vw_resultados_03", schema = "cartaauditoria")
//@Table(name = "vw_resultados_03", schema = "cartaauditoria")
//@Table(name = "vw_resultados_03_alt", schema = "cartaauditoria") /*somente listas extensas*/
public class VwResultados implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VwResultadosPK vwResultadosPK;

    @Column(name = "nm_mdld")
    private String nmMdld;
    @Column(name = "vl_sld_1")
    private Double vlSld1;
    @Column(name = "vl_sld_2")
    private Double vlSld2;
    @Column(name = "vl_sld_3")
    private Double vlSld3;
    @Column(name = "vl_sld_4")
    private Double vlSld4;
    @Column(name = "cd_col_sld")
    private String cdColSld;
/*    @Column(name = "vl_encargos")
    private Double vlEncargos;*/

    public VwResultados() {
    }

    public VwResultados(VwResultadosPK vwResultadosPK) {
        this.vwResultadosPK = vwResultadosPK;
    }

    public VwResultados(Date dtBase, Integer cdCli, Integer cdPrd, Integer cdMdld, Integer cdDepe, String nmCtr) {
        this.vwResultadosPK = new VwResultadosPK(dtBase, cdCli, cdPrd, cdMdld, cdDepe, nmCtr);
    }

    public VwResultados(Integer cdMdld, String nmMdld, Integer cdDepe, String nmCtr, Double vlSld1, Double vlSld2, Double vlSld3, Double vlSld4, String cdColSld) {
        this.vwResultadosPK = new VwResultadosPK(cdMdld, cdDepe, nmCtr);
        this.nmMdld = nmMdld;
        this.vlSld1 = vlSld1;
        this.vlSld2 = vlSld2;
        this.vlSld3 = vlSld3;
        this.vlSld4 = vlSld4;
        this.cdColSld = cdColSld;
        /*this.vlEncargos = vlEncargos;*/
    }

    public VwResultadosPK getVwResultadosPK() {
        return vwResultadosPK;
    }

    public void setVwResultadosPK(VwResultadosPK vwResultadosPK) {
        this.vwResultadosPK = vwResultadosPK;
    }
    
    public String getNmMdld() {
        return nmMdld;
    }

    public void setNmMdld(String nmMdld) {
        this.nmMdld = nmMdld;
    }

    public Double getVlSld1() {
        return vlSld1;
    }

    public void setVlSld1(Double vlSld1) {
        this.vlSld1 = vlSld1;
    }

    public Double getVlSld2() {
        return vlSld2;
    }

    public void setVlSld2(Double vlSld2) {
        this.vlSld2 = vlSld2;
    }

    public Double getVlSld3() {
        return vlSld3;
    }

    public void setVlSld3(Double vlSld3) {
        this.vlSld3 = vlSld3;
    }

    public Double getVlSld4() {
        return vlSld4;
    }

    public void setVlSld4(Double vlSld4) {
        this.vlSld4 = vlSld4;
    }

    public String getCdColSld() {
        return cdColSld;
    }

    public void setCdColSld(String cdColSld) {
        this.cdColSld = cdColSld;
    }

/*    public Double getVlEncargos() {
        return vlEncargos;
    }

    public void setVlEncargos(Double vlEncargos) {
        this.vlEncargos = vlEncargos;
    }*/

}
