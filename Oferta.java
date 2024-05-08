import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Oferta {
	int k, n;
	ArrayList<Double> preturi;
	ArrayList<ArrayList<Double>> dp;

	public void citire() throws FileNotFoundException {
		MyScanner sc = new MyScanner(new FileReader("oferta.in"));
		n = sc.nextInt();
		k = sc.nextInt();
		preturi = new ArrayList<Double>();
		dp = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < n; i++) {
			preturi.add(sc.nextDouble());
		}
	}

	public void afisare(double rezultat) {
		try {
			FileWriter fw = new FileWriter("oferta.out");
			fw.write(String.format("%.1f", rezultat));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// functii pentru calculul pretului produselor luate la oferta
	public double oferta(double pret1, double pret2) {
		return Math.max(pret1, pret2) + Math.min(pret1, pret2) / 2;
	}

	public double oferta(double pret1, double pret2, double pret3) {
		double pret_min = Math.min(pret1, Math.min(pret2, pret3));
		return pret1 + pret2 + pret3 - pret_min;
	}

	public static void main(String[] args) {
		Oferta oferta = new Oferta();
		try {
			oferta.citire();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// cazurile de baza (i = 0, 1, 2)
		oferta.dp.add(new ArrayList<>());

		// primul produs poate fi luat doar independent
		oferta.dp.get(0).add(oferta.preturi.get(0));

		if (oferta.n == 1) {
			if (oferta.dp.get(oferta.n - 1).size() < oferta.k) {
				oferta.afisare(-1);
				return;
			}
			oferta.afisare(oferta.dp.get(0).get(oferta.k - 1));
			return;
		}

		oferta.dp.add(new ArrayList<>());

		// pentru primele 2, se pot lua la oferta sau independent
		oferta.dp.get(1).add(oferta.oferta(oferta.preturi.get(0), oferta.preturi.get(1)));
		oferta.dp.get(1).add(oferta.preturi.get(0) + oferta.preturi.get(1));

		if (oferta.n == 2) {
			if (oferta.dp.get(oferta.n - 1).size() < oferta.k) {
				oferta.afisare(-1);
				return;
			}
			oferta.afisare(oferta.dp.get(1).get(oferta.k - 1));
			return;
		}

		oferta.dp.add(new ArrayList<>());

		// pentru primele 3, avem cele 2 cazuri pentru primele 2 + al 3-lea independent
		for (double pret_dp : oferta.dp.get(1)) {
			oferta.dp.get(2).add(pret_dp + oferta.preturi.get(2));
		}

		// si cazul in care se iau toate 3 la oferta, sau toate 3 independent
		oferta.dp.get(2).add(oferta.oferta(
			oferta.preturi.get(0), oferta.preturi.get(1), oferta.preturi.get(2)));
		oferta.dp.get(2).add(oferta.preturi.get(0)
			+ oferta.oferta(oferta.preturi.get(1), oferta.preturi.get(2)));

		// sortez pentru a pastra conditia de ordine crescatoare necesara in recurenta
		Collections.sort(oferta.dp.get(2));
		for (int i = 3; i < oferta.n; i++) {

			oferta.dp.add(new ArrayList<Double>());

			int index1 = 0;
			int index2 = 0;
			int index3 = 0;

			double d1_current = oferta.dp.get(i - 1).get(index1);
			double d2_current = oferta.dp.get(i - 2).get(index2);
			double d3_current = oferta.dp.get(i - 3).get(index3);

			while (oferta.dp.get(i).size() < oferta.k) {
				double pret_posibil = Double.MAX_VALUE;

				// retin 'sursa' de unde am construit pretul minim actual
				int sursa = -1;

				// cele 3 recurente (voi alege minimul dintre preturile posibile)
				if (index1 < oferta.dp.get(i - 1).size()
					&& (d1_current + oferta.preturi.get(i) < pret_posibil)) {
					pret_posibil = d1_current + oferta.preturi.get(i);
					sursa = i - 1;
				}

				if (index2 < oferta.dp.get(i - 2).size()
					&& (d2_current + oferta.oferta(oferta.preturi.get(i - 1),
						oferta.preturi.get(i)) < pret_posibil)) {
					pret_posibil = d2_current
						+ oferta.oferta(oferta.preturi.get(i - 1), oferta.preturi.get(i));
					sursa = i - 2;
				}

				if (index3 < oferta.dp.get(i - 3).size()
					&& (d3_current + oferta.oferta(oferta.preturi.get(i - 2),
						oferta.preturi.get(i - 1), oferta.preturi.get(i)) < pret_posibil)) {
					pret_posibil = d3_current 
						+ oferta.oferta(oferta.preturi.get(i - 2), oferta.preturi.get(i - 1),
							oferta.preturi.get(i));
					sursa = i - 3;
				}

				// daca nu mai pot adauga preturi noi, inseamna ca nu exista k preturi distincte
				if (sursa == -1) {
					break;
				}

				// conditia de a nu adauga preturi duplicate
				if (oferta.dp.get(i).isEmpty()
					|| (oferta.dp.get(i).get(oferta.dp.get(i).size() - 1) != pret_posibil)) {
					oferta.dp.get(i).add(pret_posibil);
				}

				// avansez in vectorul corespunzator in functie de sursa
				if (sursa == i - 1) {
					index1++;
					if (index1 < oferta.dp.get(i - 1).size()) {
						d1_current = oferta.dp.get(i - 1).get(index1);
					}
				} else if (sursa == i - 2) {
					index2++;
					if (index2 < oferta.dp.get(i - 2).size()) {
						d2_current = oferta.dp.get(i - 2).get(index2);
					}
				} else {
					index3++;
					if (index3 < oferta.dp.get(i - 3).size()) {
						d3_current = oferta.dp.get(i - 3).get(index3);
					}
				}
				;
			}
		}

		if (oferta.dp.get(oferta.n - 1).size() < oferta.k) {
			oferta.afisare(-1);
			return;
		}

		oferta.afisare(oferta.dp.get(oferta.n - 1).get(oferta.k - 1));
	}
}
