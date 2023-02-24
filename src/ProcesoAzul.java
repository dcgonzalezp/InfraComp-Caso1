
import java.util.concurrent.ThreadLocalRandom;

public class ProcesoAzul extends Thread{

	private Buzon buzonEntrada;
	private Buzon buzonSalida;
	private Asignador asig;
	private int numProductosTotales;
	private int prodConsumidos = 0;
	private int etapa;
	private int id;

	public ProcesoAzul(Buzon buzonEntrada, Buzon buzonSalida, Asignador asig, int numProductosTotales, int etapa, int id) {
		this.buzonEntrada = buzonEntrada;
		this.buzonSalida = buzonSalida;
		this.asig = asig;
		this.numProductosTotales = numProductosTotales;
		this.etapa = etapa;
		this.id = id;
	}
	
	public void crearProductos() {
		
		for (int i = 0; i < numProductosTotales; i++) {
			int idProducto = asig.asignarIdProducto();
			String prodNuevo = idProducto + " Producto AZUL";
			buzonSalida.guardarAzul(prodNuevo);
			System.out.println("Proceso AZUL " + id + " etapa "+ etapa + " ------> mensaje CREADO: " + prodNuevo);
		}
	}
	
	public void procesarProd() {
		
		while (prodConsumidos < numProductosTotales) {
			
			String productoObtenido = buzonEntrada.retirarAzul();
			prodConsumidos++;
			System.out.println("Proceso AZUL " + id + " etapa "+ etapa + " ------> mensaje OBTENIDO: " + productoObtenido);
			productoObtenido += " A" + id + "E" + etapa;
			
			int tiempo = ThreadLocalRandom.current().nextInt(50, 500);
			
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			buzonSalida.guardarAzul(productoObtenido);
			
			System.out.println("Proceso AZUL " + id + " etapa "+ etapa + " ------> mensaje PRODUCIDO: " + productoObtenido);
		}
		
	}
	
	public void run() {
		if (etapa == 1) {
			crearProductos();
		}
		else {
			procesarProd();
		}
	}
}
