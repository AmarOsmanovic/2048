package mainPackage.grafika;

import mainPackage.Main;

/**
 * Prikazivac se koristi za manipulaciju i prikazivanje grafike u igri.
 * Ova klasa sadrzi metode za prikazivanje pozadine i kvadrata na ekranu.
 */
public class Prikazivac {
	/**
	 * Sirina ekrana, smanjena za 200 piksela.
	 */
	public static int sirina = Main.sirina - 200;

	/**
	 * Visina ekrana.
	 */
	public static int visina = Main.visina;

	/**
	 * Niz piksela koji se koristi za prikazivanje grafike.
	 */
	public static int[] pikseli = new int[sirina * visina];


    /**
     * Postavlja piksele na određenu boju kako bi se prikazala pozadina.
     */
    public static void prikaziPozadinu() {
        for (int y = 0; y < visina; y++) {
            for (int x = 0; x < sirina; x++) {
                pikseli[x + y * sirina] = 0xfff4f4f4;

                if (x % 100 < 3 || x % 100 > 97 || y % 100 < 3 || y % 100 > 97) {
                    // Oboji rubove drugacijom bojom
                    pikseli[x + y * sirina] = 0xffcccccc;
                }
            }
        }
    }

    /**
     * Prikazuje kvadrat na ekranu.
     *
     * @param kvadrat Kvadrat koji će biti prikazan.
     * @param xp      X koordinata gornjeg lijevog kuta kvadrata.
     * @param yp      Y koordinata gornjeg lijevog kuta kvadrata.
     */
    public static void prikaziKvadrat(Kvadrat kvadrat, int xp, int yp) {
        if (xp < -kvadrat.sirina || xp >= sirina || yp < -kvadrat.visina || yp >= visina) {
            return;
        }
        for (int y = 0; y < kvadrat.visina; y++) {
            int yy = y + yp;
            if (yy < 0 || yy >= visina) continue;
            for (int x = 0; x < kvadrat.sirina; x++) {
                int xx = x + xp;
                if (xx < 0 || xx >= sirina) continue;
                int boja = kvadrat.pikseli[x + y * kvadrat.sirina];
                if (boja != 0xffff00ff) {
                    // Postavi piksel samo ako nije boja ruba kvadrata
                    pikseli[xx + yy * sirina] = boja;
                }
            }
        }
    }
}
