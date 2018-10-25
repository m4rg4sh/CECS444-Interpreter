import Symbols.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

public class PredictionTableGenerator {
    private static HashMap<Prediction, ArrayList<Symbol>> predictionTable;
    //TODO Finish prediction table
    public static HashMap<Prediction, ArrayList<Symbol>> createPredictionTable(){
        if (predictionTable == null){
            fillPredictionTable();
        }
        
        return predictionTable;
    }
    
    private static void fillPredictionTable() {
    
    }
}
