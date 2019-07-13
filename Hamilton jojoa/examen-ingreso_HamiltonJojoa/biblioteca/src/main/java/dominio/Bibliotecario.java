package dominio;

import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.logica.Logica;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String LIBRO_PALINDROMO_SOLO_BIBLIOTECA = "los libros palíndromos solo se pueden utilizar en la biblioteca";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;
	

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn, String nombreUsuario) {
		Date fechaEntregaMaxima = null;
		Date fechaInicial = new Date();
		if(!esPrestado(isbn)) {
			Logica logica = new Logica(); 
			if(!logica.esPalindromo(isbn)) {
				if(logica.sumaTotalDigitosIsbn(isbn)>30) {
					fechaEntregaMaxima = logica.calculoFechaMaximaEntrega(fechaInicial);
				}
				Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
				Prestamo prestamo = new Prestamo(libro, nombreUsuario, fechaEntregaMaxima);
				repositorioPrestamo.agregar(prestamo);
				
				
			}else {
				throw new PrestamoException(LIBRO_PALINDROMO_SOLO_BIBLIOTECA);
			}
		}else {
		throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
	}
	
	public boolean esPrestado(String isbn) {
		Libro libro = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		if(libro == null) {
			return false;
		}else {
			return true;
		}
		
	}

}
