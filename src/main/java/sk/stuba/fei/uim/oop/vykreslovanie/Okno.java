package sk.stuba.fei.uim.oop.vykreslovanie;

import sk.stuba.fei.uim.oop.hra.Doska;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Okno implements ChangeListener {
    private final JFrame okno;
    private JPanel doska;
    private final JSlider slider;
    private DlazdicaTlacitko[][] dlazdice;
    private final JLabel pocetBLabel;
    private final JLabel velkostDoskyLabel;
    private final JLabel vypisHraca;
    private int pocetB, pocetC;
    private final Doska doskaHra;
    private int nastavenieSlidera;
    private ImageIcon imgIcn, imgIcnVieZahrat, imgIcnVieZahratHighlight;
    private ImageIcon imgIcnCierna;
    private ImageIcon imgIcnBiela;
    private int sirka, vyska;

    public Okno(Doska doskaHra) {
        this.nacitajObrazky(720,720,6);
        this.nastavenieSlidera = 6;
        this.doskaHra = doskaHra;
        //vutvor okno nastav mu layout
        okno = new JFrame("Reversi");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setSize(720, 900);
        Color pozadie = new Color(100, 180, 130);
        okno.getContentPane().setBackground(pozadie);
        okno.setLayout(new BorderLayout());
        PocuvacKlaves pocuvacKlaves = new PocuvacKlaves(this);
        okno.addKeyListener(pocuvacKlaves);
        okno.setFocusable(true);


        //nastavenia
        JPanel nastavenia = new JPanel();
        nastavenia.setLayout(new BorderLayout());
        nastavenia.setBounds(0, 0, 720, 90);
        nastavenia.setBackground(pozadie);

        //slider
        slider = new JSlider(JSlider.HORIZONTAL, 6, 12, 6);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setPreferredSize(new Dimension(360, 64));
        slider.setBackground(pozadie);
        slider.addChangeListener(this);
        nastavenia.add(slider, BorderLayout.EAST);

        //reset
        ResetTlacitko resetTlacitko = new ResetTlacitko(this);

        nastavenia.add(resetTlacitko.getResetBtn(), BorderLayout.WEST);


        okno.add(nastavenia, BorderLayout.NORTH);


        //vytvor dlazdice
        vytvorDlazdice(doskaHra.getVelkostHracejPlochy());

        //daj naspodok vypis
        JPanel spodnyVypis = new JPanel();
        spodnyVypis.setBackground(pozadie);
        spodnyVypis.setBounds(0, 0, 720, 90);
        spodnyVypis.setLayout(new BorderLayout());

        //daj mu pocitadlo
        pocetB = 2;
        pocetC = 2;
        pocetBLabel = new JLabel("<html>pocet bielych je: " + pocetB +"<br/> pocet ciernych je: " + pocetC + "</html>",JLabel.CENTER);
        pocetBLabel.setPreferredSize(new Dimension(720/3,90));


        velkostDoskyLabel = new JLabel("velkost dosky je: " + doskaHra.getVelkostHracejPlochy()+"x"+doskaHra.getVelkostHracejPlochy(),JLabel.CENTER);
        velkostDoskyLabel.setPreferredSize(new Dimension(720/3, 90));

        vypisHraca = new JLabel();
        nastavZeHraHrac();
        vypisHraca.setPreferredSize(new Dimension(720/3, 90));

        spodnyVypis.add(velkostDoskyLabel, BorderLayout.WEST);
        spodnyVypis.add(pocetBLabel, BorderLayout.EAST);
        spodnyVypis.add(vypisHraca,BorderLayout.CENTER);


        okno.add(spodnyVypis, BorderLayout.SOUTH);

        okno.setIconImage(imgIcnCierna.getImage());





        //posledna vec
        okno.setVisible(true);
    }

    public void updatniDosku(Doska doska) {
        int velkost = doska.getVelkostHracejPlochy();
        int[][] dlazdice = doska.getHraciaPlocha();
        //nastav biele cierne praznde
        for (int y = 0; y < velkost; y++) {
            for (int x = 0; x < velkost; x++) {
                if (dlazdice[y][x] == 0) {
                    this.dlazdice[y][x].nastavPrazdnu();
                    //pozri ci sa da zahrat
                    if (doska.getKolkoOtocim()[y][x] > 0){
                        this.dlazdice[y][x].nastavViemZahrat();
                    }
                } else if (dlazdice[y][x] == 1) {
                    this.dlazdice[y][x].nastavCiernu();
                } else if (dlazdice[y][x] == 2) {
                    this.dlazdice[y][x].nastavBielu();


                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.resetAkcia();
        this.okno.setFocusable(true);
        this.okno.requestFocus();
    }

    public int getNastavenieSlidera() {
        return nastavenieSlidera;
    }

    public void odoberDosku(){
        okno.remove(doska);
    }

    public void vytvorDlazdice(int velkost){
        doska = new JPanel();
        doska.setLayout(new GridLayout(velkost, velkost));
        okno.add(doska, BorderLayout.CENTER);
        doska.addComponentListener(new ZvacsovanieOknaPocuvac(this));

        dlazdice = new DlazdicaTlacitko[velkost][velkost];
        for (int y = 0; y < velkost; y++) {
            for (int x = 0; x < velkost; x++) {
                dlazdice[y][x] = new DlazdicaTlacitko(x, y,doskaHra, this);
            }
        }
        for (int y = 0; y < velkost; y++) {
            for (int x = 0; x < velkost; x++) {
                doska.add(dlazdice[y][x].getDlazdica());
            }
        }
    }

    public void zmenTextury(int sirka,int vyska, int pocet){
        this.nacitajObrazky(sirka,vyska,this.nastavenieSlidera);
        for (int y = 0; y < pocet; y++) {
            for (int x = 0; x < pocet; x++) {
                dlazdice[y][x].nacitajTextury();
            }
        }
    }

    public void setPocetBC(int pocetB, int pocetC) {
        this.pocetB = pocetB;
        this.pocetC = pocetC;
        this.pocetBLabel.setText("<html>pocet bielych je: " + pocetB +"<br/> pocet ciernych je: " + pocetC + "</html>");
    }

    public void velkostDoskyvypis() {

        this.velkostDoskyLabel.setText("velkost dosky je: " + this.doskaHra.getVelkostHracejPlochy()+"x"+this.doskaHra.getVelkostHracejPlochy());
    }

    public void skoncilaHra(){
        if (this.pocetC > this.pocetB){
            vypisHraca.setText("vyhral hrac");
        }
        else if (this.pocetC < this.pocetB){
            vypisHraca.setText("vyhral pocitac");
        }
        else{
            vypisHraca.setText("remiza");
        }

    }

    public void nastavZeHraHrac(){
        vypisHraca.setText("<html>na tahu si ty<br/>si cierny</html>");
    }

    public void resetAkcia(){

        if (this.slider.getValue() % 2 == 0) {
            nastavenieSlidera = this.slider.getValue();
        }
        this.doskaHra.resetDoska(this.getNastavenieSlidera());
        this.odoberDosku();
        this.vytvorDlazdice(this.getNastavenieSlidera());
        this.zmenTextury(this.sirka,this.vyska,this.getNastavenieSlidera());
        this.updatniDosku(this.doskaHra);
        this.velkostDoskyvypis();
        this.setPocetBC(2,2);
        this.nastavZeHraHrac();

    }

    public JFrame getOkno() {
        return okno;
    }

    private void nacitajObrazky(int oknoSirka, int oknoVyska, int velkostDosky) {
        BufferedImage pic = null;
        //obrazky
        try {
            pic = ImageIO.read(Objects.requireNonNull(Okno.class.getResourceAsStream("/othelo_empty.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcn = new ImageIcon(pic);
        }


        imgIcn.setImage(nastavVelkost(oknoSirka / velkostDosky, oknoVyska / velkostDosky, imgIcn.getImage()));


        //cierna
        pic = null;
        try {
            pic = ImageIO.read(Objects.requireNonNull(Okno.class.getResourceAsStream("/othelo_black.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcnCierna = new ImageIcon(pic);
        }

        imgIcnCierna.setImage(nastavVelkost(oknoSirka / velkostDosky, oknoVyska / velkostDosky, imgIcnCierna.getImage()));

        //biela
        pic = null;
        try {
            pic = ImageIO.read(Objects.requireNonNull(Okno.class.getResourceAsStream("/othelo_white.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcnBiela = new ImageIcon(pic);
        }
        imgIcnBiela.setImage(nastavVelkost(oknoSirka / velkostDosky, oknoVyska / velkostDosky, imgIcnBiela.getImage()));

        //prazdna vie zahrat
        pic = null;
        try {
            pic = ImageIO.read(Objects.requireNonNull(Okno.class.getResourceAsStream("/othelo_empty_canplay.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcnVieZahrat = new ImageIcon(pic);
        }


        imgIcnVieZahrat.setImage(nastavVelkost(oknoSirka / velkostDosky, oknoVyska / velkostDosky, imgIcnVieZahrat.getImage()));

        //prazdna vie zahrat highlight
        pic = null;
        try {
            pic = ImageIO.read(Objects.requireNonNull(Okno.class.getResourceAsStream("/othelo_empty_canplay_highlight.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcnVieZahratHighlight = new ImageIcon(pic);

            imgIcnVieZahratHighlight.setImage(nastavVelkost(oknoSirka / velkostDosky, oknoVyska / velkostDosky, imgIcnVieZahratHighlight.getImage()));

        }
    }

    private Image nastavVelkost(int sirka, int vyska, Image obrazok){
        obrazok = obrazok.getScaledInstance(sirka,vyska,Image.SCALE_DEFAULT);
        return obrazok;

    }

    public ImageIcon getImgIcn() {
        return imgIcn;
    }

    public ImageIcon getImgIcnVieZahrat() {
        return imgIcnVieZahrat;
    }

    public ImageIcon getImgIcnVieZahratHighlight() {
        return imgIcnVieZahratHighlight;
    }

    public ImageIcon getImgIcnCierna() {
        return imgIcnCierna;
    }


    public ImageIcon getImgIcnBiela() {
        return imgIcnBiela;
    }


    public int getSirka() {
        return sirka;
    }

    public int getVyska() {
        return vyska;
    }

    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    public void setVyska(int vyska) {
        this.vyska = vyska;
    }

    public Doska getDoskaHra() {
        return doskaHra;
    }
}

