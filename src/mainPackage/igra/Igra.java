package mainPackage.igra;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mainPackage.Main;
import mainPackage.grafika.Prikazivac;
import mainPackage.objekat.Objekat;
import mainPackage.unos.Tastatura;

/**
 * Klasa predstavlja logiku igre "2048". Sadrzi listu objekata, bodove, metode za azuriranje, prikaz i provjeru zavrsetka igre.
 */
public class Igra {
	/**
	 * Lista objekata u igri.
	 */
	public static List<Objekat> objekti;

	/**
	 * Lista najboljih ostvarenih bodova.
	 */
	public List<Integer> najboljiBodovi = new ArrayList<>();

	/**
	 * Promenljiva koja oznacava da li se objekti krecu.
	 */
	public static boolean kretanje = false;

	/**
	 * Promenljiva koja oznacava da li su objekti pomjereni u trenutnom koraku.
	 */
	public static boolean pomjereno = true;

	/**
	 * Promenljiva koja oznacava da li se nesto trenutno krece na ekranu.
	 */
	public static boolean nestoSeKrece = false;

	/**
	 * Smjer kretanja objekata (0 - lijevo, 1 - desno, 2 - gore, 3 - dole).
	 */
	public static int smjer = 0;

	/**
	 * Najveci ostvareni bod u igri.
	 */
	public int najveci = 0;

	/**
	 * Ukupni bodovi u igri.
	 */
	public int bodovi = 0;

	/**
	 * Promenljiva koja oznacava da li postoji unos korisnika.
	 */
	boolean postojiUnos = true;

	/**
	 * Generator slucajnih brojeva.
	 */
	private Random rand = new Random();



    /**
     * Konstruktor za inicijalizaciju igre.
     */
    public Igra() {
        inicijalizacija();
    }

    /**
     * Inicijalizacija igre, postavljanje pocetnih vrijednosti.
     */
    public void inicijalizacija() {
        objekti = new ArrayList<Objekat>();
        kretanje = false;
        pomjereno = true;
        nestoSeKrece = false;
        bodovi = 0;
        postojiUnos = true;
        dodaj();
    }

    /**
     * Azuriranje logike igre.
     */
    public void azuriraj() {
        if (Tastatura.tipkaPritisnuta(KeyEvent.VK_R)) {
            inicijalizacija();
            najveci = 0;
        }

        for (int i = 0; i < objekti.size(); i++) {
            objekti.get(i).azuriraj();
        }

        povecajVrijednost();
        kretanje();
        jeKraj();
    }

    /**
     * Povecava vrijednost objekta ako su dva ista objekta susjedna.
     * Takodjer provodi brisanje objekata koji se spajaju.
     */
    private void povecajVrijednost() {
        for (int i = 0; i < objekti.size(); i++) {
            for (int j = 0; j < objekti.size(); j++) {
                if (i == j) continue;

                if (objekti.get(i).x == objekti.get(j).x && objekti.get(i).y == objekti.get(j).y
                        && !objekti.get(i).brisi && !objekti.get(j).brisi) {
                    objekti.get(j).brisi = true;
                    objekti.get(i).vrijednost *= 2;
                    if ((objekti.get(i).vrijednost * 2) > najveci) {
                        najveci = objekti.get(i).vrijednost;
                    }
                    izracunajBodove(objekti.get(i).vrijednost);
                    objekti.get(i).kreirajKvadrat();
                }
            }
        }

        for (int i = 0; i < objekti.size(); i++) {
            if (objekti.get(i).brisi) {
                objekti.remove(i);
            }
        }
    }

    /**
     * Izracunava bodove na temelju vrijednosti objekta.
     *
     * @param vrijednost Vrijednost objekta.
     * @return Ukupni broj bodova.
     */
    public int izracunajBodove(int vrijednost) {
        bodovi += vrijednost;
        return bodovi;
    }

    /**
     * Dodaje novi objekat u igru.
     */
    private void dodaj() {
        if (objekti.size() == 16) {
            return;
        }

        boolean dostupno = false;
        int x = 0, y = 0;

        while (!dostupno) {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
            boolean jeDostupno = true;

            for (int i = 0; i < objekti.size(); i++) {
                if (objekti.get(i).x / 100 == x && objekti.get(i).y / 100 == y) {
                    jeDostupno = false;
                }
            }

            if (jeDostupno) {
                dostupno = true;
            }
        }
        objekti.add(new Objekat(x * 100, y * 100));
    }

