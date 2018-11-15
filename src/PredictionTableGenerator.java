import Symbols.NonTerminal;
import Symbols.Symbol;
import Symbols.Terminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PredictionTableGenerator {
    private static HashMap<Prediction, Rule> parseTable;

    private PredictionTableGenerator() {
        //hides default constructor
    }

    //TODO Finish prediction table
    public static Map<Prediction, Rule> createPredictionTable(){
        if (parseTable == null){
            fillParseTable();
        }
        
        return parseTable;
    }
    
    private static void fillParseTable() {
        parseTable = new HashMap<>();
        ArrayList<Symbol> rhs;
        Prediction prediction;

        rhs = new ArrayList<>();
        rhs.add(NonTerminal.VARGROUP);
        prediction = new Prediction(NonTerminal.PGM, Terminal.KPROG);
        Rule rule = new Rule(1, prediction, rhs);
        parseTable.put(prediction, rule);

        rhs = new ArrayList<>();
        rhs.add(Terminal.EOF);
        prediction = new Prediction(NonTerminal.VARGROUP, Terminal.KVAR);
        rule = new Rule(2, prediction, rhs);
        parseTable.put(prediction, rule);
    }
}
