/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.jdbc_base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ConsultaDB2_old {

    public String getAPIDB2MySQLConnection_old(String BBSSOToken, String query) {
        HttpsURLConnection conn = null;
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            

            conn = (HttpsURLConnection) new URL("https://imobpj.intranet.bb.com.br:8443/consultaDB2/api?action=Consulta").openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(4000);
            conn.setRequestProperty("Content-Type", "application/json");
            
            conn.setDoOutput(true);
            PrintStream printStream = new PrintStream(conn.getOutputStream());
            String input = "{\"BBSSOToken\":\"" + BBSSOToken + "\",\"query\":\"" + query + "\"}";
            printStream.println(input);
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("retorno imobpj:"+reader.readLine());
            return reader.readLine();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        } finally {
            desconectar(conn);
        }
    }  

   
    private void desconectar(HttpsURLConnection conn) {
        try {
            conn.disconnect();
        } catch (Exception e) {
        }
    }

    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
    };
}
