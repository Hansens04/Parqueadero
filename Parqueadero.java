import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un parqueadero con TAMANO puestos.
 */
public class Parqueadero {
    // Constantes
    public static final int TAMANO = 40;
    public static final int NO_HAY_PUESTO = -1;
    public static final int PARQUEADERO_CERRADO = -2;
    public static final int CARRO_NO_EXISTE = -3;
    public static final int CARRO_YA_EXISTE = -4;
    public static final int HORA_INICIAL = 6;
    public static final int HORA_CIERRE = 20;
    public static final int TARIFA_INICIAL = 1200;

    // Atributos
    private List<Puesto> puestos;
    private int tarifa;
    private int caja;
    private int horaActual;
    private boolean abierto;

    // Constructores
    public Parqueadero() {
        horaActual = HORA_INICIAL;
        abierto = true;
        tarifa = TARIFA_INICIAL;
        caja = 0;
        puestos = new ArrayList<>(TAMANO);
        for (int i = 0; i < TAMANO; i++) {
            puestos.add(new Puesto(i));
        }
    }

    // Métodos
    public String darPlacaCarro(int pPosicion) {
        String respuesta = "";
        if (estaOcupado(pPosicion)) {
            respuesta = "Placa: " + puestos.get(pPosicion).darCarro().darPlaca();
        } else {
            respuesta = "No hay un carro en esta posición";
        }
        return respuesta;
    }

    public int entrarCarro(String pPlaca) {
        int resultado;
        if (!abierto) {
            resultado = PARQUEADERO_CERRADO;
        } else {
            int numPuestoCarro = buscarPuestoCarro(pPlaca.toUpperCase());
            if (numPuestoCarro != CARRO_NO_EXISTE) {
                resultado = CARRO_YA_EXISTE;
            } else {
                resultado = buscarPuestoLibre();
                if (resultado != NO_HAY_PUESTO) {
                    Carro carroEntrando = new Carro(pPlaca, horaActual);
                    puestos.get(resultado).parquearCarro(carroEntrando);
                }
            }
        }
        return resultado;
    }

    public int sacarCarro(String pPlaca) {
        int resultado;
        if (!abierto) {
            resultado = PARQUEADERO_CERRADO;
        } else {
            int numPuesto = buscarPuestoCarro(pPlaca.toUpperCase());
            if (numPuesto == CARRO_NO_EXISTE) {
                resultado = CARRO_NO_EXISTE;
            } else {
                Carro carro = puestos.get(numPuesto).darCarro();
                int nHoras = carro.darTiempoEnParqueadero(horaActual);
                int porPagar = nHoras * tarifa;
                caja += porPagar;
                puestos.get(numPuesto).sacarCarro();
                resultado = porPagar;
            }
        }
        return resultado;
    }

    public int darMontoCaja() {
        return caja;
    }

    public int calcularPuestosLibres() {
        int puestosLibres = 0;
        for (Puesto puesto : puestos) {
            if (!puesto.estaOcupado()) {
                puestosLibres++;
            }
        }
        return puestosLibres;
    }

    public void cambiarTarifa(int pTarifa) {
        tarifa = pTarifa;
    }

    private int buscarPuestoLibre() {
        int puesto = NO_HAY_PUESTO;
        for (int i = 0; i < TAMANO && puesto == NO_HAY_PUESTO; i++) {
            if (!puestos.get(i).estaOcupado()) {
                puesto = i;
            }
        }
        return puesto;
    }

    private int buscarPuestoCarro(String pPlaca) {
        int puesto = CARRO_NO_EXISTE;
        for (int i = 0; i < TAMANO && puesto == CARRO_NO_EXISTE; i++) {
            if (puestos.get(i).tieneCarroConPlaca(pPlaca)) {
                puesto = i;
            }
        }
        return puesto;
    }

    public void avanzarHora() {
        if (horaActual <= HORA_CIERRE) {
            horaActual++;
        }
        if (horaActual == HORA_CIERRE) {
            abierto = false;
        }
    }

    public int darHoraActual() {
        return horaActual;
    }

    public boolean estaAbierto() {
        return abierto;
    }

    public int darTarifa() {
        return tarifa;
    }

    public boolean estaOcupado(int pPuesto) {
        return puestos.get(pPuesto).estaOcupado();
    }
    public double darTiempoPromedio() {
        int totalCarros = 0;
        int tiempoTotal = 0;

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                tiempoTotal += carro.darTiempoEnParqueadero(horaActual);
                totalCarros++;
            }
        }

        // Evitar la división por cero
        if (totalCarros == 0) {
            return 0.0;
        }

        return (double) tiempoTotal / totalCarros;
    }
    public boolean hayCarroMasDeOchoHoras() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 8) {
                    return true; // Hay un carro que lleva más de 8 horas parqueado
                }
            }
        }
        return false; // No hay ningún carro que lleve más de 8 horas parqueado
    }

    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<Carro> carrosMasDeTresHoras = new ArrayList<>();

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);
                if (tiempoEnParqueadero > 3) {
                    carrosMasDeTresHoras.add(carro);
                }
            }
        }

        return carrosMasDeTresHoras;
    }
    public boolean hayCarrosPlacaIgual() {
        for (int i = 0; i < TAMANO; i++) {
            if (puestos.get(i).estaOcupado()) {
                Carro carro1 = puestos.get(i).darCarro();
                String placaCarro1 = carro1.darPlaca();

                for (int j = i + 1; j < TAMANO; j++) {
                    if (puestos.get(j).estaOcupado()) {
                        Carro carro2 = puestos.get(j).darCarro();
                        String placaCarro2 = carro2.darPlaca();

                        if (carro1.tienePlaca(placaCarro2)) {
                            return true; // Hay dos carros con la misma placa
                        }
                    }
                }
            }
        }
        return false; // No hay dos carros con la misma placa
    }
    public int contarCarrosQueComienzanConPlacaPB() {
        int contador = 0;

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                String placa = carro.darPlaca();

                if (placa.startsWith("PB")) {
                    contador++;
                }
            }
        }

        return contador;
    }

    public boolean hayCarroCon24Horas() {
        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                Carro carro = puesto.darCarro();
                int tiempoEnParqueadero = carro.darTiempoEnParqueadero(horaActual);

                if (tiempoEnParqueadero >= 24) {
                    return true; // Hay un carro que lleva 24 o más horas parqueado
                }
            }
        }

        return false; // No hay ningún carro que lleve 24 o más horas parqueado
    }





    // Puntos de Extensión
    public String metodo1() {
        int cantidadCarrosPB = contarCarrosQueComienzanConPlacaPB();
        boolean hayCarro24Horas = hayCarroCon24Horas();

        String mensaje = "Cantidad de carros con placa PB: " + cantidadCarrosPB + " – Hay carro parqueado por 24 o más horas: ";

        if (hayCarro24Horas) {
            mensaje += "Sí.";
        } else {
            mensaje += "No.";
        }

        return mensaje;
    }

    public int desocuparParqueadero() {
        int cantidadCarrosSacados = 0;

        for (Puesto puesto : puestos) {
            if (puesto.estaOcupado()) {
                puesto.sacarCarro();
                cantidadCarrosSacados++;
            }
        }

        return cantidadCarrosSacados;
    }


    public String metodo2() {
        int cantidadCarrosSacados = desocuparParqueadero();
        return "Cantidad de carros sacados: " + cantidadCarrosSacados + ".";
    }

}
