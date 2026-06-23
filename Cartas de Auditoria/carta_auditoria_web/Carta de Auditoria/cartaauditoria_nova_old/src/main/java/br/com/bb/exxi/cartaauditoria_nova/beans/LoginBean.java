/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.beans;

import br.com.bb.exxi.cartaauditoria_nova.util.SSOUtil;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author F9066619
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    @PostConstruct
    public void init() {
    }
    
        public void enviaPaginaLogin(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.sendRedirect(SSOUtil.getURL_LOGIN(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
