import Symbols.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private int id;
    private Prediction prediction;
    private ArrayList<Symbol> rhs;

    public Rule(int id, Prediction prediction, ArrayList<Symbol> rhs) {
        this.id = id;
        this.prediction = prediction;
        this.rhs = rhs;
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public int getId() {
        return id;
    }

    public List<Symbol> getRhs() {
        return rhs;
    }
}
