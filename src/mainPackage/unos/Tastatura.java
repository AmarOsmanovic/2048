package mainPackage.unos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tastatura klasa se koristi za pracenje stanja tastature (koje tipke su pritisnute, otpustene, itd.).
 */
public class Tastatura implements KeyListener {
	/** Nizovi za pracenje stanja tastature */
	public static boolean[] tipke = new boolean[120];
	/** Nizovi za pracenje stanja tastature */
	public static boolean[] prethodneTipke = new boolean[120];

	/** Promenljive koje oznacavaju pritisnute tipke */
	public static boolean lijevo, desno, gore, dole, restart;


    /**
     * Azurira prethodno stanje tipki kako bi se pravilno pratilo stanje tastature.
     */
    public void azuriraj() {
        for (int i = 0; i < tipke.length; i++) {
            prethodneTipke[i] = tipke[i];
        }

        lijevo = tipke[KeyEvent.VK_A] || tipke[KeyEvent.VK_LEFT];
        desno = tipke[KeyEvent.VK_D] || tipke[KeyEvent.VK_RIGHT];
        gore = tipke[KeyEvent.VK_W] || tipke[KeyEvent.VK_UP];
        dole = tipke[KeyEvent.VK_S] || tipke[KeyEvent.VK_DOWN];
        restart = tipke[KeyEvent.VK_R];
    }

    /**
     * Provjerava je li odredjena tipka pritisnuta u trenutnom stanju tastature.
     *
     * @param kod Kod tipke koja se provjerava.
     * @return True ako je tipka pritisnuta, inace false.
     */
    public static boolean tipka(int kod) {
        return tipke[kod];
    }

    /**
     * Provjerava je li odredjena tipka pritisnuta u trenutnom stanju tastature, ali nije bila pritisnuta u prethodnom stanju.
     *
     * @param kod Kod tipke koja se provjerava.
     * @return True ako je tipka pritisnuta, inace false.
     */
    public static boolean tipkaPritisnuta(int kod) {
        return tipke[kod] && !prethodneTipke[kod];
    }

    /**
     * Provjerava je li odredjena tipka pustena u trenutnom stanju tastature, a bila je pritisnuta u prethodnom stanju.
     *
     * @param kod Kod tipke koja se provjerava.
     * @return True ako je tipka pustena, inace false.
     */
    public static boolean tipkaPustena(int kod) {
        return !tipke[kod] && prethodneTipke[kod];
    }

    /**
     * Ova metoda se ne koristi u kontekstu ove klase.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Ne koristi se
    }

    /**
     * Ova metoda se poziva kada je tipka pritisnuta.
     * Postavlja odgovarajuci indeks u nizu tipki na true.
     *
     * @param e Dogadjaj pritiska tipke.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        tipke[e.getKeyCode()] = true;
    }

    /**
     * Ova metoda se poziva kada je tipka otpustena.
     * Postavlja odgovarajuci indeks u nizu tipki na false.
     *
     * @param e Dogadjaj pustanja tipke.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        tipke[e.getKeyCode()] = false;
    }
}
