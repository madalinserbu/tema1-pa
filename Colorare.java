import java.io.FileReader;
import java.io.FileWriter;

public class Colorare {
	long mod = 1000000007;

	// functie pentru ridicare la putere folosind divide et impera
	public long ridicare_la_putere(long baza, long exponent) {
		if (exponent == 0) {
			return 1;
		}

		if (exponent % 2 == 0) {
			long x = ridicare_la_putere(baza, exponent / 2);
			return (x * x) % mod;
		} else {
			return (baza * ridicare_la_putere(baza, exponent - 1)) % mod;
		}
	}

	public static void main(String[] args) {
		try {
			int k;
			long x;
			char t = 0;
			char last_t;
			long tablouri = 0;
			MyScanner sc = new MyScanner(new FileReader("colorare.in"));
			k = sc.nextInt();
			Colorare colorare = new Colorare();

			for (int i = 0; i < k; i++) {
				x = sc.nextLong();
				last_t = t;
				t = sc.next().charAt(0);
				if (t == 'H') {
					if (i == 0) {
						tablouri = 6; // putem aseza primele dreptunghiuri orizontal in 6 moduri
					} else {
						if (last_t == 'H') {
							// o asezare orizontala dupa una orizontala se poate face in 3 moduri
							tablouri = tablouri * 3;
						} else {
							// o asezare orizontala dupa una verticala se poate face in 2 moduri
							tablouri = tablouri * 2;
						}
					}
					// pentru restul dreptunghiurilor orizontale vom fi in cazul
					// orizontal-orizontal, deci se vor face inmultiri repetate cu 3
					tablouri = tablouri * colorare.ridicare_la_putere(3, x - 1) % colorare.mod;

				} else {
					if (i == 0) {
						// putem aseza primele dreptunghiuri vertical in 3 moduri
						tablouri = 3;
					} else {
						if (last_t == 'H') {
							// o asesare verticala dupa una orizontala se poate face in 1 mod
							tablouri = tablouri * 1;
						} else {
							// o asesare verticala dupa una verticala se poate face in 2 moduri
							tablouri = tablouri * 2;
						}
					}
					// pentru restul dreptunghiurilor verticale vom fi in cazul vertical-vertical,
					// deci se vor face inmultiri repetate cu 2
					tablouri = tablouri * colorare.ridicare_la_putere(2, x - 1) % colorare.mod;
				}
			}

			FileWriter out = new FileWriter("colorare.out");
			out.write(tablouri + "\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
