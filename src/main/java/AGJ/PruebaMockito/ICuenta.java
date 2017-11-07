package AGJ.PruebaMockito;

public interface ICuenta {

	boolean claveCorrecta(String candidata);
	void entrarCuenta();
	void bloquearCuenta();
	boolean estaBloqueada();
	boolean estaEnUso();

}
