package com.everis.EverisLogback1MFH;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App {

	// nos creamos el logger primero
	final static Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static List<Integer> primes;
	final static Path PRIMEPATH = Paths.get("primes.txt");
	final static Integer NUMBERTOP = 10000;

	/**
	 * Metodo main que lanza la aplicación.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (Files.notExists(PRIMEPATH)) {
			primes = primeGen(NUMBERTOP);
			savePrimes();
		} else {
			readPrimes();
		}

		loggerTest();

	}

	/**
	 * Bucle que genera los logs, el ejemplo consiste en iterar 10000 numeros, y guardar el numero la vuelta. Ademas, vamos generando un numero aleatorio en
	 * cada vuelta, y si es primo, lanzamos un warning en el logger.
	 */
	private static void loggerTest() {
		LOGGER.info("inicio");
		Random rand = new Random();
		Integer currentRand;
		for (int i = 0; i < NUMBERTOP; i++) {

			currentRand = rand.nextInt(NUMBERTOP);
			LOGGER.debug("vuelta {}", i);
			LOGGER.trace("esto no deberia loggearse por que el root del logger esta en debug");
			if (primes.contains(currentRand)) {
				LOGGER.warn("ha APARECIDO un PRIMO");
			}
		}
		LOGGER.info("fin");
	}

	/**
	 * Metodo para generar una lista de primos usando la criba de Eratostenes.
	 * 
	 * @param n
	 *            se generan todos los numeros primos por debajo de este parametro
	 * @return ArrayList de numeros primos.
	 */
	public static List<Integer> primeGen(int n) {
		boolean prime[] = new boolean[n + 1];
		Arrays.fill(prime, true);
		for (int p = 2; p * p <= n; p++) {
			if (prime[p]) {
				for (int i = p * 2; i <= n; i += p) {
					prime[i] = false;
				}
			}
		}
		List<Integer> primeNumbers = new ArrayList<>();
		for (int i = 2; i <= n; i++) {
			if (prime[i]) {
				primeNumbers.add(i);
			}
		}
		return primeNumbers;
	}

	/**
	 * Guarda La lista de primos en un archivo para tener persistencia y no tener que regenerarla cada vez que se lanza el programa.
	 * 
	 */
	private static void savePrimes() {
		String primeStr = "";

		for (Integer p : primes) {
			primeStr += p + " ";
		}
		try {
			Files.writeString(PRIMEPATH, primeStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Lee de un archivo la lista de numeros primos.
	 */

	private static void readPrimes() {
		primes = new ArrayList<>();
		String primeStr = "";
		try {
			primeStr = Files.readString(PRIMEPATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] primeStrArray = primeStr.split(" ");

		for (String s : primeStrArray) {
			Integer n = Integer.parseInt(s);
			primes.add(n);
		}
	}
}
