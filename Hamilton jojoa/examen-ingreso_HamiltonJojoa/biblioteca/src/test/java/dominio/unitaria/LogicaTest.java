package dominio.unitaria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dominio.logica.Logica;

public class LogicaTest {
	
	Logica logica = new Logica();
	
	@Test
	public void esPalindromo() {
		String isbn ="1221";
		boolean palindromo = logica.esPalindromo(isbn);
		assertTrue(palindromo);
	}
	
	@Test
	public void noPalindromo() {
		String isbn ="111122345";
		boolean palindromo = logica.esPalindromo(isbn);
		assertFalse(palindromo);
	}
	
	@Test 
	public void noPalindromoVacio() {
		String isbn = "";
		boolean palindromo = logica.esPalindromo(isbn);
		assertFalse(palindromo);
	}
	
	@Test
	public void sumaMas30Isbn() {
		String isbn = "T878B85Z";
		boolean resultado = false;
		int totalIsbn = logica.sumaTotalDigitosIsbn(isbn);
		if(totalIsbn>30) {
			resultado = true;
		}
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void sumaMenor30Isbn() {
		String isbn = "T87B5Z";
		boolean resultado = false;
		int totalIsbn = logica.sumaTotalDigitosIsbn(isbn);
		if(totalIsbn<30) {
			resultado = true;
		}
		Assert.assertTrue(resultado);
	}
	
	@Test
	public void fechaEntregaDomingo() throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaSolicitudPrueba = formato.parse("05/07/2019");
		Date fechaMaximaPrueba = formato.parse("22/07/2019");
                
        Date fechaResultado = logica.calculoFechaMaximaEntrega(fechaSolicitudPrueba);
        
        Assert.assertEquals(fechaResultado, fechaMaximaPrueba);
	}
	
	@Test
	public void fechaEntregaSemana() throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaSolicitudPrueba = formato.parse("06/07/2019");
		Date fechaMaximaPrueba = formato.parse("23/07/2019");
                
        Date fechaResultado = logica.calculoFechaMaximaEntrega(fechaSolicitudPrueba);
        
        Assert.assertEquals(fechaResultado, fechaMaximaPrueba);
	}

}
