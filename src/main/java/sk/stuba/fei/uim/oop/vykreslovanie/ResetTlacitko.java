package sk.stuba.fei.uim.oop.vykreslovanie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ResetTlacitko implements ActionListener {
    private final JButton resetBtn;
    private ImageIcon imgIcn;
    private final Okno okno;

    public ResetTlacitko(Okno okno) {
        resetBtn = new JButton();
        resetBtn.addActionListener(this);
        this.okno = okno;
        //nacitaj obrazok
        BufferedImage pic = null;
        try {
            pic = ImageIO.read(Objects.requireNonNull(ResetTlacitko.class.getResourceAsStream("/reset.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pic != null) {
            imgIcn = new ImageIcon(pic);
        }
    resetBtn.setPreferredSize(new Dimension(280,64));
    Image img = imgIcn.getImage();
    img = img.getScaledInstance(280,64,Image.SCALE_DEFAULT);
    imgIcn.setImage(img);

    resetBtn.setIcon(imgIcn);

    }

    public JButton getResetBtn() {
        return resetBtn;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        this.okno.resetAkcia();
        this.okno.getOkno().setFocusable(true);
        this.okno.getOkno().requestFocus();





    }
}
