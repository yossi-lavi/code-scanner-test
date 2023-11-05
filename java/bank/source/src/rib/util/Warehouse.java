package rib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Warehouse {
	Scanner scanner = new Scanner(System.in);

	public void exit() {
		String answer;
		do {
			System.out.println("\nSunteti sigur ca doriti sa parasiti contul?");
			System.out.print("Raspuns (da sau nu): ");
			answer = scanner.next();
			switch (answer.toLowerCase()) {
			case "da": {
				System.err.println("La revedere!");
				System.exit(0);
				break;
			}
			case "nu": {
				break;
			}
			default: {
				System.err.println("Optiunea este invalida!");
			}
			}
		} while (!answer.toLowerCase().equals("nu"));
	}

	public void hello() {
		Calendar calendar = Calendar.getInstance();
		int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		if (timeOfDay >= 0 && timeOfDay < 12) {
			System.out.println("BUNA DIMINEATA!\n");
		} else if (timeOfDay >= 12 && timeOfDay < 16) {
			System.out.println("BUNA ZIUA!\n");
		} else if (timeOfDay >= 16 && timeOfDay < 24) {
			System.out.println("BUNA SEARA!\n");
			//System.out.println();
		}
	}

	public void progressBar() {
		char[] animationChars = new char[] { '|', '/', '-', '\\' };
		for (int i = 0; i <= 100; i++) {
			System.out.print("Se verifica: " + i + "% " + animationChars[i % 4] + "\r");

			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.print("Verificare: ");
	}

}