    /**
     * Obrada korisnickog unosa i pokretanje objekata.
     */
    private void kretanje() {
        nestoSeKrece = false;

        for (int i = 0; i < objekti.size(); i++) {
            if (objekti.get(i).kretanje) {
                nestoSeKrece = true;
            }
        }

        if (!nestoSeKrece) {
            kretanje = false;
            for (int i = 0; i < objekti.size(); i++) {
                objekti.get(i).pomjereno = false;
            }
        }

        if (!kretanje && pomjereno) {
            dodaj();
            pomjereno = false;
        }

        if (!kretanje && !pomjereno) {
            if (Tastatura.tipkaPritisnuta(KeyEvent.VK_A)) {
                pomjereno = true;
                kretanje = true;
                smjer = 0;
            } else if (Tastatura.tipkaPritisnuta(KeyEvent.VK_D)) {
                pomjereno = true;
                kretanje = true;
                smjer = 1;
            } else if (Tastatura.tipkaPritisnuta(KeyEvent.VK_W)) {
                pomjereno = true;
                kretanje = true;
                smjer = 2;
            } else if (Tastatura.tipkaPritisnuta(KeyEvent.VK_S)) {
                pomjereno = true;
                kretanje = true;
                smjer = 3;
            }
        }
    }

    /**
     * Prikazuje elemente igre.
     */
    public void prikazi() {
        Prikazivac.prikaziPozadinu();

        for (int i = 0; i < objekti.size(); i++) {
            objekti.get(i).prikazi();
        }

        for (int i = 0; i < Main.pikseli.length; i++) {
            Main.pikseli[i] = Prikazivac.pikseli[i];
        }
    }

    /**
     * Prikazuje tekst na ekranu, ukljucujuci vrijednosti objekata.
     *
     * @param g Grafika za crtanje teksta.
     */
    public void prikaziTekst(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Lato", 0, 50));
        g.setColor(Color.black);

        for (int i = 0; i < objekti.size(); i++) {
            Objekat o = objekti.get(i);
            String s = o.vrijednost + "";
            int sw = (int) (g.getFontMetrics().stringWidth(s) / 2);
            g.drawString(s, (int) (o.x + o.sirina / 2 - sw), (int) (o.y + o.visina / 2 + 18));
        }
    }

    /**
     * Provjerava zavrsetak igre.
     *
     * @return True ako je igra zavrsena, inace false.
     */
    public boolean jeKraj() {
        if (najveci == 2048) return true;

        boolean slobodnaPolja = false;
        boolean mogucnostSpajanja = false;

        // Provjeri dostupne poteze 
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!zauzeto(i, j)) {
                    slobodnaPolja = true;
                    break;
                }
            }
            if (slobodnaPolja) {
                break;  // Nema potrebe za daljnjom provjerom ako je pronadjen dostupan potez
            }
        }

        // Provjeri mogucnost spajanja 
        for (int i = 0; i < objekti.size(); i++) {
            Objekat trenutniObjekat = objekti.get(i);

            // Provjeri susjedne pozicije za jednake vrijednosti 
            if (provjeriMogucnostSpajanja(trenutniObjekat, -1, 0) ||
                provjeriMogucnostSpajanja(trenutniObjekat, 1, 0) ||
                provjeriMogucnostSpajanja(trenutniObjekat, 0, -1) || 
                provjeriMogucnostSpajanja(trenutniObjekat, 0, 1)) {
                mogucnostSpajanja = true;
                break;  // Izlaz iz petlje kada je pronadjena mogucnost spajanja
            }
        }
        
        if (!slobodnaPolja && !mogucnostSpajanja) {
            if (postojiUnos && najboljiBodovi.size() < 5) {
                najboljiBodovi.add(bodovi);
                postojiUnos = false;
                Collections.sort(najboljiBodovi, Collections.reverseOrder());
            } else if (postojiUnos && najboljiBodovi.size() == 5) {
                for (int i = 0; i < najboljiBodovi.size() - 1; i++) {
                    if (bodovi > najboljiBodovi.get(i)) {
                        najboljiBodovi.add(bodovi);
                        Collections.sort(najboljiBodovi, Collections.reverseOrder());
                        najboljiBodovi.remove(najboljiBodovi.size() - 1);                	    
                        break;
                    }
                }

                postojiUnos = false;
                Collections.sort(najboljiBodovi, Collections.reverseOrder());
            }

            return true;
        } else return false;
    }

    /**
     * Provjerava mogucnost spajanja s drugim objektom.
     *
     * @param trenutniObjekat Trenutni objekat.
     * @param offsetX         Pomak po X osi.
     * @param offsetY         Pomak po Y osi.
     * @return True ako je moguce spojiti, inace false.
     */
    private boolean provjeriMogucnostSpajanja(Objekat trenutniObjekat, int offsetX, int offsetY) {
        int x = (int) (trenutniObjekat.x / 100 + offsetX);
        int y = (int) (trenutniObjekat.y / 100 + offsetY);

        for (Objekat o : objekti) {
            if (o.x / 100 == x && o.y / 100 == y && o.vrijednost == trenutniObjekat.vrijednost && !o.brisi) {
                return true;
            }
        }
        return false;
    }

    /**
     * Provjerava zauzetost odredjenog polja.
     *
     * @param x Koordinata X.
     * @param y Koordinata Y.
     * @return True ako je polje zauzeto, inace false.
     */
    private boolean zauzeto(int x, int y) {
        for (Objekat o : objekti) {
            if (o.x / 100 == x && o.y / 100 == y) {
                return true;
            }
        }
        return false;
    }
}
