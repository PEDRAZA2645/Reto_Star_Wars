package com.reto1;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;

public class ViajeInterplanetario {

    static String[] planetas = {
            "Tatooine", "Coruscant", "Endor", "Hoth", "Naboo",
            "Mustafar", "Dagobah", "Alderaan", "Jakku", "Mandalore",
            "Bespin", "Kashyyyk", "Yavin 4", "Geonosis", "Kamino"
    };
    static double[] distancias = {
            43.5, 120.0, 89.0, 150.0, 55.0,
            60.0, 95.0, 35.0, 70.0, 80.0,   // Distancias basadas en narrativa STAR WARS
            110.0, 75.0, 98.0, 100.0, 115.0
    };
    static String[] descripciones = {
            "Planeta desértico con dos soles.",
            "Capital de la República Galáctica.",
            "Luna boscosa hogar de los Ewoks.",
            "Planeta helado, base rebelde secreta.",
            "Planeta pacífico con una cultura rica.",
            "Planeta volcánico, lugar de duelo icónico.",
            "Planeta pantanoso, hogar de Yoda.",
            "Planeta destruido por la Estrella de la Muerte.",
            "Planeta desértico, lleno de chatarreros.",
            "Hogar de los Mandalorianos y su cultura.",
            "Planeta gaseoso con una ciudad flotante.",
            "Planeta bosque hogar de los Wookies.",
            "Luna selvática, base rebelde clave.",
            "Planeta árido con historia bélica.",
            "Planeta oceánico, hogar de los clonadores."
    };

    static String[] naves = {
            "Halcón Milenario", "X-Wing", "TIE Fighter", "Star Destroyer", "Slave I",
            "Imperial Shuttle", "A-Wing", "B-Wing", "Y-Wing", "TIE Interceptor"
    };
    static double[] velocidades = {
            0.5, 0.4, 0.35, 0.2, 0.3,  // Velocidades como fracciones de velocidad de la luz
            0.25, 0.45, 0.4, 0.3, 0.35
    };
    static int pasajeros;
    static double distanciaSeleccionada = -1;
    static double velocidadSeleccionada = -1;
    static String naveSeleccionada;
    static int cambiosDeRumbo = 2;
    static String destinoFinal;
    static Set<String> planetasVisitados = new HashSet<>();
    static int vidas = 3;
    static int reparacionesDisponibles = 2;
    static boolean escudosDisponibles = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuarJuego = true;

        while (continuarJuego) {
            iniciarJuego(scanner);
            System.out.println("\n¿Deseas volver a jugar o salir?");
            System.out.println("1. Volver a jugar");
            System.out.println("2. Salir");
            int decision = leerOpcion(scanner, 1, 2);
            if (decision != 1) {
                continuarJuego = false;
                System.out.println("Gracias por jugar. ¡Adiós!");
            }
        }

