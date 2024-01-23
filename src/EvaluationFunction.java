public class EvaluationFunction {
    public static double FitnessFunction(String value){
        return SinFunc.Sin(getDecimal(value));
    }
    public static double getDecimal(String binary){
        double total = 0;
        String[] bits = binary.split("");
        int index = bits.length-1;
        for(String x: bits) {
            if (x.equals("1"))
                total+=Math.pow(2,index);
            index--;
        }
        return total/(Math.pow(2, bits.length)-1)*3-1;
    }
}
