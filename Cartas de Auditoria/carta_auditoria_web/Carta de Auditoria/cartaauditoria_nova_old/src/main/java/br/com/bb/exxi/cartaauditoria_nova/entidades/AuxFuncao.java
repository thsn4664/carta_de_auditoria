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
@Table(name = "tbaux_funcao", schema="cap")
public class AuxFuncao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_funcao")
    private Integer id;
    @Column(name = "nm_funcao")
    private String nmFuncao;

    public AuxFuncao() {
    }

    public AuxFuncao(Integer id, String nmFuncao) {
        this.id = id;
        this.nmFuncao = nmFuncao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNmFuncao() {
        return nmFuncao;
    }

    public void setNmFuncao(String nmFuncao) {
        this.nmFuncao = nmFuncao;
    }

    
}
