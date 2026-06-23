/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author F9066619
 */
@Entity
@Table(name = "lista_municipios", schema = "arg")
public class Municipios implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cd_mun")
    private Integer cdMun;
    @Column(name = "nm_mun")
    private String nmMun;
    @Column(name = "sg_uf")
    private String sgUf;

    public Municipios() {
    }

    public Municipios(String nmMun, String sgUf) {
        this.nmMun = nmMun;
        this.sgUf = sgUf;
    }

    public Integer getCdMun() {
        return cdMun;
    }

    public void setCdMun(Integer cdMun) {
        this.cdMun = cdMun;
    }

    public String getNmMun() {
        return nmMun;
    }

    public void setNmMun(String nmMun) {
        this.nmMun = nmMun;
    }

    public String getSgUf() {
        return sgUf;
    }

    public void setSgUf(String sgUf) {
        this.sgUf = sgUf;
    }
    
}
