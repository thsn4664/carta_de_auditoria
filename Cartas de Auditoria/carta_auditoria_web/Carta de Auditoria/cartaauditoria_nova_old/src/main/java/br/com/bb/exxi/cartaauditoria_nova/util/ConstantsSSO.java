/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.util;

/**
 *
 * @author F9066619
 */
public class ConstantsSSO {

        public static String PROTOCOLO = "https";
        public static final String PROTOCOLO_LOGIN = "https";
        public static final String DOMINIO_LOGIN = "login.intranet.bb.com.br";
        public static String PORTA = "443";
        public static String NOME_COOKIE_SSO = "BBSSOToken";
        public static String ssoToken = "";
        public static String NOME_COOKIE_ACR = "ssoacr";
        public static String URL_LOGIN = PROTOCOLO_LOGIN + "://" + DOMINIO_LOGIN + "/distAuth/UI/Login?ForceAuth=True&goto=";
        public static final String SERVIDOR_SSO_PADRAO = "sso.intranet.bb.com.br";
        public static final String SERVIDOR_SSO_DISEM = "http://disem2.intranet.bb.com.br:3027/sso";


        private ConstantsSSO() {
                throw new AssertionError();
        }
        
        public static void setSSOToken(String token){
            ssoToken = token;
        }
        
        
}


