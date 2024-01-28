package mainPackage.objekat;

import java.util.Random;

import mainPackage.Main;
import mainPackage.grafika.Kvadrat;
import mainPackage.grafika.Prikazivac;
import mainPackage.igra.Igra;

/**
 * Klasa koja predstavlja objekat u igri "2048". Objekti imaju svoje koordinate,
 * dimenzije, vrijednost, brzinu kretanja, te informacije o kretanju i brisanju.
 */
public class Objekat {
	/**
	 * Pozicija objekta po X koordinati na ekranu.
	 */
	public double x;

	/**
	 * Pozicija objekta po Y koordinati na ekranu.
	 */
	public double y;

	/**
	 * Sirina objekta.
	 */
	public int sirina;

	/**
	 * Visina objekta.
	 */
	public int visina;

	/**
	 * Grafika kvadrata koja predstavlja objekat.
	 */
	public Kvadrat kvadrat;

	/**
	 * Vrijednost objekta (može biti 2, 4, 8, ..., 2048).
	 */
	public int vrijednost;

	/**
	 * Brzina kretanja objekta na ekranu.
	 */
	public int brzina = 30;

	/**
	 * Promenljiva koja oznacava da li je objekat u pokretu.
	 */
	public boolean kretanje = false;

	/**
	 * Promenljiva koja oznacava da li je objekat oznacen za brisanje.
	 */
	public boolean brisi = false;

	/**
	 * Promenljiva koja oznacava da li je objekat pomjeran u trenutnom koraku.
	 */
	public boolean pomjereno = false;

	/**
	 * Generator slucajnih brojeva.
	 */
	Random rand = new Random();


    /**
     * Konstruktor za inicijalizaciju objekta na zadanim koordinatama.
     * Postavlja se i vrijednost objekta, nasumice izborom izmedju 2 i 4.
     *
     * @param x Pocetna x koordinata objekta.
     * @param y Pocetna y koordinata objekta.
     */
    public Objekat(double x, double y) {
        this.x = x;
        this.y = y;
        this.vrijednost = (rand.nextBoolean() ? 2 : 4);
        kreirajKvadrat();
        this.sirina = kvadrat.sirina;
        this.visina = kvadrat.visina;
    }

    /**
     * Metoda za kreiranje kvadrata na osnovu vrijednosti objekta.
     * Boje kvadrata se određuju prema određenim vrijednostima.
     */
    public void kreirajKvadrat() {
        int boja;
        switch (this.vrijednost) {
            case 2:
                boja = 0xefe5db;
                break;
            case 4:
                boja = 0xece0c8;
                break;
            case 8:
                boja = 0xf1b078;
                break;
            case 16:
                boja = 0xeb8c52;
                break;
            case 32:
                boja = 0xf57c5f;
                break;
            case 64:
                boja = 0xec5630;
                break;
            case 128:
                boja = 0xece0c8;
                break;
            case 256:
                boja = 0xf2d86a;
                break;
            case 512:
                boja = 0xe5bf2d;
                break;
            case 1024:
                boja = 0xe2b913;
                break;
            case 2048:
                boja = 0xedc22e;
                break;
            default:
                boja = 0xefe5db; // Default boja
        }

        this.kvadrat = new Kvadrat(100, 100, boja);
    }

    /**
     * Provjerava da li objekat može da se pomjeri na novu poziciju.
     *
     * @return True ako objekat može da se pomjeri, inace False.
     */
    public boolean mozeSePomjeriti() {
        if (x < 0 || x + sirina > Main.sirina - 200 || y < 0 || y + visina > Main.visina) {
            return false;
        }

        for (int i = 0; i < Igra.objekti.size(); i++) {
            Objekat o = Igra.objekti.get(i);
            if (this == o || vrijednost == o.vrijednost) {
                continue;
            }

            if (x + sirina > o.x && x < o.x + o.sirina && y + visina > o.y && y < o.y + o.visina) {
                return false;
            }
        }

        return true;
    }

    /**
     * Azurira poziciju objekta na osnovu smjera kretanja.
     */
    public void azuriraj() {
        if (Igra.kretanje) {
            if (!pomjereno) {
                pomjereno = true;
            }

            if (mozeSePomjeriti()) {
                kretanje = true;
            }

            if (kretanje) {
                if (Igra.smjer == 0) {
                    x -= brzina;
                }
                if (Igra.smjer == 1) {
                    x += brzina;
                }
                if (Igra.smjer == 2) {
                    y -= brzina;
                }
                if (Igra.smjer == 3) {
                    y += brzina;
                }
            }

            if (!mozeSePomjeriti()) {
                kretanje = false;
                x = Math.round(x / 100) * 100;
                y = Math.round(y / 100) * 100;
            }
        }
    }

    /**
     * Prikazuje objekat na ekranu koristeći Prikazivac klasu.
     */
    public void prikazi() {
        Prikazivac.prikaziKvadrat(kvadrat, (int) x, (int) y);
    }
}
