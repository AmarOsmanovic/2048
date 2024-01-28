package mainPackage.grafika;

/**
 * Predstavlja kvadrat koji se koristi u grafickom prikazu igre.
 * Kvadrat ima odredjenu sirinu, visinu i piksele koji ga cine.
 * Rubovi kvadrata su oznaceni drugacijom bojom od unutrasnjosti.
 */
public class Kvadrat {
	/**
	 * Sirina ekrana.
	 */
	public int sirina;

	/**
	 * Visina ekrana.
	 */
	public int visina;

	/**
	 * Niz piksela koji se koristi za prikazivanje grafike.
	 */
	public int[] pikseli;


    /**
     * Konstruktor za inicijalizaciju kvadrata sa zadanim dimenzijama i bojom.
     *
     * @param sirina sirina kvadrata.
     * @param visina Visina kvadrata.
     * @param boja   Boja unutrasnjosti kvadrata.
     */
    public Kvadrat(int sirina, int visina, int boja) {
        this.visina = visina;
        this.sirina = sirina;
        this.pikseli = new int[sirina * visina];

        for (int y = 0; y < visina; y++) {
            for (int x = 0; x < sirina; x++) {
                if (x % 100 < 3 || x % 100 > 97 || y % 100 < 3 || y % 100 > 97) {
                    // Oznaci rubove drugacijom bojom
                    pikseli[x + y * sirina] = 0xffff00ff; 
                } else {
                    // Unutrasnjost kvadrata
                    pikseli[x + y * sirina] = boja;
                }
            }
        }
    }
}
