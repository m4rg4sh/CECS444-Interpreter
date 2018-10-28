package Symbols;

public enum NonTerminal implements Symbol {
    Pgm(0),
    Vargroup(1);

    private int id;

    public int getId() {
        return id;
    }

    NonTerminal(int id) {
        this.id = id;
    }
}
