package br.com.bb.exxi.cartaauditoria_nova.jdbc_base;

import br.com.bb.exxi.cartaauditoria_nova.entidades.Outorgados;
import br.com.bb.exxi.cartaauditoria_nova.entidades.Usuario;
import br.com.bb.exxi.cartaauditoria_nova.util.ConstantsSSO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Sas_libraries {
    private Usuario usuario;
    
    public void init(){
    }
    
    public List<Outorgados> executeQuery(int cdCli, String sql, Date dtBase) throws SQLException, IOException {

            List<Outorgados> lc = new ArrayList<Outorgados>();

            //SQL
            sql = "SELECT t1.F_CLIENTE_COD, t2.F_CLIENTE_OTGDO, t3.NOM, t3.COD_TIPO, t3.COD_CPF_CGC, SUM(CASE WHEN t1.COD_TIPO=12 THEN 0 ELSE 1 END) AS SOMA "
                    + "FROM DB2MCI.DOCUMENTO_PODER t1, DB2MCI.REPRESENTANTE t2, DB2MCI.CLIENTE t3 "
                    + "WHERE (t1.F_CLIENTE_COD = t2.F_DCTOPODR_CLIENTE "
                    + "AND t1.COD_SEQL = t2.F_DCTOPODR_CODSEQL "
                    + "AND t2.F_CLIENTE_OTGDO = t3.COD) "
                    + "AND t1.F_CLIENTE_COD = " + cdCli + " "
                    + "AND t1.DT_INC_VGC_DOC <= '" + new SimpleDateFormat("yyyy-MM-dd").format(dtBase) + "' "
                    + "AND ( t1.DT_VNCT_VLD >= '" + new SimpleDateFormat("yyyy-MM-dd").format(dtBase) + "' OR ( t1.DT_VNCT_VLD IS NULL AND t1.DTA_VENC >= '" + new SimpleDateFormat("yyyy-MM-dd").format(dtBase) + "' ) ) "
                    + "AND ( t2.DTA_BAIX_OTGA_REPR >= '" + new SimpleDateFormat("yyyy-MM-dd").format(dtBase) + "' OR t2.DTA_BAIX_OTGA_REPR IS NULL ) "
                    + "GROUP BY t1.F_CLIENTE_COD, t2.F_CLIENTE_OTGDO, t3.NOM, t3.COD_TIPO, t3.COD_CPF_CGC "
                    + "ORDER BY t1.F_CLIENTE_COD, t2.F_CLIENTE_OTGDO";

            System.out.println(sql);

            ConsultaDB2 api = new ConsultaDB2();
            String result = api.getAPIDB2MySQLConnection(ConstantsSSO.ssoToken, sql);


            result = result.replace("[", "").replace("]", "").replace("\"", "").replace("\'", "").replace("{", "");
            //result = result.replace("[", "").replace("]", "").replace("\"", "\'");
            System.out.println(result);
            System.out.println("retorno outorgados:"+result);

            //result = result.replace("{", "");
            String[] jsons = result.split("},");
            jsons[jsons.length - 1] = jsons[jsons.length -1].replace("}","");
            String[] outorgadoObj = new String[6];

            for(String json : jsons){
                String[] dados = json.split(",");
                for(int i=0; i<dados.length; i++){
                    String[] d = dados[i].split(":");
                    //outorgadoObj[i] = d[1].replace("\'","");
                    outorgadoObj[i] = d[1];
                    //System.out.println(dados[i]);
                    System.out.println(outorgadoObj[i]);
                }
               // System.out.println(json);
                //System.out.println(outorgado);
                lc.add(new Outorgados(
                    dtBase, //data base
                    Integer.parseInt(outorgadoObj[2]), //mci_cli
                    Integer.parseInt(outorgadoObj[0]), //mci_outorgado
                    outorgadoObj[3],                    //nome_outorgado
                    (outorgadoObj[4].equals("1") ? "PF" : "PJ"),  //tipo_pessoa
                    outorgadoObj[5],                   //cpf_cnpj
                    Integer.parseInt(outorgadoObj[1])  //sum_tipo_doc
                ));    
            }
            return lc;
    }
                  
}
