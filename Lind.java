public class Lind {

    private String liik;
    private String värv;
    private char sugu;
    private String isKaitseall;


    public Lind(String liik, String värv, char sugu, String isKaitseall) {
        this.liik = liik;
        this.värv = värv;
        if (sugu != 'M' && sugu != 'N' ) {
            throw new ValeSuguErind("Arusaamatu linnu sugu");
        } else {
            this.sugu = sugu;
        }
        this.isKaitseall = isKaitseall;
    }

    public String getLiik() {
        return liik;
    }

    public String getVärv() {
        return värv;
    }

    public char getSugu() {
        return sugu;
    }

    public boolean isKaitseAll() {
        return isKaitseall.equals("1");
    }

    @Override
    public String toString() {
        return getLiik() + " " + getVärv() + " " + getSugu() + "\n";
    }
}
