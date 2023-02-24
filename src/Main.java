import java.util.Scanner;

public class Main {
	
	private final static int ETAPAS = 3;
	
	private static Asignador asig;
	private static ProcesoRojo procesoFinal;
	private static Buzon buzon1;
	private static Buzon buzon2;
	private static Buzon buzonFinal;
	
	public static void main(String[] args) throws InterruptedException{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el número de procesos por etapa: " );
		int procesosPorEtapa = scanner.nextInt();
		System.out.println("Ingrese el número de productos que se deben crear: " );
		int productosACrear = scanner.nextInt();
		System.out.println("Ingrese el tamaño de los buzones (este tamaño no aplica para el último buzón): " );
		int tamanoBuzon = scanner.nextInt();
		scanner.close();
		buzonFinal = new Buzon(Integer.MAX_VALUE);
		procesoFinal = new ProcesoRojo(buzonFinal, procesosPorEtapa * productosACrear);
		
		buzon1 = new Buzon(tamanoBuzon);
		buzon2 = new Buzon(tamanoBuzon);
		asig = new Asignador();
		ProcesoNaranja N;
		ProcesoAzul A;
		
		for (int i = 1; i <= ETAPAS; i++) {
			
			for (int j = 0; j < procesosPorEtapa; j++) {
				if (i == 1) {
					if(j == 0) {
						N = new ProcesoNaranja(null, buzon1, asig, productosACrear, i, asig.asignarIdNaranja());
						N.start();
					}
					else {
						A = new ProcesoAzul(null, buzon1, asig, productosACrear, i, asig.asignarIdAzul());
						A.start();
					}
				}
				else if(i == 2) {
					if(j == 0) {
						N = new ProcesoNaranja(buzon1, buzon2, asig, productosACrear, i, asig.asignarIdNaranja());
						N.start();
					}
					else {
						A = new ProcesoAzul(buzon1, buzon2, asig, productosACrear, i, asig.asignarIdAzul());
						A.start();
					}
				}
				else if(i == 3) {
					if(j == 0) {
						N = new ProcesoNaranja(buzon2, buzonFinal, asig, productosACrear, i, asig.asignarIdNaranja());
						N.start();
					}
					else {
						A = new ProcesoAzul(buzon2, buzonFinal, asig, productosACrear, i, asig.asignarIdAzul());
						A.start();
					}
				}
			}
		}
		procesoFinal.start();
	}
}