public class Partner {
    private String p1;
    private String p2;

    public String getPartner(String partner){
        if (partner.equals("P1")) {
            return this.p1;
        } else {
            return this.p2;
        }
    }
    public void setP1(String p1) {this.p1 = p1;}

    public void setP2(String p2) {this.p2 = p2;}

    public String toString() {
        return this.getPartner("P1") + "\n" + this.getPartner("P2");
    }
}
