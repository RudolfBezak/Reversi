package sk.stuba.fei.uim.oop.vykreslovanie;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ZvacsovanieOknaPocuvac implements ComponentListener {

    private final Okno okno;
    public ZvacsovanieOknaPocuvac(Okno okno) {
        this.okno = okno;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        okno.setSirka(e.getComponent().getWidth());
        okno.setVyska(e.getComponent().getHeight());
        //System.out.println(okno.getSirka() +" "+ okno.getVyska());
        okno.zmenTextury(okno.getSirka(), okno.getVyska(),okno.getDoskaHra().getVelkostHracejPlochy());
        okno.updatniDosku(okno.getDoskaHra());

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
