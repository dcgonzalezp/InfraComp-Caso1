
public class Asignador {
	
	private int idsNaranjas=0;
	private int idsAzules=0;
	private int idsProductos=0;
	
	public synchronized int asignarIdNaranja() {
		return idsNaranjas++;
	}
	
	public synchronized int asignarIdAzul() {
		return idsAzules++;
	}
	
	public synchronized int asignarIdProducto() {
		return idsProductos++;
	}
}
