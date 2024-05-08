
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Compresie {

	int n, m;
	ArrayList<Long> a, b;

	public void citire() throws FileNotFoundException {
		MyScanner sc = new MyScanner(new FileReader("compresie.in"));
		a = new ArrayList<Long>();
		b = new ArrayList<Long>();
		n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			a.add(sc.nextLong());
		}
		m = sc.nextInt();
		for (int i = 0; i < m; i++) {
			b.add(sc.nextLong());
		}
	}

	public static void main(String[] args) {
		Compresie compresie = new Compresie();
		try {
			compresie.citire();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// index_a si index_b sunt folosite pentru a parcurge vectorii a si b simultan
		int index_a = 0;
		int index_b = 0;
		int k = 0;

		// cat timp nu am terminat de parcurs niciunul din vectori
		while (index_a < compresie.n && index_b < compresie.m) {
			// daca elementul curent din a este mai mic decat cel din b
			// facem compresie in a
			if (compresie.a.get(index_a) < compresie.b.get(index_b)) {
				index_a++;
				compresie.a.set(index_a, compresie.a.get(index_a) + compresie.a.get(index_a - 1));
			} else if (compresie.a.get(index_a) > compresie.b.get(index_b)) {
				// altfel facem compresie in b
				index_b++;
				compresie.b.set(index_b, compresie.b.get(index_b) + compresie.b.get(index_b - 1));
			} else {
				// daca elementele curente din cei doi vectori sunt egale,
				// acesta va fi un element in vectorul final, il contorizam si mergem mai departe
				k++;
				index_a++;
				index_b++;
			}
		}

		if (index_a != compresie.n || index_b != compresie.m) {
			k = -1;
		}

		try {
			FileWriter fw = new FileWriter("compresie.out");
			fw.write(k + "\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
