import java.util.HashMap;

class Main {
    public static String calc(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 3)
                throw new IllegalArgumentException("Неверный формат ввода");

            String operand1 = parts[0];
            String operation = parts[1];
            String operand2 = parts[2];

            boolean isRoman = isRoman(operand1) && isRoman(operand2);
            if (!isRoman && (!operand1.matches("\\d+") || !operand2.matches("\\d+")))
                throw new IllegalArgumentException("Числа должны быть целыми от 1 до 10 включительно");

            int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
            int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10)
                throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");

            int result;
            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0)
                        throw new ArithmeticException("Деление на ноль");
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }

            return isRoman ? arabicToRoman(result) : String.valueOf(result);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVX]+");
    }

    private static int romanToArabic(String input) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int value = map.get(input.charAt(i));
            if (value < prevValue)
                result -= value;
            else
                result += value;
            prevValue = value;
        }

        return result;
    }

    private static String arabicToRoman(int num) {
        if (num < 1)
            throw new IllegalArgumentException("Результат не может быть меньше единицы");

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
        int[] arabicValues = {1, 4, 5, 9, 10, 40, 50, 90, 100};

        StringBuilder roman = new StringBuilder();
        int i = arabicValues.length - 1;

        while (num > 0) {
            if (num >= arabicValues[i]) {
                roman.append(romanSymbols[i]);
                num -= arabicValues[i];
            } else {
                i--;
            }
        }

        return roman.toString();
    }

    public static void main(String[] args) {
        // Пример использования
        System.out.println(calc("3 + 3")); // Вывод: 5
        System.out.println(calc("IV + VI")); // Вывод: X
        System.out.println(calc("10 / 2")); // Вывод: 5
        System.out.println(calc("X / IV")); // Вывод: II
        System.out.println(calc("10 - 2")); // Вывод: 8
        System.out.println(calc("X - IV")); // Вывод: VI
        System.out.println(calc("3 * 2")); // Вывод: 6
        System.out.println(calc("III * II")); // Вывод: VI
        System.out.println(calc("3 + II")); // Вывод: Исключение: Неподдерживаемый ввод
        System.out.println(calc("XI / II")); // Вывод: Исключение: Результат не может быть меньше единицы
    }
}
