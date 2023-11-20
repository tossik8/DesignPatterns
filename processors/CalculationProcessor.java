package processors;

public abstract class CalculationProcessor {
    public CalculationProcessor nextProcessor;

    public CalculationProcessor(CalculationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract CalculationProcessor supportOperation(String equation);

    public abstract float solve(String equation) throws NumberFormatException;

    public float[] getNumbers(String equation, String operator){
        float[] numbers = new float[2];
        String[] numberStrings = equation.split(String.valueOf(operator));
        for(int i = 0; i < numberStrings.length; ++i){
            numbers[i] = Float.parseFloat(numberStrings[i]);
        }
        return numbers;
    }
}
