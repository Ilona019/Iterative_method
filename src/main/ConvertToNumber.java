package main;

/**
 *
 * @author Ilona
 */
public class ConvertToNumber {

    private enum TypesNumber {

        IntegerType, DoubleType;

        public <T> T convertToNumber(String value) {
            switch (this) {
                case IntegerType:
                    return (T) Integer.valueOf(value);
                case DoubleType:
                    return (T) Double.valueOf(value);
                default:
                    return null;
            }
        }

    }

    //Вычисление
    public static int convertStringToInteger(String numberStr) {
        return (int) ConvertToNumber.TypesNumber.IntegerType.convertToNumber(numberStr);
    }

    //Вычисление
    public static double convertStringToDouble(String numberStr) {
        return (double) ConvertToNumber.TypesNumber.DoubleType.convertToNumber(numberStr);
    }

    //Вычисление
    public static double convertObjectToDouble(Object numberObj) {
        return (double) ConvertToNumber.TypesNumber.DoubleType.convertToNumber(numberObj.toString());
    }
}
