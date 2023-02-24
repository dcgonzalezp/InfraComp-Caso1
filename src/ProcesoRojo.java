
public class ProcesoRojo extends Thread{
	
	private Buzon buzonFinal;
	private int numGlobalTotalProductos;
	
	public ProcesoRojo(Buzon buzonFinal, int numGlobalTotalProductos) {
		this.buzonFinal = buzonFinal;
		this.numGlobalTotalProductos = numGlobalTotalProductos;
	}
	
	public void mostrarResultados() {
		int size = buzonFinal.darAlmacen().size();
		buzonFinal.conteo(size);
		
		int contador = 0;
		for (int i = 0; i < size; i++) {
			String[] strs = buzonFinal.darAlmacen().get(i).split(" ");
			int num = Integer.parseInt(strs[0]);
			if (num == contador) {
				System.out.println("PROCESO ROJO ------> Se obtuvo el producto: " + buzonFinal.darAlmacen().get(i));
				contador++;
				i = 0;
			}
			if (contador == numGlobalTotalProductos-1) {
				break;
			}
		}
	}
	
	public void run() {
		this.setPriority(1);
		mostrarResultados();
	}
}
