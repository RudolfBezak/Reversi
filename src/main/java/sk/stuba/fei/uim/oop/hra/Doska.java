package sk.stuba.fei.uim.oop.hra;


import java.util.Arrays;

public class Doska {
    private int[][] hraciaPlocha, kolkoOtocim;
    private int velkostHracejPlochy;
    private boolean skoncilaHra;

    public Doska(int velkostHracejPlochy) {
       //nastav dosku
        resetDoska(velkostHracejPlochy);

    }

    private void nastavStred(){
        //nastav stred
        hraciaPlocha[(velkostHracejPlochy/2)-1][(velkostHracejPlochy/2)-1] = 1;
        hraciaPlocha[(velkostHracejPlochy/2)][(velkostHracejPlochy/2)-1] = 2;
        hraciaPlocha[(velkostHracejPlochy/2)][(velkostHracejPlochy/2)] = 1;
        hraciaPlocha[(velkostHracejPlochy/2)-1][(velkostHracejPlochy/2)] = 2;

    }

    private void nastavNuly(int[][] pole){
        for (int[]i : pole){
            Arrays.fill(i,0);
        }
    }

    private int najdiNepriatela(int hracNaRade){
        //najdi nepriatela
        int nepriatel = 1;
        if (hracNaRade == 1){
            nepriatel = 2;
        }
        return nepriatel;
    }

