package fury.dndev.com.mltiplasinteligncias;
public class mairCalc {


    public mairCalc(int nota1, int nota2, int nota3, int nota4, int nota5, int nota6, int nota7) {
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
        this.nota5 = nota5;
        this.nota6 = nota6;
        this.nota7 = nota7;
    }
    public void calcularN(){
        this.maiorNota = 0;
        this.maiorNota2 = 0;
        int notaver = 0;
        int notaver2 = 0;

        if (this.nota1 > this.maiorNota){
            notaver = 1;
            this.maiorNota = this.nota1;

        }if (this.nota2 > this.maiorNota){
            notaver = 2;
            this.maiorNota = this.nota2;

        }if (this.nota3 > this.maiorNota){
            notaver = 3;
            this.maiorNota = this.nota3;

        }if (this.nota4 > this.maiorNota){
            notaver = 4;
            this.maiorNota = this.nota4;


        }if (this.nota5 > this.maiorNota){
            notaver = 5;
            this.maiorNota = this.nota5;

        }if (this.nota6 > this.maiorNota){
            notaver = 6;
            this.maiorNota = this.nota6;

        }if (this.nota7 > this.maiorNota){
            notaver = 7;
            this.maiorNota = this.nota7;
        }

        ///calcular segunda nota maior
        if (notaver == 1 ) {
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;
            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }

        if (notaver == 2 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;
            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }

        if (notaver == 3 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;

            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }if (notaver == 4 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }if (notaver == 5 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }if (notaver == 6 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;
            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota7 >this.maiorNota2){
                notaver2 = 7;
                this.maiorNota2 = this.nota7;
            }

        }if (notaver == 7 ) {
            if (this.nota1 >this.maiorNota2){
                notaver2 = 1;
                this.maiorNota2 = this.nota1;
            }
            if (this.nota2 >this.maiorNota2){
                notaver2 = 2;
                this.maiorNota2 = this.nota2;
            }
            if (this.nota3 >this.maiorNota2){
                notaver2 = 3;
                this.maiorNota2 = this.nota3;
            }
            if (this.nota4 >this.maiorNota2){
                notaver2 = 4;
                this.maiorNota2 = this.nota4;
            }
            if (this.nota5 >this.maiorNota2){
                notaver2 = 5;
                this.maiorNota2 = this.nota5;
            }
            if (this.nota6 >this.maiorNota2){
                notaver2 = 6;
                this.maiorNota2 = this.nota6;
            }

        }
        this.nota1MN = notaver;
        this.nota2MN = notaver2;

    }



    private int nota1;
    private int nota2;
    private int nota3;
    private int nota4;
    private int nota5;
    private int nota6;
    private int nota7;

    public int getNota1MN() {
        return nota1MN;
    }

    public int getNota2MN() {
        return nota2MN;
    }

    private int nota1MN;
    private int nota2MN;

    public int getMaiorNota() {
        return maiorNota;
    }
    public int getMaiorNota2() {
        return maiorNota2;
    }

    private int maiorNota2;

    private int maiorNota;

}
