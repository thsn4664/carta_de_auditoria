/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author F9066619
 */
@ManagedBean
@SessionScoped
public class FileBean {
    private StreamedContent file;
    private StreamedContent file2;
    public FileBean() throws FileNotFoundException {
        InputStream stream = this.getClass().getResourceAsStream("/docs/produtos_incluidos.pdf");
        file = new DefaultStreamedContent(stream, "application/pdf", "produtos_incluidos.pdf");
        stream = this.getClass().getResourceAsStream("/docs/produtos_a_incluir.pdf");
        file2 = new DefaultStreamedContent(stream, "application/pdf", "produtos_a_incluir.pdf");
    }
    public StreamedContent getFile() {
        return file;
    }
    public StreamedContent getFile2() {
        return file2;
    }

}
