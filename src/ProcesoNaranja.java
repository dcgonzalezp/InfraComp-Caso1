
import java.util.concurrent.ThreadLocalRandom;

public class ProcesoNaranja extends Thread{
	
	private Buzon buzonEntrada;
	private Buzon buzonSalida;
	private Asignador asig;
	private int numProductosTotales;
	private int prodConsumidos = 0;
	private int etapa;
	private int id;

	public ProcesoNaranja(Buzon buzonEntrada, Buzon buzonSalida, Asignador asig, int numProductosTotales, int etapa, int id) {
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
			String prodNuevo = idProducto + " Producto NARANJA";
			buzonSalida.guardarNaranja(prodNuevo);
			System.out.println("Proceso NARANJA " + id + " etapa "+ etapa + " ------> mensaje CREADO: " + prodNuevo);
			//System.out.println("ID: " + id + "Etapa:" + etapa +"->" + buzonSalida.darAlmacen().size());
		}
	}
	
	public void procesarProd() {

		while (prodConsumidos < numProductosTotales) {
			String productoObtenido = buzonEntrada.retirarNaranja();
			prodConsumidos++;
			System.out.println("Proceso NARANJA " + id + " etapa "+ etapa + " ------> mensaje OBTENIDO: " + productoObtenido);
			productoObtenido += " N" + id + "E" + etapa;

			int tiempo = ThreadLocalRandom.current().nextInt(50, 500);
			
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			buzonSalida.guardarNaranja(productoObtenido);

			System.out.println("Proceso NARANJA " + id + " etapa "+ etapa + " ------> mensaje PRODUCIDO: " + productoObtenido);
		}
	}

	public void run() {
		if (etapa == 1) {
			this.setPriority(2);
			crearProductos();
		}
		else {
			this.setPriority(3);
			procesarProd();
		}

	}
}
