public class Spieler {
    private String farbName;
    private String name;
    private boolean isRoboter;
    private Farben farbe;

    public Spieler(String farbName, String name, boolean isRoboter, Farben farbe) {
        this.farbName = farbName;
        this.name = name;
        this.isRoboter = isRoboter;
        this.farbe = farbe;
    }
    public Spieler(String farbName, Farben farbe) {
        this.farbName = farbName;
        this.name = "Roboter";
        this.isRoboter = true;
        this.farbe = farbe;
    }




}
