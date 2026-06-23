/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bb.exxi.cartaauditoria_nova.jdbc_base;

/**
 *
 * @author F9066619
 */
public class SQLs {
    //Classe não é mais necessária... acionamento via api direto na SAS_libraries
    public static final String libname_mci = "LIBNAME DB2MCI DB2 DATABASE=bdb2p04 SCHEMA=DB2MCI AUTHDOMAIN=DB2SDCOM;";
    public static final String SAS_carta_auditoria_outorgado = "SELECT " +
                                "t1.F_CLIENTE_COD FORMAT=9. AS CD_CLI, " +
                                "t2.F_CLIENTE_OTGDO FORMAT=9. AS CD_CLI_OTGDO, " +
                                "t3.NOM FORMAT=$60. AS NM_OTGDO, " +
                                "t3.COD_TIPO FORMAT=1. AS TP_PESSOA, " +
                                "t3.COD_CPF_CGC FORMAT=BEST15. AS CPF_CNPJ_OTGDO, " +
                                "SUM(CASE WHEN t1.COD_TIPO=12 THEN 0 ELSE 1 END) FORMAT=8. AS SUM_TIPO_DOC " +
                                "FROM DB2MCI.DOCUMENTO_PODER t1, DB2MCI.REPRESENTANTE t2, DB2MCI.CLIENTE t3 " +
                                "WHERE (t1.F_CLIENTE_COD = t2.F_DCTOPODR_CLIENTE AND t1.COD_SEQL = t2.F_DCTOPODR_CODSEQL AND t2.F_CLIENTE_OTGDO = t3.COD) " +
                                "AND t1.F_CLIENTE_COD = $where01 AND t1.DT_INC_VGC_DOC <= '$where02'd AND ( t1.DT_VNCT_VLD >= '$where02'd OR ( t1.DT_VNCT_VLD IS MISSING AND t1.DTA_VENC >= '$where02'd ) ) " +
                                "AND ( t2.DTA_BAIX_OTGA_REPR >= '$where02'd OR t2.DTA_BAIX_OTGA_REPR IS MISSING ) " +
                                "GROUP BY t1.F_CLIENTE_COD, t2.F_CLIENTE_OTGDO, t3.NOM, t3.COD_TIPO, t3.COD_CPF_CGC " +
                                "ORDER BY t1.F_CLIENTE_COD, t2.F_CLIENTE_OTGDO;";

}
