
import Symbols.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParseTable{
    private Map<Prediction, Rule> parseTableMap;
    ParseTable(){
        parseTableMap = new HashMap<>();
    }
    
    public void put(Prediction prediction, ArrayList<Symbol> rhs, int ruleNumber) {
        Rule rule = new Rule(ruleNumber, prediction, rhs);
        parseTableMap.put(prediction, rule);
    }
    
    public boolean containsKey(Prediction key) {
        return parseTableMap.containsKey(key);
    }
    
    public Rule get(Prediction key) {
        return parseTableMap.get(key);
    }
    
    @Override
    public String toString() {
        return parseTableMap.toString();
    }
}
