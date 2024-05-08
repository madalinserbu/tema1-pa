import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Criptat {
	int n;
	ArrayList<String> cuvinte;
	ArrayList<Character> litere;

	public void citire() throws FileNotFoundException {
		MyScanner sc = new MyScanner(new FileReader("criptat.in"));
		n = sc.nextInt();
		cuvinte = new ArrayList<String>();
		litere = new ArrayList<Character>();
		for (int i = 0; i < n; i++) {
			cuvinte.add(sc.next());
			for (int j = 0; j < cuvinte.get(i).length(); j++) {
				if (!litere.contains(cuvinte.get(i).charAt(j))) {
					litere.add(cuvinte.get(i).charAt(j));
				}
			}
		}
	}

	// descrie un cuvant in care litera dominanta din parola
	// apare de mai putin de l/2 ori
	public class CuvantNedominant {
		int ap_dominanta;
		int lungime;
		// costul e dat de numarul de litere 'in plus',
		// care nu sunt anulate de litere dominante
		int cost;

		CuvantNedominant(int ap_dominanta, int lungime) {
			this.ap_dominanta = ap_dominanta;
			this.lungime = lungime;
			cost = lungime - 2 * ap_dominanta;
		}
	}

	public static void main(String[] args) {
		System.out.println("Criptat");

		Criptat criptat = new Criptat();
		try {
			criptat.citire();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int max = 0;

		for (Character c : criptat.litere) {
			// cautam lungimea maxima pe care o putem obtine pentru litera dominanta c
			int lungime = 0;
			int ap_dominanta = 0;
			ArrayList<CuvantNedominant> cuvinte_nedominante = new ArrayList<CuvantNedominant>();
			for (String cuvant : criptat.cuvinte) {
				int ap = 0;
				for (int i = 0; i < cuvant.length(); i++) {
					if (cuvant.charAt(i) == c) {
						ap++;
					}
				}

				// daca litera e dominanta in cuvant il punem in parola (la fel si daca nu
				// afecteaza dominanta)
				if (ap * 2 >= cuvant.length()) {
					lungime += cuvant.length();
					ap_dominanta += ap;
				} else {
					cuvinte_nedominante.add(criptat.new CuvantNedominant(ap, cuvant.length()));
				}
			}

			if (ap_dominanta * 2 <= lungime) {
				// daca deja litera nu e dominanta in parola inseamna ca nu se poate obtine o
				// parola valida cu ea
				continue;
			}

			// salvam lungimea obtinuta pana acum daca e cazul
			max = Math.max(max, lungime);

			// incercam sa adaugam cuvinte nedominante
			// dp[i][j] - lungimea adaugata folosind primele i cuvinte nedominante, cu
			// costul maxim j
			int cost_disponibil = 2 * ap_dominanta - lungime - 1;
			int[][] dp = new int[cuvinte_nedominante.size() + 1][cost_disponibil + 1];

			for (int j = 0; j < cost_disponibil + 1; j++) {
				dp[0][j] = 0;
			}

			for (int i = 0; i < cuvinte_nedominante.size() + 1; i++) {
				dp[i][0] = 0;
			}

			for (int i = 1; i < cuvinte_nedominante.size() + 1; i++) {
				for (int j = 1; j < cost_disponibil + 1; j++) {
					if (cuvinte_nedominante.get(i - 1).cost <= j) {
						dp[i][j] = Math.max(dp[i - 1][j],
							dp[i - 1][j - cuvinte_nedominante.get(i - 1).cost]
								+ cuvinte_nedominante.get(i - 1).lungime);
					} else {
						dp[i][j] = dp[i - 1][j];
					}
				}
			}

			lungime += dp[cuvinte_nedominante.size()][cost_disponibil];

			max = Math.max(max, lungime);
		}

		try {
			FileWriter out = new FileWriter("criptat.out");
			out.write(max + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
