import java.util.HashMap;

public class PredictionTableGenerator {
    private static HashMap<Prediction, Rule> predictionTable;
    //TODO Finish prediction table
    public static HashMap<Prediction, Rule> createPredictionTable(){
        if (predictionTable == null){
            fillPredictionTable();
        }
        
        return predictionTable;
    }
    
    private static void fillPredictionTable() {
    
    }
}
