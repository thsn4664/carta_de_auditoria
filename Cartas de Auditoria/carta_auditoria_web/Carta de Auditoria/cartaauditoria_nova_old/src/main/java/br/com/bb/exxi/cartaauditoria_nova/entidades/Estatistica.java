/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

/**
 *
 * @author F9066619
 */
public class Estatistica {
    
    private Integer cdPrf;
    private String nmPrf;
    private Integer vlDemandasWorkflow;
    private Integer vlCartasOnline;
    private Integer vlCartasOffline;
    private Integer vlCartasTotal;
    private Double percCartasOnline;
    private Double percCartasOffline;
    private Double percTotalCartas;
    private Double percUtilizacao;

    public Estatistica() {
        this.vlDemandasWorkflow = 0;
        this.vlCartasTotal = 0;
        this.vlCartasOffline = 0;
        atualizar();
    }

    private void atualizar(){
        this.vlCartasOnline = this.vlCartasTotal - this.vlCartasOffline;
        if(this.vlCartasTotal > 0){
            this.percCartasOnline = (this.vlCartasOnline*1.0 / this.vlCartasTotal);
            this.percCartasOffline = (this.vlCartasOffline*1.0 / this.vlCartasTotal);
        }else{
            this.percCartasOnline = 0.0;
            this.percCartasOffline = 0.0;
        }
        if(vlDemandasWorkflow > 0){
            this.percUtilizacao = (this.vlCartasTotal*1.0 / this.vlDemandasWorkflow);
        }else{
            this.percUtilizacao = 0.0;
        }
    }

    public Integer getCdPrf() {
        return cdPrf;
    }

    public void setCdPrf(Integer cdPrf) {
        this.cdPrf = cdPrf;
    }

    public String getNmPrf() {
        return nmPrf;
    }

    public void setNmPrf(String nmPrf) {
        this.nmPrf = nmPrf;
    }

    public Integer getVlDemandasWorkflow() {
        return vlDemandasWorkflow;
    }

    public void setVlDemandasWorkflow(Integer vlDemandasWorkflow) {
        this.vlDemandasWorkflow = vlDemandasWorkflow;
        atualizar();
    }

    public Integer getVlCartasOnline() {
        return vlCartasOnline;
    }

//    public void setVlCartasOnline(Integer vlCartasOnline) {
//        this.vlCartasOnline = vlCartasOnline;
//    }
//
    public Integer getVlCartasOffline() {
        return vlCartasOffline;
    }

    public void setVlCartasOffline(Integer vlCartasOffline) {
        this.vlCartasOffline = vlCartasOffline;
        atualizar();
    }

    public Integer getVlCartasTotal() {
        return vlCartasTotal;
    }

    public void setVlCartasTotal(Integer vlCartasTotal) {
        this.vlCartasTotal = vlCartasTotal;
        atualizar();
    }

    public Double getPercCartasOnline() {
        return percCartasOnline;
    }

//    public void setPercCartasOnline(Double percCartasOnline) {
//        this.percCartasOnline = percCartasOnline;
//    }
//
    public Double getPercCartasOffline() {
        return percCartasOffline;
    }

//    public void setPercCartasOffline(Double percCartasOffline) {
//        this.percCartasOffline = percCartasOffline;
//    }
//
    public Double getPercTotalCartas() {
        return percTotalCartas;
    }

    public void setPercTotalCartas(Double percTotalCartas) {
        this.percTotalCartas = percTotalCartas;
    }

    public Double getPercUtilizacao() {
        return percUtilizacao;
    }

//    public void setPercUtilizacao(Double percUtilizacao) {
//        this.percUtilizacao = percUtilizacao;
//    }
    
}
