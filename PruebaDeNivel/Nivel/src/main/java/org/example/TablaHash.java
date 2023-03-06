package org.example;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class TablaHash {

    private Map<Integer, List<String>> tabla;
    private int tamTabla;

    public TablaHash(int tamTabla) {
        this.tamTabla = tamTabla;
        tabla = new HashMap<>();
        for (int i = 0; i < tamTabla; i++) {
            tabla.put(i, new ArrayList<String>());
        }
    }

    public void agregar(String dato) {
        int pos = funcionHash(dato, tamTabla);
        List<String> lista = tabla.get(pos);
        lista.add(dato);
        tabla.put(pos, lista);
    }

    public String quitar(String dato) {
        int pos = buscar(dato);
        if (pos != -1) {
            List<String> lista = tabla.get(pos);
            lista.remove(dato);
            tabla.put(pos, lista);
            return dato;
        }
        return null;
    }

    public int buscar(String dato) {
        int pos = funcionHash(dato, tamTabla);
        List<String> lista = tabla.get(pos);
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(dato)) {
                return pos;
            }
        }
        return -1;
    }

    public int funcionHash(String dato, int tamTabla) {
        int hash = 7;
        for (int i = 0; i < dato.length(); i++) {
            hash = hash*31 + dato.charAt(i);
        }
        return Math.abs(hash) % tamTabla;
    }

    public int sondeo(int posicion, int tamTabla) {
        return (posicion + 1) % tamTabla;
    }

    public int cantidadElementos() {
        int cont = 0;
        for (int i = 0; i < tamTabla; i++) {
            cont += tabla.get(i).size();
        }
        return cont;
    }

    public static void main(String[] args) {
        TablaHash tabla = new TablaHash(100);
        InputStream inputStream = TablaHash.class.getResourceAsStream("/palabras.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                tabla.agregar(line);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }

        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("Elija una opción: ");
            System.out.println("1. Buscar palabra");
            System.out.println("2. Añadir palabra");
            System.out.println("3. Quitar palabra");
            System.out.println("4. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Introduce la palabra a buscar:");
                    String palabraBuscar = sc.nextLine();
                    int pos = tabla.buscar(palabraBuscar);
                    if (pos != -1) {
                        System.out.println("La palabra " + palabraBuscar + " se encuentra en la posición " + pos);
                    } else {
                        System.out.println("La palabra " + palabraBuscar + " no se encuentra en la tabla.");
                    }
                    break;
                case 2:
                    System.out.println("Introduce la palabra a añadir:");
                    String palabraAgregar = sc.nextLine();
                    tabla.agregar(palabraAgregar);
                    System.out.println("La palabra " + palabraAgregar + " ha sido añadida a la tabla.");
                    break;
                case 3:
                    System.out.println("Introduce la palabra a quitar:");
                    String palabraQuitar = sc.nextLine();
                    String resultado = tabla.quitar(palabraQuitar);
                    if (resultado != null) {
                        System.out.println("La palabra " + resultado + " ha sido eliminada de la tabla.");
                    } else {
                        System.out.println("La palabra " + palabraQuitar + " no se encuentra en la tabla.");
                    }
                    break;
                case 4:
                    System.out.println("Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }
}
