/*
 * @author Amar Osmanovic  
 */

package mainPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import mainPackage.igra.Igra;
import mainPackage.unos.Tastatura;

/**
 * Glavna klasa koja predstavlja ulaznu tacku programa za igru "2048".
 * Prosiruje Canvas i implementira Runnable za omogucavanje visenitnog izvrsavanja.
 */
@SuppressWarnings("serial")
public class Main extends Canvas implements Runnable {
    /** Sirina prozora igre. */
    public static final int sirina = 600;
    
    /** Visina prozora igre. */
    public static final int visina = 400;

    /** Glavni prozor aplikacije. */
    public static JFrame prozor;

    /** Nit koja upravlja izvrsavanjem igre. */
    public Thread nit;

    /** Objekat igre. */
    public Igra igra;

    /** Objekat za pracenje tastature. */
    public Tastatura tastatura;

    /** Flag koji oznacava da li se igra trenutno izvrsava. */
    public boolean radi = false;

    /** Slika za iscrtavanje igre. */
    public static BufferedImage slika = new BufferedImage(sirina - 200, visina, BufferedImage.TYPE_INT_RGB);

    /** Niz piksela koji cini sliku igre. */
    public static int[] pikseli = ((DataBufferInt) slika.getRaster().getDataBuffer()).getData();

    /**
     * Konstruktor klase Main.
     * Inicijalizuje velicinu prozora, objekte igre i tastature, te dodaje tastaturu kao KeyListener.
     */
    public Main() {
        setPreferredSize(new Dimension((int) (sirina), (int) (visina)));
        prozor = new JFrame();
        igra = new Igra();
        tastatura = new Tastatura();
        addKeyListener(tastatura);
    }

    /**
     * Metoda koja se izvrsava u posebnoj niti i upravlja glavnom petljom igre.
     */
    public void run() {
        double sekundiPoAzuriranju = 100000000 / 6;
        double azurirati = 0;
        long tajmer = System.currentTimeMillis();
        long zadnjeVrijeme = System.nanoTime();

        requestFocus(radi);

        while (radi) {
            long trenutnoVrijeme = System.nanoTime();
            azurirati += (trenutnoVrijeme - zadnjeVrijeme) / sekundiPoAzuriranju;
            if (azurirati >= 1) {
                azuriraj();
                azurirati--;
            }
            zadnjeVrijeme = trenutnoVrijeme;

            prikazi();
            if (System.currentTimeMillis() - tajmer > 1000) {
                tajmer += 1000;
            }
        }
    }

    /**
     * Metoda za pocetak izvrsavanja niti.
     */
    public void start() {
        radi = true;
        nit = new Thread(this, "petlja");
        nit.start();
    }

    /**
     * Metoda za azuriranje logike igre i unos korisnika.
     */
    public void azuriraj() {
        igra.azuriraj();
        tastatura.azuriraj();
    }

    /**
     * Metoda za iscrtavanje grafickih elemenata na ekranu.
     */
    public void prikazi() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        igra.prikazi();

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.drawImage(slika, 0, 0, (int) (sirina - 200), (int) (visina), null);

        g.setColor(Color.WHITE);
        g.fillRect((int) (sirina - 200), 0, 200, visina);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Trenutni bodovi: " + igra.izracunajBodove(0), (int) (sirina - 160), 30);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Najboljih 5", (int) (sirina - 160), 70);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        for (int i = 0; i < igra.najboljiBodovi.size(); i++) {
            g.drawString((i + 1) + ". " + igra.najboljiBodovi.get(i), (int) (sirina - 160), 100 + i * 20);
        }

        if (igra.jeKraj()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Igra zavrsena.", (int) (sirina - 150), 280);
            g.drawString("Osvojili ste: " + igra.izracunajBodove(0) + " bodova!", (int) (sirina - 190), 320);
            g.drawString("R za restart.", (int) (sirina - 150), 380);
        }

        igra.prikaziTekst(g);

        g.dispose();
        bs.show();
    }

    /**
     * Glavna metoda za pokretanje programa.
     *
     * @param args parametri main.
     */
    public static void main(String[] args) {
        Main m = new Main();
        Main.prozor.setResizable(false);
        Main.prozor.setTitle("2048 Igra");
        Main.prozor.add(m);
        Main.prozor.pack();
        Main.prozor.setLocationRelativeTo(null);
        Main.prozor.setVisible(true);
        Main.prozor.setAlwaysOnTop(true);
        Main.prozor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.start();
    }


}
