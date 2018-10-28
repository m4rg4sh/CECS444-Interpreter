import Symbols.NonTerminal;
import Symbols.Symbol;
import Symbols.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PredictionTableGenerator {
    private static HashMap<Prediction, ArrayList<Symbol>> predictionTable;

    private PredictionTableGenerator() {
        //hides default constructor
    }

    //TODO Finish prediction table
    public static Map<Prediction, ArrayList<Symbol>> createPredictionTable(){
        if (predictionTable == null){
            fillPredictionTable();
        }
        
        return predictionTable;
    }
    
    private static void fillPredictionTable() {
        predictionTable = new HashMap<>();
        ArrayList<Symbol> rhs;
        rhs = new ArrayList<>();
        rhs.add(NonTerminal.Vargroup);
        predictionTable.put(new Prediction(NonTerminal.Pgm, Terminal.KPROG), rhs);

        rhs = new ArrayList<>();
        rhs.add(Terminal.EOF);
        predictionTable.put(new Prediction(NonTerminal.Vargroup, Terminal.KVAR), rhs);
    }
}
