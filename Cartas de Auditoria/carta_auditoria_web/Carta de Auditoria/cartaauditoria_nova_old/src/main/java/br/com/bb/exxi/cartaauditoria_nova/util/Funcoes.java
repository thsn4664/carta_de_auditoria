package br.com.bb.exxi.cartaauditoria_nova.util;

import de.jollyday.Holiday;
import de.jollyday.HolidayManager;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.chrono.ISOChronology;

public class Funcoes {

    private static final String MESES[] = {"", "JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
    private static final int DEZ = 10;
    private static final int CEM = 100;
    private static final int TAM = 6;
    private static final String ZEROS = "0000000000000000000000";

    public String valor(double vl) {
        return new DecimalFormat("###,###,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR"))).format(vl);
    }

    public String valor(double vl, int decimais) {
        if (decimais == 0) {
            return new DecimalFormat("###,###,###,###,##0", new DecimalFormatSymbols(new Locale("pt", "BR"))).format(vl);
        } else {
            return new DecimalFormat("###,###,###,###,##0" + ".0000000000".substring(0, (decimais + 1)), new DecimalFormatSymbols(new Locale("pt", "BR"))).format(vl);
        }
    }

    public double valorGrafico(double vl, double divisor) {
        return Double.parseDouble(new DecimalFormat("##############0", new DecimalFormatSymbols(new Locale("pt", "BR"))).format(vl / divisor));
    }

    public String dia(Date dt) {
        if (dt == null) {
            return " ";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(dt);
    }

    public String mes(Date d) {
        return new SimpleDateFormat("MMM/yyyy").format(d);
    }

    public String mesAno(String d) {
        int ano = 0, mes = 0;
        if (d == null) {
            return "";
        }
        if (d.trim().length() != TAM) {
            return "";
        }
        mes = Integer.parseInt(d) % CEM;
        ano = (Integer.parseInt(d) - mes) / CEM;
        return MESES[mes] + "/" + ano;
    }

    public String formatatexto(String e) {
        String s = "";

        if (e == null) {
            return " ";
        }
        for (int i = 0; i < e.length(); i++) {
            if (e.charAt(i) == DEZ) {
                s += "<br>";
            } else {
                s += e.charAt(i);
            }
        }
        return s;
    }

    public String limpaTexto(String e) {
        String s = "", t1 = "���������������������������������������Ǫ���Ѵ~`^�'���\"", t2 = "cCaeiouaeiouAEIOUAEIOUaoAOUuaeiouAEIOUcC                     ";
        if (e == null) {
            return " ";
        }
        boolean ok = false;
        for (int i = 0; i < e.length(); i++) {
            ok = false;
            for (int x = 0; x < t1.length(); x++) {
                if (e.charAt(i) == t1.charAt(x)) {
                    s += t2.charAt(x);
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                s += e.charAt(i);
            }
        }
        return s;
    }

    public int intervaloDatas(Date di, Date df) {
        int intervalo = 0;
        LocalDate dti = new LocalDate(di.getYear() + 1900, di.getMonth() + 1, di.getDate());
        LocalDate dtf = new LocalDate(df.getYear() + 1900, df.getMonth() + 1, df.getDate());
        try {
            intervalo = Days.daysBetween(dti, dtf).getDays();
        } catch (Exception e) {
            intervalo = 0;
        }
        return intervalo;
    }

    public int intervaloDiasUteis(LocalDate di, LocalDate df) {
        int intervalo = 0;

        // obtem os feriados brasileiros
        HolidayManager gerenciadorDeFeriados = HolidayManager.getInstance(de.jollyday.HolidayCalendar.BRAZIL);
        Set<Holiday> feriados = gerenciadorDeFeriados.getHolidays(new DateTime().getYear());
        Set<LocalDate> dataDosFeriados = new HashSet<LocalDate>();
        for (Holiday h : feriados) {
            dataDosFeriados.add(new LocalDate(h.getDate(), ISOChronology.getInstance()));
        }
        // popula com os feriados brasileiros
        HolidayCalendar<LocalDate> calendarioDeFeriados = new DefaultHolidayCalendar<LocalDate>(dataDosFeriados);
        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("BR", calendarioDeFeriados);
        DateCalculator<LocalDate> calendario = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("BR", HolidayHandlerType.FORWARD);
        // calcula o intervalo
        //LocalDate dti = new LocalDate(di.getYear() + 1900, di.getMonth() + 1, di.getDate());
        //LocalDate dtf = new LocalDate(df.getYear() + 1900, df.getMonth() + 1, df.getDate());
        LocalDate dti = di;
        LocalDate dtf = df;
        while(!dti.isEqual(dtf)){
            if(!calendario.isNonWorkingDay(dti)){
                intervalo++;
            }
            dti = dti.plusDays(1);
        }
        
        return intervalo;
    }
    
    public LocalDate ultimoDiaMes(YearMonth anomes){
        int mes = anomes.getMonthOfYear();
        int ano = anomes.getYear();
        int dia;
        if(mes == 2){
            if(((ano % 4 == 0) && (ano % 100 != 0)) || (ano % 400 == 0)){
                dia = 29;
            }else{
                dia = 28;
            }
        }else if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes ==12){
            dia = 31;
        }else{
            dia = 30;
        }
        return new LocalDate(ano,mes,dia);
    }
    
    public boolean eLong(String s) {
        try {
            Long l = Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eInteger(String s) {
        try {
            Integer i = Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String verCnpj(String cnpj) {
        cnpj = cnpj.trim();
        if (cnpj.length() < 14) {
            cnpj = ZEROS.substring(0, 14 - cnpj.length()) + cnpj;
        }
        return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "."
                + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"
                + cnpj.substring(12, 14);
    }
}
