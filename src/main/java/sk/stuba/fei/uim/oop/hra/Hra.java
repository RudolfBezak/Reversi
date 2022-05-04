package sk.stuba.fei.uim.oop.hra;


import sk.stuba.fei.uim.oop.vykreslovanie.Okno;

public class Hra {
    public Hra() {

        //vytvor dosku
        Doska doska = new Doska(6);

        Okno okno = new Okno(doska);
        okno.updatniDosku(doska);








    }
}
