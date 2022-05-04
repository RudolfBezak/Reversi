package sk.stuba.fei.uim.oop.vykreslovanie;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PocuvacKlaves implements KeyListener {

    private final Okno okno;

    public PocuvacKlaves(Okno okno) {
        this.okno = okno;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'r'){
            this.okno.resetAkcia();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.okno.getOkno().dispose();
            System.out.println("ukoncenie pomocou esc\n");
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
