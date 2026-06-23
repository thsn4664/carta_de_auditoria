/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.filtros;

import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import br.com.bb.exxi.cartaauditoria_nova.util.ConstantsSSO;
import br.com.bb.exxi.cartaauditoria_nova.util.SSOUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Implementação de
 * <code>Servlet Filter</code> do Filtro de Segurança/Autenticador.
 *
 * <p>
 * Os filtros da API servlet são responsáveis por interceptar a chamada a certo
 * recurso, fazer algum processamento e liberar o acesso ou não. O Filtro de
 * Segurança é utilizado para interceptar as chamadas para os Servlet’s e JSP’s
 * de uma área restrita da aplicação.
 *
 * <p>
 * É nesta classe que se implementa o Enforcement Point (PEP) de políticas de
 * segurança, ou seja, a integração com o sistema de Gerenciamento de Segurança
 * Web Corporativo.
 *
 * <p>
 * Este filtro deve ser incluído nas configurações da aplicação, usualmente no
 * arquivo <tt>web.xml</tt>.
 *
 * <p>
 * Um exemplo seria:
 * <blockquote>
 * <pre>
 * <filter>
 * <filter-name>OpenSSO</filter-name>
 * <filter-class>br.com.bb.sso.api.filter.FiltroSeguranca</filter-class>
 * </filter>
 *
 * <filter-mapping>
 * <filter-name>OpenSSO</filter-name>
 * <url-pattern>/*</url-pattern>
 * </filter-mapping>
 * </pre>
 * </blockquote>
 *
 * @author Ditec/Diges
 * @author F0411579
 * @version 1.1, 06/09/12
 * @see br.com.bb.exxi.cartaauditoria.util.ConstantsSSO
 * @see br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
 * @see br.com.bb.exxi.cartaauditoria.util.SSOUtil;
 */
public class FiltroSeguranca implements Filter {

    public FiltroSeguranca() {
        // construtor
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // destrutor
    }

    /**
     * Faz o filtro verificando se o usuário está logado, e se não envia para o
     * autenticador. Após autenticado, popula a variavel usuario com os
     * atributos do usuário e insere na sessão. Na Direc, para manter a
     * compatibilidade com o autenticador antigo nas aplicações que utilizavam o
     * <code>UsuarioVO</code>, foi criada uma
     * <code>FacadeAutenticacao</code> para popular o UsuarioVO e adicionar a
     * variável
     * <code>funci</code> na sessão.
     *
     * @author Ditec/Diges
     * @author F0411579
     * @version 1.1, 06/09/12
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
            HttpServletRequest hsRequest = (HttpServletRequest) request;
            HttpSession session = hsRequest.getSession();
            Usuario usuario;
            usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                Cookie cookies[] = hsRequest.getCookies();
                SSOUtil ssoUtil = new SSOUtil();
                if (cookies != null) {
                    /*inicio teste de cookies*/
                    System.out.println("cookies");
                    Cookie cookie = null;
                    String cookieTxt = "";
                    for (int i = 0; i < cookies.length; i++) {
                        cookie = cookies[i];
                        cookieTxt = cookieTxt + cookie.getName( ) + ":  "+cookie.getValue( )+ ",  ";                           
                        System.out.println("cookie:"+cookies[i].getName( )+": "+cookies[i].getValue( )); 
                    }
                    //System.out.println("cookieTxt"+cookieTxt); 
                    /*fim teste de cookies*/                   
                    
                    String tokenId = ssoUtil.getCookieValue(cookies, ConstantsSSO.NOME_COOKIE_SSO);
                    ConstantsSSO.setSSOToken(tokenId);
                    
                    System.out.println("tokenId"+tokenId);
                    
                    if (tokenId == null) {
                        Logger.getLogger(FiltroSeguranca.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Não existe tokenId. - Filtro de Segurança");
                        enviaPaginaLogin((HttpServletResponse) response, hsRequest);
                    } else {
                        ssoUtil.setServidorSSO(ssoUtil.getCookieValue(cookies, ConstantsSSO.NOME_COOKIE_ACR));
                        usuario = ssoUtil.getAtributosUsuario(tokenId);
                        if (usuario == null) {
                            Logger.getLogger(FiltroSeguranca.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Não existe usuario. - Filtro de Segurança");
                            enviaPaginaLogin((HttpServletResponse) response, hsRequest);
                        }
                        session.setAttribute("usuario", usuario);
                        /* Popula o funci para manter a compatibilidade com as aplicações que liam o atributo <tt>funci</tt> */
                        //httpSession.setAttribute("funci", FacadeAutenticacao.toUsuarioVO(usuario) );
                    }
                } else {
                    Logger.getLogger(FiltroSeguranca.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Não existe Cookie SSO. - Filtro de Segurança");
                    enviaPaginaLogin((HttpServletResponse) response, hsRequest);
                }
            }
        }
        
        try {
            chain.doFilter(request, response);
        } catch (NullPointerException e) {
            Logger.getLogger(FiltroSeguranca.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Não foi possível recuperar os atributos do usuário. - Filtro de Segurança", e.getMessage());
            // enviaPaginaLogin((HttpServletResponse) response, httpServletRequest);
        } catch (Throwable t) {
            Logger.getLogger(FiltroSeguranca.class.getName()).log(Level.SEVERE, "Carta de Auditoria - Não foi possível recuperar os atributos do usuário. - Filtro de Segurança", t.getMessage());
        }

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // init
    }

    private void enviaPaginaLogin(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.sendRedirect(SSOUtil.getURL_LOGIN(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
