import java.util.ArrayList;

public class Buzon  {

	private ArrayList<String> productos;
	private  boolean hayAzules = false;
	private  boolean hayNaranjas = false;
	private int tamano;

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public Buzon(int tamano) {
		this.tamano = tamano;
		productos = new ArrayList<>();
	}

	public ArrayList<String> darAlmacen(){
		return productos;
	}
	
//	public void conteo(int total) {
//		synchronized (lock1) {
//			while(productos.size() < total) {
//				Thread.yield();
//				System.out.println("feeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//			}
//			
//			
//		}
//	}

	public void guardarAzul(String producto) {

		synchronized (lock1) {
			while (productos.size() == tamano) {
				try {
					lock1.wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			productos.add(producto);
			hayAzules = true;
			lock1.notify();
		}


	}

	public void guardarNaranja(String producto) {

		synchronized (lock1) {
			//System.out.println(Thread.currentThread());
			while (productos.size() == tamano) {
				Thread.yield();
				//System.out.println("111111111111111111111111");
			}		
			productos.add(producto);
			hayNaranjas = true;
			//System.out.println(hayNaranjas);
			lock1.notify();
			
		}
	}

	public String retirarAzul() {
		synchronized (lock1) {
			String prod = "";

			while (productos.isEmpty() || !hayAzules) {
				try {
					lock1.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < productos.size(); i++) {
				String[] strs = productos.get(i).split(" ");
				if(strs[2].equals("AZUL")) {
					prod = productos.remove(i);
					break;
				}
			}

			verificarAzules();
			lock1.notify();
			return prod;
		}
	}

	public String retirarNaranja() {
		String prod = "";
		synchronized (lock2) {
			while (productos.isEmpty() || !hayNaranjas) {
				Thread.yield();
				//System.out.println("000000000000000000000000");
			}

			for (int i = 0; i < productos.size(); i++) {
				String[] strs = productos.get(i).split(" ");
				if(strs[2].equals("NARANJA")) {
					prod = productos.remove(i);
					break;
				}
			}
			verificarNaranjas();
		}
		synchronized (lock1) {
			lock1.notify();
		}
		return prod;
	}


	private void verificarAzules() {
		hayAzules = false;
		for (int i = 0; i < productos.size(); i++) {
			String[] strs = productos.get(i).split(" ");
			if(strs[2].equals("AZUL")) {
				hayAzules = true;
				break;
			}

		}
	}

	private void verificarNaranjas() {
		hayNaranjas = false;
		for (int i = 0; i < productos.size(); i++) {
			String[] strs = productos.get(i).split(" ");
			if(strs[2].equals("NARANJA")) {
				hayNaranjas = true;
				break;
			}
		}
	}
}
