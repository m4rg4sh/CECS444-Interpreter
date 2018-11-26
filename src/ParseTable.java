
import Symbols.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the LL parse table
 *
 * @author Kevin Bui <Kevinthuybui@gmail.com>
 * @author Stefan Brand <stefan.brandepprecht@student.csulb.edu>
 * @author Gabe Flores <rgabeflores@gmail.com>
 * @author Richard Salmeron <richard.salmeron@student.csulb.edu>
 */
public class ParseTable{
    private Map<Prediction, Rule> parseTableMap;

    ParseTable(){
        parseTableMap = new HashMap<>();
    }

    /**
     * Adds a new prediction and rule to the parse table
     * @param prediction The prediction used on the LHS of the rule
     * @param rhs The symbols in the RHS of the rule
     * @param ruleNumber The number of the rule
     */
    public void put(Prediction prediction, ArrayList<Symbol> rhs, int ruleNumber) {
        Rule rule = new Rule(ruleNumber, rhs);
        parseTableMap.put(prediction, rule);
    }

    /**
     * Checks if the table contains a given prediction
     * @param key the prediction to search for
     * @return
     */
    public boolean containsKey(Prediction key) {
        return parseTableMap.containsKey(key);
    }

    /**
     * Returns the rule which is associated with a given prediction
     * @param key the prediction which is used to search for the rule
     * @return the rule corresponding to the given key
     */
    public Rule get(Prediction key) {
        return parseTableMap.get(key);
    }

    /**
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return parseTableMap.toString();
    }
}
