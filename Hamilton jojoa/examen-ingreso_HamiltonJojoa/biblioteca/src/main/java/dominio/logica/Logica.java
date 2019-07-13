package dominio.logica;

import java.util.Calendar;
import java.util.Date;

public class Logica {
	
	public boolean esPalindromo(String isbn) {
		boolean esPalindromo = true;
		int asc = 0;
		int size = isbn.length();
		if( size > 0) {
			for (int desc = (size-1); desc > 0; desc--) {
	            if(isbn.charAt(desc) != isbn.charAt(asc))
	            {
	                esPalindromo = false;
	                break;
	            }
	            asc++;
	        }        	
        }else {
        	esPalindromo = false;
        }
        
		return esPalindromo;
	}
	
	
	public Date calculoFechaMaximaEntrega(Date fechaInicial) {
		Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime(fechaInicial);
        int cont = 0;
        while(cont <14) {
            if (fechaActual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                cont++;
            }
            fechaActual.add(Calendar.DATE, 1);
        }
        if(fechaActual.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            fechaActual.add(Calendar.DATE, 1);
        }
        return fechaActual.getTime();
    }
	
	public int sumaTotalDigitosIsbn(String isbn) {
		int totalIsbn = 0;
		for(int i=0; i<isbn.length();i++) {
			char digito = isbn.charAt(i);
			if(isNumeric(digito)){
				String valor = String.valueOf(digito);
				totalIsbn = totalIsbn + Integer.parseInt(valor);
			}
		}
		return totalIsbn;
	}
	
	public boolean isNumeric(char valor) {
		boolean resultado = false;
		try {
			Integer.parseInt(String.valueOf(valor));
			resultado = true;
		}catch(Exception e) {
			
		}
		return resultado;
	}

}
