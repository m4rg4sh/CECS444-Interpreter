import java.util.HashMap;

/**
 * This class represents the machine portion of the LL parse machine. It is responsible for setting up the prediction
 * table, prediction stack, and token stream.
 */
public class LLParseMachine {
    private static HashMap<Prediction, Rule> predictionTable = PredictionTableGenerator.createPredictionTable();
    
    public static void main(String[] args){
        TokenStreamReader tokenStream = new TokenStreamReader(System.in);
        //TODO Create prediction stack. Add the LL parse machine algorithm
    }
}
