import processors.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ProcessorPool processorPool = ProcessorPool.getInstance();
        CalculationProcessor processor1 = processorPool.acquireCalculationProcessor();
        Thread thread1 = new Thread(() -> {
            String[] equations = {"65.8+9", "65.8-9", "65.8/0", "65.8*9", "65.89"};
            for(String equation : equations){
                CalculationProcessor processor = processor1.supportOperation(equation);
                if(processor != null){
                    try{
                        float res = processor.solve(equation);
                        System.out.println(equation + "=" + res);
                    } catch (NumberFormatException e){
                        System.err.println(e.getMessage() + " \"" + equation + "\"");
                    }
                }
            }
        });
        CalculationProcessor processor2 = processorPool.acquireCalculationProcessor();
        Thread thread2 = new Thread(() -> {
            String[] equations = {"193.003.2+9", "65.8+90", "25.8-100","65.89&66", "65.8/9999", "695.8*9"};
            for(String equation : equations){
                CalculationProcessor processor = processor2.supportOperation(equation);
                if(processor != null){
                    try{
                        float res = processor.solve(equation);
                        System.out.println(equation + "=" + res);
                    } catch (NumberFormatException e){
                        System.err.println(e.getMessage() + " \"" + equation + "\"");
                    }
                }
            }
        });
        CalculationProcessor processor3 = processorPool.acquireCalculationProcessor();
        if(processor3 != null){
            Thread thread3 = new Thread(() -> {
                String[] equations = {"65.8+90", "25.8-100", "65.8/9999", "695.8*9", "65.89&66"};
                for(String equation : equations){
                    CalculationProcessor processor = processor3.supportOperation(equation);
                    float res = processor.solve(equation);
                    System.out.println(equation + "=" + res);
                }
            });
            thread3.start();
        }
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        processorPool.releaseCalculationProcessor(processor1);
        processorPool.releaseCalculationProcessor(processor2);
    }
}
