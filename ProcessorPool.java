import processors.*;

import java.util.ArrayList;
import java.util.List;

public class ProcessorPool {
    private int size;
    private static ProcessorPool pool;
    private final List<CalculationProcessor> availableProcessors;
    private final List<CalculationProcessor> inUseProcessors;

    public ProcessorPool() {
        size = 2;
        availableProcessors = new ArrayList<>(size);
        inUseProcessors = new ArrayList<>(size);
    }

    public static synchronized ProcessorPool getInstance(){
        if(pool == null){
            pool = new ProcessorPool();
        }
        return pool;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public synchronized CalculationProcessor acquireCalculationProcessor(){
        if(!availableProcessors.isEmpty()){
            CalculationProcessor processor = availableProcessors.get(0);
            availableProcessors.remove(processor);
            inUseProcessors.add(processor);
            return processor;
        }
        if(inUseProcessors.size() < size){
            CalculationProcessor processor = createProcessor();
            inUseProcessors.add(processor);
            return processor;
        }
        System.err.println("No available objects in the pool");
        return null;
    }

    public synchronized void releaseCalculationProcessor(CalculationProcessor processor){
        availableProcessors.add(processor);
        inUseProcessors.remove(processor);
    }
    private CalculationProcessor createProcessor(){
        CalculationProcessor additionProcessor = new AdditionProcessor(null);
        CalculationProcessor divisionProcessor = new DivisionProcessor(additionProcessor);
        CalculationProcessor multiplicationProcessor = new MultiplicationProcessor(divisionProcessor);
        return new SubtractionProcessor(multiplicationProcessor);
    }
}