        scanner.close();
    }

    public static void iniciarJuego(Scanner scanner) {
        resetJuego();
        Random random = new Random();

        System.out.println("""
================================================================================================
================================================================================================

      _________________________ __________   __      __  _____ __________  _________
     /   _____/\\__    ___/  _  \\\\______   \\ /  \\    /  \\/  _  \\\\______   \\/   _____/
     \\_____  \\   |    | /  /_\\  \\|       _/ \\   \\/\\/   /  /_\\  \\|       _/\\_____  \\\s
     /        \\  |    |/    |    \\    |   \\  \\        /    |    \\    |   \\/        \\
    /_______  /  |____|\\____|__  /____|_  /   \\__/\\  /\\____|__  /____|_  /_______  /
            \\/                 \\/       \\/         \\/         \\/       \\/        \\/\s                    

                              V I A J E I N T E R P L A N E T A R I O

================================================================================================
================================================================================================

Hace mucho tiempo, en una galaxia muy, muy lejana...

La galaxia está en peligro. El Imperio se encuentra en su apogeo,
y los rebeldes necesitan tu ayuda. Como capitán de una de las
naves más rápidas, debes atravesar el espacio estelar,
afrontar peligros desconocidos y ayudar a la resistencia
a restaurar la paz y la justicia en la galaxia.

""");

        System.out.println("Tu punto de partida es la Tierra.");

        boolean continuar = true;

        while (continuar) {
            if (vidas == 0) {
                continuar = false;
                break;
            }
            if (distanciaSeleccionada == -1) {
                System.out.println("\nMenú Principal:");
                System.out.println("1. Seleccionar Planeta Destino");
                System.out.print("Elige una opción: ");
                int opcion = leerOpcion(scanner, 1, 1);

                if (opcion == 1) {
                    seleccionarPlaneta(scanner);
                }
            } else if (velocidadSeleccionada == -1) {
                System.out.println("\nMenú Principal:");
                System.out.println("2. Seleccionar Nave");
                System.out.print("Elige una opción: ");
                int opcion = leerOpcion(scanner, 2, 2);

               if (opcion == 2) {
                    seleccionarNave(scanner);
               }
            } else {
                System.out.println("\nMenú Principal:");
                System.out.println("3. Iniciar Viaje");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");
                int opcion = leerOpcion(scanner, 3, 4);

                if (opcion == 3) {
                    System.out.println("Saltando al hiperespacio...");
                    iniciarViaje(scanner, random);
                    continuar = false;
                } else if (opcion == 4) {
                    continuar = false;
                }
            }
        }
    }

    public static void resetJuego() {
        distanciaSeleccionada = -1;
        velocidadSeleccionada = -1;
        naveSeleccionada = null;
        cambiosDeRumbo = 2;
        destinoFinal = null;
        planetasVisitados.clear();
        reparacionesDisponibles = 2;
        escudosDisponibles = true;
        vidas = 3;
        pasajeros = 0;
    }

    public static int leerOpcion(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= min && opcion <= max) {
                    return opcion;
                } else {
                    System.out.println("Por favor seleccione una opción válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor seleccione solo números enteros positivos.");
            }
        }
    }

    public static void seleccionarPlaneta(Scanner scanner) {
        while (true) {
            System.out.println("\nPlanetas Disponibles:");
            for (int i = 0; i < planetas.length; i++) {
                System.out.println((i + 1) + ". " + planetas[i] + " - " + distancias[i] + " millones de km");
                System.out.println(" Descripción: " + descripciones[i]);
            }
            System.out.print("Elige un planeta destino (1-" + planetas.length + "): ");
            int seleccion = leerOpcion(scanner, 1, planetas.length);

            String planetaSeleccionado = planetas[seleccion - 1];
            if (planetaSeleccionado.equals(destinoFinal)) {
                System.out.println("Ya has elegido " + destinoFinal + " como destino final, selecciona otro planeta.");
            } else if (planetasVisitados.contains(planetaSeleccionado)) {
                System.out.println("Lo siento, este planeta ha sido conquistado por el Imperio, por favor seleccione otro planeta.");
            } else {
                distanciaSeleccionada = distancias[seleccion - 1] * 1_000_000;
                planetasVisitados.add(planetaSeleccionado);
                if (destinoFinal == null) {
                    destinoFinal = planetaSeleccionado;
                    System.out.println("Has seleccionado " + destinoFinal + " como tu destino final.");
                } else {
                    System.out.println("Has cambiado el rumbo a " + planetaSeleccionado + ".");
                }
                break;
            }
        }
    }

    public static void seleccionarNave(Scanner scanner) {
        System.out.println("\nNaves Disponibles:");
        for (int i = 0; i < naves.length; i++) {
            System.out.println((i + 1) + ". " + naves[i] + " - Velocidad: " + velocidades[i] + " UE");
        }
        System.out.print("Elige una nave (1-" + naves.length + "): ");
        int seleccion = leerOpcion(scanner, 1, naves.length);

        velocidadSeleccionada = velocidades[seleccion - 1];
        naveSeleccionada = naves[seleccion - 1];
        System.out.print("Ingresa la cantidad de pasajeros: ");
        pasajeros = leerOpcion(scanner, 1, Integer.MAX_VALUE);
        System.out.println("Has seleccionado la nave " + naveSeleccionada);
    }

    public static void iniciarViaje(Scanner scanner, Random random) {
        double duracion = (distanciaSeleccionada / velocidadSeleccionada) / 24;
        double combustible = calcularCombustible(distanciaSeleccionada, pasajeros);
        double oxigeno = calcularOxigeno(duracion, pasajeros);

        System.out.println("\nIniciando el viaje desde la Tierra...");
        System.out.println("Destino final: " + destinoFinal);
        System.out.println("Distancia al destino final: " + distanciaSeleccionada + " parsecs");
        System.out.println("Velocidad estimada: " + velocidadSeleccionada + " parsecs/hora");
        System.out.println("Duración estimada: " + (duracion) + " horas");
        System.out.println("Nave seleccionada: " + naveSeleccionada);
        System.out.println("Combustible requerido: " + combustible + " litros de Tibanna gas");
        System.out.println("Oxígeno requerido: " + oxigeno + " litros de oxígeno galáctico");

        double progreso = 0;

        while (progreso < 100 && vidas > 0) {
            progreso += random.nextInt(20) + 1;
            if (progreso >= 100) {
                System.out.println("Felicitaciones Ganaste!");
                System.out.println("¡Has llegado a tu destino final " + destinoFinal + " con éxito!");
                break;
            }

            System.out.println("Progreso del viaje: " + (int) progreso + "%");

            progreso = manejarEvento(scanner, random, progreso);

            if (cambiosDeRumbo == 0) {
                System.out.println("Ya no puedes cambiar de rumbo. Continuando tu viaje hacia tu destino final " + destinoFinal);
                progreso = manejarEvento(scanner, random, progreso);
            }

            if (vidas == 0) {
                return;
            }
        }
    }

    public static double manejarEvento(Scanner scanner, Random random, double progreso) {
        int evento = random.nextInt(4);
        boolean exito = false;

        switch (evento) {
            case 0:
                System.out.println("\nEstamos siendo atacados por el Imperio.");
                System.out.println("1. Atacar");
                System.out.println("2. Evadir y cambiar de rumbo");
                break;
            case 1:
                System.out.println("\n¡Cuidado! Estás a punto de colisionar con ballenas Purrgil.");
                System.out.println("1. Reducir velocidad y esperar");
                System.out.println("2. Aumentar velocidad y esquivar");
                break;
            case 2:
                System.out.println("\n¡Alerta! Estás entrando en una lluvia de meteoritos.");
                System.out.println("1. Activar escudos");
                System.out.println("2. Desviar y evitar");
                break;
            case 3:
                System.out.println("\n¡Peligro! Entrando en zona de agujeros de gusano.");
                System.out.println("1. Navegar cuidadosamente");
                System.out.println("2. Intentar salto rápido");
                break;
        }

        System.out.print("Elige una opción: ");
        int opcion = leerOpcion(scanner, 1, 2);

        if (evento == 0 && opcion == 1) {
            if (random.nextBoolean()) {
                System.out.println("¡Has ganado la batalla contra el Imperio!");
                exito = true;
            } else {
                manejarDestruccion(scanner);
            }
        } else if (evento == 0 && opcion == 2) {
            if (cambiosDeRumbo > 0) {
                cambiosDeRumbo--;
                seleccionarPlaneta(scanner);
                System.out.println("Ahora te quedan " + cambiosDeRumbo + " cambios de rumbo.");
                exito = true;
            } else if (cambiosDeRumbo == 0) {
                System.out.println("No te quedan cambios de rumbo. Atacando al Imperio.");
                if (random.nextBoolean()) {
                    System.out.println("¡Has ganado la batalla contra el Imperio!");
                    exito = true;
                } else {
                    manejarDestruccion(scanner);
                }
            }
        } else if (evento == 1 && opcion == 1) {
            if (random.nextBoolean()) {
                System.out.println("Lograste esquivar a las ballenas con éxito.");
                exito = true;
            } else {
                System.out.println("Evitaste chocar, pero la nave fue atacada por los SITH.");
                repararNave(scanner);
            }
        } else if (evento == 1 && opcion == 2) {
            if (random.nextBoolean()) {
                System.out.println("Lograste esquivar a las ballenas con éxito.");
                exito = true;
            } else {
                System.out.println("Esquivaste, pero la nave sufrió un daño severo.");
                repararNave(scanner);
            }
        } else if (evento == 2 && opcion == 1) {
            if (escudosDisponibles) {
                System.out.println("Pasaste, pero los escudos han sido dañados y no puedes volver a usarlos.");
                escudosDisponibles = false;
                exito = true;
            } else {
                System.out.println("Los Escudos estan dañados");
                manejarDestruccion(scanner);
            }
        } else if (evento == 2 && opcion == 2) {
            System.out.println("Desviaste y evitaste, pero la nave sufrió daños.");
            repararNave(scanner);
        } else if (evento == 3 && opcion == 1) {
            if (random.nextBoolean()) {
                System.out.println("Navegaste cuidadosamente y saliste de la zona.");
                exito = true;
            } else {
                System.out.println("Fuiste succionado por un agujero de gusano");
                manejarDestruccion(scanner);
            }
        } else if (evento == 3 && opcion == 2) {
            if (random.nextBoolean()) {
                System.out.println("El salto rápido fue exitoso.");
                exito = true;
            } else {
                System.out.println("Saliste, pero la nave sufrió graves daños.");
                repararNave(scanner);
            }
        }

        if (exito) {
            int progresoIncremento = random.nextInt(10) + 1; // Incremento aleatorio entre 1 y 10
            progreso += progresoIncremento;
        }

        return progreso;
    }

    public static double calcularCombustible(double distancia, int pasajeros) {
        return (distancia * 0.3) + (pasajeros * 50);
    }

    public static double calcularOxigeno(double duracion, int pasajeros) {
        return duracion * pasajeros * 0.8;
    }

    public static void manejarDestruccion(Scanner scanner) {
        manejarDestruccion(scanner, 0); // Llama al método con progreso inicializado a 0 o algún valor por defecto
    }

    public static void manejarDestruccion(Scanner scanner, double progreso) {
        vidas--;
        System.out.println("Tu nave ha sido destruida. Te quedan " + vidas + " vidas.");
        if (vidas == 0) {
            System.out.println("\nHas perdido todas tus vidas. Fin del juego.");
        } else {
            System.out.println("¿Quieres intentar de nuevo?");
            System.out.println("1. Volver a intentar");
            System.out.println("2. Terminar");
            int decision = leerOpcion(scanner, 1, 2);
            if (decision == 1) {
                manejarEvento(scanner, new Random(), progreso);
            } else {
                finDelJuego(scanner);
            }
        }
    }

    public static void repararNave(Scanner scanner) {
        if (reparacionesDisponibles > 0) {
            reparacionesDisponibles--;
            System.out.println("Reparando la nave. Reparaciones restantes: " + reparacionesDisponibles);
            return;
        } else {
            System.out.println("No te quedan reparaciones.");
            if (vidas > 0) {
                manejarDestruccion(scanner);
            } else {
                System.out.println("No te quedan vidas. Fin del juego.");
            }
        }
    }

    public static void finDelJuego(Scanner scanner) {
        System.out.println("\n¿Deseas volver a jugar o salir?");
        System.out.println("1. Volver a jugar");
        System.out.println("2. Salir");
        int decision = leerOpcion(scanner, 1, 2);
        if (decision == 1) {
            resetJuego(); // Reinicia el estado del juego
        } else {
            System.out.println("Gracias por jugar. ¡Adiós!");
            System.exit(0);
        }
    }
}