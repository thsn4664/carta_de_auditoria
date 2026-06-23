/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.util;

import java.util.Date;

/**
 *
 * @author F9066619
 */
public class DataFormatada {
    
    private Date dtNormal;
    private String dtForm;

    public DataFormatada() {
    }

    public DataFormatada(Date dtNormal, String dtForm) {
        this.dtNormal = dtNormal;
        this.dtForm = dtForm;
    }
    
    public Date getDtNormal() {
        return dtNormal;
    }

    public void setDtNormal(Date dtNormal) {
        this.dtNormal = dtNormal;
    }

    public String getDtForm() {
        return dtForm;
    }

    public void setDtForm(String dtForm) {
        this.dtForm = dtForm;
    }
}
