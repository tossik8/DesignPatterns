package processors;

public class SubtractionProcessor extends CalculationProcessor{
    public SubtractionProcessor(CalculationProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public CalculationProcessor supportOperation(String equation) {
        if(equation.indexOf('-') != -1){
            return this;
        }
        if(nextProcessor != null){
            return nextProcessor.supportOperation(equation);
        }
        System.err.println("Malformed equation \"" + equation + "\"");
        return null;
    }

    @Override
    public float solve(String equation) {
        float[] numbers = getNumbers(equation, "-");
        return numbers[0] - numbers[1];
    }

    @Override
    public String toString() {
        return "SubtractionProcessor{" +
                "nextProcessor=" + nextProcessor +
                '}';
    }
}
