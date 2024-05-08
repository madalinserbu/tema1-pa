import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Servere {
	int n;

	public class Server {
		long putere;
		long alimentare;

		public Server(long putere, long alimentare) {
			this.putere = putere;
			this.alimentare = alimentare;
		}
	}

	ArrayList<Server> servere;

	public void citire() throws FileNotFoundException {
		MyScanner sc = new MyScanner(new FileReader("servere.in"));
		n = sc.nextInt();
		servere = new ArrayList<Server>();
		ArrayList<Long> putere = new ArrayList<Long>();
		ArrayList<Long> alimentare = new ArrayList<Long>();
		for (int i = 0; i < n; i++) {
			putere.add(sc.nextLong());
		}
		for (int i = 0; i < n; i++) {
			alimentare.add(sc.nextLong());
		}

		for (int i = 0; i < n; i++) {
			servere.add(new Server(putere.get(i), alimentare.get(i)));
		}
	}

	// cautare binara pentru pragul de alimentare optim
	public double alimentare_optima(long alimnetare_minima, long alimentare_maxima) {
		long alimentare_curenta = alimnetare_minima + (alimentare_maxima - alimnetare_minima) / 2;
		long putere_minima = get_power(alimentare_curenta, servere.get(0));
		boolean e_alimentat_la_fix = false;
		boolean e_supraalimentat = false;
		boolean e_subalimentat = false;

		// cautam puterea minima si retinem in ce caz ne aflam
		for (int i = 0; i < n; i++) {
			long putere = get_power(alimentare_curenta, servere.get(i));
			if (putere < putere_minima) {
				putere_minima = putere;
				e_alimentat_la_fix = false;
				e_supraalimentat = false;
				e_subalimentat = false;
			}
			if (putere == putere_minima) {
				if (alimentare_curenta == servere.get(i).alimentare) {
					e_alimentat_la_fix = true;
				} else if (alimentare_curenta < servere.get(i).alimentare) {
					e_subalimentat = true;
				} else {
					e_supraalimentat = true;
				}
			}
		}


		// in functie de caz continuam sau nu cautarea binara
		if (e_alimentat_la_fix) {
			return (double) alimentare_curenta;
		}

		if (e_supraalimentat && e_subalimentat) {
			return (double) alimentare_curenta;
		}

		if (e_supraalimentat) {
			if (alimentare_curenta == alimnetare_minima) {
				return alimentare_curenta - 0.5;
			}
			return alimentare_optima(alimnetare_minima, alimentare_curenta - 1);
		}

		if (e_subalimentat) {
			if (alimentare_curenta == alimentare_maxima) {
				return alimentare_curenta + 0.5;
			}
			return alimentare_optima(alimentare_curenta + 1, alimentare_maxima);
		}

		return -1;
	}

	long get_power(long alimentare, Server server) {
		return (long) (server.putere - Math.abs(alimentare - server.alimentare));
	}

	double get_power_double(double alimentare, Server server) {
		return server.putere - Math.abs(alimentare - server.alimentare);
	}

	public static void main(String[] args) {
		Servere servere = new Servere();
		try {
			servere.citire();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		long alimnetare_minima = Collections.min(servere.servere, new Comparator<Servere.Server>() {
			public int compare(Servere.Server o1, Servere.Server o2) {
				return Long.compare(o1.alimentare, o2.alimentare);
			}
		}).alimentare;

		long alimentare_maxima = Collections.max(servere.servere, new Comparator<Servere.Server>() {
			public int compare(Servere.Server o1, Servere.Server o2) {
				return Long.compare(o1.alimentare, o2.alimentare);
			}
		}).alimentare;

		double alimentare_optima = servere.alimentare_optima(alimnetare_minima, alimentare_maxima);

		// calculam puterea minima pentru alimentarea gasita
		double putere_minima = servere.get_power_double(alimentare_optima, servere.servere.get(0));
		for (int i = 0; i < servere.n; i++) {
			double putere = servere.get_power_double(alimentare_optima, servere.servere.get(i));
			if (putere < putere_minima) {
				putere_minima = putere;
			}
		}

		try {
			FileWriter fw = new FileWriter("servere.out");
			fw.write(String.format("%.1f", putere_minima));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}