    private void pozriOkoloSeba(int x,int y,int nepriatel, int hracNaRade){
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,+1,0);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,-1,0);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,0,-1);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,0,+1);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,+1,+1);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,+1,-1);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,-1,+1);
        pozriOkoloSebaStrana(x,y,nepriatel,hracNaRade,-1,-1);



    }

    private void pozriOkoloSebaStrana(int x,int y,int nepriatel, int hracNaRade, int xKrok, int yKrok){

        int oKolkoSaPozeramX = xKrok;
        int oKolkoSaPozeramY = yKrok;
        int kolkoOtocimTmp = 1;
        int kolkoOtocim = 0;

        //pozri ci je prazdne policko kam chces polozit
       if(y - yKrok <= -1){
            return;
        }
        if(x - xKrok <= -1){
            return;
        }

        if(y - yKrok >= this.velkostHracejPlochy){
            return;
        }
        if(x - xKrok >= this.velkostHracejPlochy){
            return;
        }

        if (this.hraciaPlocha[y - yKrok][x - xKrok] == 0) {
            //vypocitaj kolko polozis
            while (true) {
                //som mimo dosky
                if (x + oKolkoSaPozeramX >= this.velkostHracejPlochy) {
                    break;
                }
                if (y + oKolkoSaPozeramY >= this.velkostHracejPlochy) {
                    break;
                }

                if (x + oKolkoSaPozeramX <= -1) {
                    break;
                }
                if (y + oKolkoSaPozeramY <= -1) {
                    break;
                }

                //prazne policko
                if (this.hraciaPlocha[y + oKolkoSaPozeramY][x + oKolkoSaPozeramX] == 0) {
                    break;
                }
                //tvoja figurka
                if (this.hraciaPlocha[y + oKolkoSaPozeramY][x + oKolkoSaPozeramX] == hracNaRade) {
                    kolkoOtocim = kolkoOtocimTmp;
                    oKolkoSaPozeramX += xKrok;
                    oKolkoSaPozeramY += yKrok;
                }
                //enemy figurka
                else if (this.hraciaPlocha[y + oKolkoSaPozeramY][x + oKolkoSaPozeramX] == nepriatel) {
                    kolkoOtocimTmp++;
                    oKolkoSaPozeramX += xKrok;
                    oKolkoSaPozeramY += yKrok;
                }
            }
            this.kolkoOtocim[y - yKrok][x - xKrok] += kolkoOtocim;
        }
    }

    private void setKolkoOtocim(int hracNaRade){
        nastavNuly(this.kolkoOtocim);
        int nepriatel = najdiNepriatela(hracNaRade);

        for (int riadok = 0; riadok < this.velkostHracejPlochy; riadok++){
            for (int stvorcek = 0; stvorcek < this.velkostHracejPlochy; stvorcek++){
                if (this.hraciaPlocha[riadok][stvorcek] == nepriatel){
                    //nepriatelsky stvorec pozera okolo seba
                    pozriOkoloSeba(stvorcek,riadok,nepriatel, hracNaRade);

                }
            }
        }



    }

    private void otocOkoloSeba(int x, int y, int hracNaRade){
        otocOkoloSebaStrana(x,y,hracNaRade,+1,0);
        otocOkoloSebaStrana(x,y,hracNaRade,-1,0);
        otocOkoloSebaStrana(x,y,hracNaRade,0,-1);
        otocOkoloSebaStrana(x,y,hracNaRade,0,+1);
        otocOkoloSebaStrana(x,y,hracNaRade,+1,+1);
        otocOkoloSebaStrana(x,y,hracNaRade,+1,-1);
        otocOkoloSebaStrana(x,y,hracNaRade,-1,+1);
        otocOkoloSebaStrana(x,y,hracNaRade,-1,-1);

    }

    private void otocOkoloSebaStrana(int x,int y, int hracNaRade, int xKrok, int yKrok){
        boolean bolaUzTvoja = false;
        int kolkoSomPosunutyX = xKrok, kolkoSomPosunutyY = yKrok;
        int kolkoKrokov = 1;
        while(true){
            //skonci mimo dosky
            if (y+kolkoSomPosunutyY >= velkostHracejPlochy){
                kolkoSomPosunutyY -= yKrok;
                kolkoSomPosunutyX -= xKrok;
                kolkoKrokov--;
                break;
            }
            if (x+kolkoSomPosunutyX >= velkostHracejPlochy){
                kolkoSomPosunutyY -= yKrok;
                kolkoSomPosunutyX -= xKrok;
                kolkoKrokov--;
                break;
            }

            if (y+kolkoSomPosunutyY <= -1){
                kolkoSomPosunutyY -= yKrok;
                kolkoSomPosunutyX -= xKrok;
                kolkoKrokov--;
                break;
            }
            if (x+kolkoSomPosunutyX <= -1){
                kolkoSomPosunutyY -= yKrok;
                kolkoSomPosunutyX -= xKrok;
                kolkoKrokov--;
                break;
            }

            //alebo na prazdnom policku
            if (this.hraciaPlocha[y+kolkoSomPosunutyY][x+kolkoSomPosunutyX] == 0){
                kolkoSomPosunutyY -= yKrok;
                kolkoSomPosunutyX -= xKrok;
                kolkoKrokov--;
                break;
            }

            //inac pokracuje dalej
            else if (this.hraciaPlocha[y+kolkoSomPosunutyY][x+kolkoSomPosunutyX] != 0){
                kolkoSomPosunutyY+=yKrok; kolkoSomPosunutyX+= xKrok;
                kolkoKrokov++;
            }
        }
        //otacaj zetony
        for (int i = 0; i < kolkoKrokov; i++){
            if(this.hraciaPlocha[y+kolkoSomPosunutyY][x+kolkoSomPosunutyX] == hracNaRade){
                bolaUzTvoja = true;
            }
            if (bolaUzTvoja){
                this.hraciaPlocha[y+kolkoSomPosunutyY][x+kolkoSomPosunutyX] = hracNaRade;
            }
            kolkoSomPosunutyY -= yKrok;
            kolkoSomPosunutyX -= xKrok;

        }



    }

    public int[] polozFigurku(int hracNaRade,int x, int y){

        //hrac zahra
        this.setKolkoOtocim(1);
        if (this.kolkoOtocim[y][x] == 0) {
            return opocitajFigurky();
        }
        otocOkoloSeba(x, y, hracNaRade);

        this.hraciaPlocha[y][x] = hracNaRade;

        setKolkoOtocim(najdiNepriatela(hracNaRade));

        //zahra bot
        botZahra();
        boolean zahraBot;
        //zisi ci moze hrac hrat
        while (true){

            if (!mozeHracHrat()){
                //ak hrac nemoze hrat ide bot znovu
                this.setKolkoOtocim(2);
                zahraBot = botZahra();
                if (!zahraBot){
                    // hrac nemoze ist a bot nemoze ist
                    this.skoncilaHra = true;
                    break;
                }
            }
            else{//hrac moze zahrat
                break;
            }
        }




        return opocitajFigurky();

    }

    private boolean botZahra(){
        int max = 0;
        int xMax = 0, yMax = 0;
        for (int ySur = 0; ySur < this.velkostHracejPlochy; ySur++){
            for (int xSur = 0; xSur < this.velkostHracejPlochy; xSur++){
                if (this.kolkoOtocim[ySur][xSur] > max){
                    max = this.kolkoOtocim[ySur][xSur];
                    xMax = xSur;
                    yMax = ySur;
                }
            }
        }

        boolean pocitacMozeZahrat = max != 0;

        if (pocitacMozeZahrat) {
            int hracNaRade = 1;
            otocOkoloSeba(xMax, yMax, najdiNepriatela(hracNaRade));
            this.hraciaPlocha[yMax][xMax] = najdiNepriatela(hracNaRade);
            setKolkoOtocim(hracNaRade);
            return true;
        }
        return false;

    }

    private boolean mozeHracHrat(){
        //zisti ci hrac moze hrat
        this.setKolkoOtocim(1);
        for (int y = 0; y < this.velkostHracejPlochy; y++){
            for (int x = 0; x < this.velkostHracejPlochy; x++){
                if(this.kolkoOtocim[y][x] != 0){
                    return true;
                }
            }
        }
        return false;
    }


    public int[][] getHraciaPlocha() {
        return hraciaPlocha;
    }

    public int getVelkostHracejPlochy() {
        return velkostHracejPlochy;
    }

    public int[][] getKolkoOtocim() {
        return kolkoOtocim;
    }

    public void resetDoska(int velkost){
        //inicializuj hodnoty
        this.velkostHracejPlochy = velkost;
        this.hraciaPlocha = new int[this.velkostHracejPlochy][this.velkostHracejPlochy]; nastavNuly(this.hraciaPlocha);
        this.kolkoOtocim = new int[this.velkostHracejPlochy][this.velkostHracejPlochy]; nastavNuly(this.kolkoOtocim);

        //nastav stred
        nastavStred();
        setKolkoOtocim(1);
        this.skoncilaHra = false;
    }

    public int[] opocitajFigurky(){
        int[] vysledok = new int[2];
        Arrays.fill(vysledok,0);
        for (int y = 0; y < this.velkostHracejPlochy; y++){
            for (int x = 0; x < this.velkostHracejPlochy; x++){
                if (this.hraciaPlocha[y][x] == 1){
                    vysledok[0]++;
                }
                else if (this.hraciaPlocha[y][x] == 2){
                    vysledok[1]++;
                }
            }
        }



        return vysledok;
    }

    public boolean zistiCiSkoncilaHra() {
        return skoncilaHra;
    }
}

