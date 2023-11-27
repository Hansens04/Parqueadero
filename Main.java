import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero();

        int opcion;

        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Ingresar un carro al parqueadero");
            System.out.println("2. Dar salida a un carro del parqueadero");
            System.out.println("3. Informar los ingresos del parqueadero");
            System.out.println("4. Consultar la cantidad de puestos disponibles");
            System.out.println("5. Avanzar el reloj del parqueadero");
            System.out.println("6. Cambiar la tarifa del parqueadero");
            System.out.println("7. Mostrar cantidad de carros con placa PB y si hay carros parqueados mas de 24 horas");
            System.out.println("8. Desocupar parquedero y mostrar cantidad");
            System.out.println("0. Salir");
            System.out.print("Ingrese la opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la placa del carro: ");
                    String placa = scanner.next();
                    int resultadoIngreso = parqueadero.entrarCarro(placa);
                    if (resultadoIngreso == Parqueadero.NO_HAY_PUESTO) {
                        System.out.println("No hay puestos disponibles.");
                    } else if (resultadoIngreso == Parqueadero.CARRO_YA_EXISTE) {
                        System.out.println("El carro ya está en el parqueadero.");
                    } else {
                        System.out.println("Carro ingresado al puesto " + resultadoIngreso);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese la placa del carro a sacar: ");
                    String placaSalida = scanner.next();
                    int resultadoSalida = parqueadero.sacarCarro(placaSalida);
                    if (resultadoSalida == Parqueadero.CARRO_NO_EXISTE) {
                        System.out.println("El carro no está en el parqueadero.");
                    } else {
                        System.out.println("Se ha sacado el carro. Total a pagar: $" + resultadoSalida);
                    }
                    break;

                case 3:
                    System.out.println("Ingresos del parqueadero: $" + parqueadero.darMontoCaja());
                    break;

                case 4:
                    System.out.println("Puestos disponibles: " + parqueadero.calcularPuestosLibres());
                    break;

                case 5:
                    parqueadero.avanzarHora();
                    System.out.println("Reloj del parqueadero avanzado. Hora actual: " + parqueadero.darHoraActual());
                    break;

                case 6:
                    System.out.print("Ingrese la nueva tarifa: ");
                    int nuevaTarifa = scanner.nextInt();
                    parqueadero.cambiarTarifa(nuevaTarifa);
                    System.out.println("Tarifa cambiada exitosamente.");
                    break;
                case 7:
                    System.out.println(parqueadero.metodo1());
                    break;
                case 8:
                    System.out.println(parqueadero.metodo2());
                case 0:
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, ingrese una opción válida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}
