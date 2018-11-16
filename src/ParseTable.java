
import Symbols.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class PredictionTable extends HashMap{
    private HashMap<Prediction, Rule> predictionTable;
    private int ruleNumber = 0;
    public PredictionTable (){
        predictionTable = new HashMap<>();
    }
    
    @Override
    public Object put(Object key, Object value) {
        Prediction prediction = (Prediction) key;
        ArrayList<Symbol> rhs = (ArrayList<Symbol>) value;
        Rule rule = new Rule(ruleNumber, prediction, rhs);
        return predictionTable.put(prediction, rule);
    }
    
    @Override
    public boolean containsKey(Object key) {
        return predictionTable.containsKey(key);
    }
    
    @Override
    public Object get(Object key) {
        return predictionTable.get(key);
    }
}
