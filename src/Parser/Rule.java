package Parser;

import Symbols.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a parse rule
 * It has an ID and a list of symbols which are the RHS of the rule
 *
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 */
public class Rule {
    private int id;
    private ArrayList<Symbol> rhs;

    public Rule(int id, List<Symbol> rhs) {
        this.id = id;
        this.rhs = new ArrayList<>(rhs);
    }

    /**
     * @return The rule's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return the RHS of the rule as a List of Symbols
     */
    public List<Symbol> getRhs() {
        return rhs;
    }
}
