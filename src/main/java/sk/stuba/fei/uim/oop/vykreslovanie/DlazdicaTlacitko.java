package sk.stuba.fei.uim.oop.vykreslovanie;

import sk.stuba.fei.uim.oop.hra.Doska;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DlazdicaTlacitko implements MouseListener {
    private final JLabel dlazdica;
    private final int xSur;
    private final int ySur;
    private ImageIcon imgIcn;
    private ImageIcon imgIcnVieZahrat;
    private ImageIcon imgIcnVieZahratHighlight;
    private ImageIcon imgIcnCierna;
    private ImageIcon imgIcnBiela;
    private final Doska doska;
    private final Okno okno;
    private int farba;
    private boolean vieZahrat;

    public DlazdicaTlacitko(int xSur, int ySur,Doska doska, Okno okno) {
        this.vieZahrat = false;
        this.okno = okno;
        this.doska = doska;
        this.xSur = xSur;
        this.ySur = ySur;
        this.dlazdica = new JLabel();
        this.dlazdica.addMouseListener(this);

        nacitajTextury();

        this.farba = 0;




    }

    public void nacitajTextury(){
        //obrazky
        imgIcnBiela = this.okno.getImgIcnBiela();
        imgIcnCierna = this.okno.getImgIcnCierna();
        imgIcn = this.okno.getImgIcn();
        imgIcnVieZahrat = this.okno.getImgIcnVieZahrat();
        imgIcnVieZahratHighlight = this.okno.getImgIcnVieZahratHighlight();


    }
    public JLabel getDlazdica() {
        return dlazdica;
    }
    public void nastavPrazdnu(){
        this.vieZahrat = false;
        this.dlazdica.setIcon(imgIcn);
        this.farba=0;

    }
    public void nastavCiernu(){
        this.dlazdica.setIcon(imgIcnCierna);
        this.farba=1;

    }
    public void nastavBielu(){
        this.dlazdica.setIcon(imgIcnBiela);
        this.farba=2;
    }

    public void nastavViemZahrat(){
        this.dlazdica.setIcon(imgIcnVieZahrat);
        this.vieZahrat = true;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        oknoRequestFocus();


    }

    @Override
    public void mousePressed(MouseEvent e) {

        int[] pocty = doska.polozFigurku(1,xSur,ySur);
        okno.setPocetBC(pocty[1],pocty[0]);
        okno.updatniDosku(doska);
        //zisti ci neskoncila hra
        if(this.doska.zistiCiSkoncilaHra()){
            this.okno.skoncilaHra();
        }
        oknoRequestFocus();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        oknoRequestFocus();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (farba == 0) {
            if (vieZahrat) {
                this.dlazdica.setIcon(imgIcnVieZahratHighlight);
            }
            oknoRequestFocus();
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (farba == 0) {
            if (this.vieZahrat){
                this.dlazdica.setIcon(imgIcnVieZahrat);
            }

        }
        oknoRequestFocus();

    }

    private void oknoRequestFocus(){
        this.okno.getOkno().setFocusable(true);
        this.okno.getOkno().requestFocus();
    }
}

