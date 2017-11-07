package AGJ.PruebaMockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.hamcrest.core.Every;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GestorLoginTest {
  GestorLogin login; // Sut
  IRepositorioCuentas repo;
  ICuenta cuenta; // Collaborator

  @Before
  public void setUp() throws Exception {
    repo = mock(IRepositorioCuentas.class);
    cuenta = mock(ICuenta.class);
    when(repo.buscar("pepe")).thenReturn(cuenta); 
    login = new GestorLogin (repo);
  }

  @After
  public void tearDown() throws Exception {
    repo = null;
    login = null;
    cuenta = null;
  }

  @Test
  public void testAccesoConcedidoALaPrimera() {
    when(cuenta.claveCorrecta ("1234")).thenReturn(true);
    login.acceder("pepe", "1234");
    verify(cuenta, times(1)).entrarCuenta(); // verifica que se invoca una cuenta
    verify(cuenta, never()).bloquearCuenta();
  }
  @Test
  public void testAccesoDenegadoALaPrimera() {
    when(cuenta.claveCorrecta ("1234")).thenReturn(true);
    login.acceder("pepe", "4321");
    verify(cuenta, never()).entrarCuenta();
    verify(cuenta, never()).bloquearCuenta();
    assertThat(login.getNumFallos(), is(1));
  }
    @Test (expected = ExcepcionUsuarioDesconocido.class)
      public void siUsuarioDesconocido_LanzarException() {
      when(repo.buscar("manolo")).thenThrow(ExcepcionUsuarioDesconocido.class);
      login.acceder("manolo", "4321");
    }
  
    /*
  @Test 
  public void siUsuarioDesconocido_LanzarException() {
    when(repo.buscar("manolo")).thenThrow(ExcepcionUsuarioDesconocido.class);
    
    try {
      login.acceder("manolo", "4321");
      fail("Debe lanzar una excepcion");
    } catch (ExcepcionUsuarioDesconocido e) {
      verify(repo).buscar("manolo");
    }
  }*/
  
  

}
