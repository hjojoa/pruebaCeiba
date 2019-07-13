package dominio.integracion;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private static final String HAMILTON_DANIEL_JOJOA_CORDOBA = "Hamilton Daniel Jojoa Córdoba";
	
	private SistemaDePersistencia sistemaPersistencia;
	
	private RepositorioLibro repositorioLibros;
	private RepositorioPrestamo repositorioPrestamo;

	@Before
	public void setUp() {
		
		sistemaPersistencia = new SistemaDePersistencia();
		
		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();
		
		sistemaPersistencia.iniciar();
	}
	

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	@Test
	public void prestarLibroSinUsuarioTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(),null);

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));
		Assert.assertNull(repositorioPrestamo.obtener(libro.getIsbn()).getNombreUsuario());
	}
	
	@Test
	public void prestarLibroConUsuarioTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		String nombreUsuario = HAMILTON_DANIEL_JOJOA_CORDOBA;
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(),nombreUsuario);

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtener(libro.getIsbn()).getNombreUsuario());
	}

	@Test
	public void prestarLibroNoDisponibleTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		String nombreUsuario = HAMILTON_DANIEL_JOJOA_CORDOBA;
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(),nombreUsuario);
		try {
			
			blibliotecario.prestar(libro.getIsbn(),nombreUsuario);
			fail();
			
		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE, e.getMessage());
		}
	}
	
	@Test
	public void prestarLibroPalindromoTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn("A12321A").build();
		String nombreUsuario = HAMILTON_DANIEL_JOJOA_CORDOBA;
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		try {
			
			blibliotecario.prestar(libro.getIsbn(),nombreUsuario);
			fail();
			
		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.LIBRO_PALINDROMO_SOLO_BIBLIOTECA, e.getMessage());
		}
	}
	
	@Test
	public void prestarLibroIsbnMayor30() {
		String isbn = "T878B85Z";
		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn(isbn).build();
		String nombreUsuario = HAMILTON_DANIEL_JOJOA_CORDOBA;
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(),nombreUsuario);		
		
		// assert
		Assert.assertTrue(blibliotecario.esPrestado(isbn));
		Assert.assertNotNull(repositorioPrestamo.obtener(isbn).getFechaEntregaMaxima());
		Assert.assertNotNull(repositorioPrestamo.obtener(isbn).getNombreUsuario());		
	}	

	@Test
	public void prestarLibroIsbnMenor30() {
		String isbn = "T11B25Z";
		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn(isbn).build();
		String nombreUsuario = HAMILTON_DANIEL_JOJOA_CORDOBA;
		repositorioLibros.agregar(libro);
		
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(),nombreUsuario);		
		
		// assert
		Assert.assertTrue(blibliotecario.esPrestado(isbn));
		Assert.assertNull(repositorioPrestamo.obtener(isbn).getFechaEntregaMaxima());
		Assert.assertNotNull(repositorioPrestamo.obtener(isbn).getNombreUsuario());		
	}
			
}
