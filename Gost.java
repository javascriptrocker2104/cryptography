import java.util.ArrayList;
import java.util.Scanner;

public class Gost {
    public static void main(String[] args) {

        //Ввод исходных данных
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите сообщение: ");//фамилия 8 первых букв
        String message = scanner.nextLine();
        System.out.print("Введите ключ: ");//отчество 4 первые буквы
        String key = scanner.nextLine();
        System.out.println();


        //Преобразуем исходный текст в битовую последовательность
        ArrayList<Integer> bit_message = stringToBinary(message);
        //Преобразуем ключ в битовую последовательность
        ArrayList<Integer> bit_key = stringToBinary(key);
        System.out.print(bit_message);
        System.out.println();

        //Выделяем память под последовательность бит L и R
        ArrayList<Integer> L = new ArrayList<Integer>();
        ArrayList<Integer> R = new ArrayList<Integer>();

        //Первые 32 бита - это L
        for (int i = 0; i < 32; i++) {
            L.add(bit_message.get(i));
        }
        //Следующие 32 бита - это R
        for (int i = 32; i < 64; i++) {
            R.add(bit_message.get(i));
        }

        ArrayList<Integer> R_old = new ArrayList<>(R); // Сохраняем копию bitR

        //Преобразования последовательности
        R = sum(R, bit_key); //сумма по модулю 2^32
        R = transformation_S(R); //преобразование в блоке подстановки
        R = bites_shift(R, 11);//циклический сдвиг

        R = xor(R, L);//сложение по модулю 2 с L
        L = new ArrayList<>(R_old); // Восстанавливаем L из сохраненной копии R_old

        ArrayList<Integer> result = new ArrayList<>(L); // Копируем L в result
        result.addAll(R);//добавялем к L биты из R

        System.out.println("Ответ: ");
        System.out.println(result);
    }


    //Функция преобразования строки в последовательность битов
    public static ArrayList<Integer> stringToBinary(String str) {
        ArrayList<Integer> binarySequence = new ArrayList<>();

        // Проходим по каждому символу в строке
        for (int i = 0; i < str.length(); i++) {
            // Получаем символ из строки
            char c = str.charAt(i);

            // Переводим символ в двоичную строку и вычитаем 848, чтобы из 10-ой системы счисления перевести в 2-ую
            String binary = Integer.toBinaryString((int) c - 848);

            // Дополняем нулями до 8 символов
            while (binary.length() < 8) {
                binary = "0" + binary;
            }

            // Проходим по каждому биту в двоичной строке и добавляем в ArrayList
            for (int j = 0; j < binary.length(); j++) {
                int digit = Character.getNumericValue(binary.charAt(j));
                binarySequence.add(digit);
            }
        }
        return binarySequence;
    }


    //Метод для вычисления суммы по модулю 2^32
    public static ArrayList<Integer> sum(ArrayList<Integer> R, ArrayList<Integer> X) {
        ArrayList<Integer> result = new ArrayList<>();
        int transfer = 0;//перенос
        for (int i = X.size() - 1; i >= 0; i--) {
            //складываем с учетом предыдущего переноса
            int sum = R.get(i) + X.get(i) + transfer;
            //добавляем в начало
            result.add(0, sum % 2);
            transfer = sum / 2;
        }
        return result;
    }

    //Функция преобразования в блоке подстановки
    public static ArrayList<Integer> transformation_S(ArrayList<Integer> bits) {
        ArrayList<Integer> box = new ArrayList<Integer>();
        int Row;
        String temp1 = "", temp2 = "", strbit;

        //8 блоков
        for(int n=0; n<8; n++)
        {
            //извлекаем 4 бита из массива и добавляем их в box.
            for(int i=0; i<4; i++)
            {
                box.add(bits.get(0));
                bits.remove(0);
            }

            //формируем десятичное число из битов в блоке.
            Row = Integer.parseInt(box.get(0)+""+box.get(1)+box.get(2)+""+box.get(3),2);
            //находим соответствующее значение в таблице подстановки Sbox
            //и представляем его в виде строки двоичных битов.
            strbit=  Integer.toString(Sbox[Row][n], 2);
            //найденные биты добавляем к пустой строке
            temp2 = temp2 +strbit;

            //определяем сколько битов надо дописать
            for(int j=0; j<4-strbit.length(); j++)
                //добавляем оставшийся биты
                temp2 = 0 + temp2;
            temp1 = temp1 + temp2;//полученный блок добавляем к основной строке

            //очищаем временные переменные для следующей иттерации
            temp2="";
            box.clear();
        }
        //добавляем к основному числу
        for(int i = 0; i<32; i++) {
            bits.add(Integer.parseInt(temp1.charAt(i)+""));
        }
        return bits;
    }

    // Функция совершает циклический сдвиг влево на 11 бит
    public static ArrayList<Integer> bites_shift(ArrayList<Integer> bits, int shift) {
        for (int i = 0; i < shift; i++) {
            // Удаляем младший бит и добавляем его в конец массива, реализуя циклический сдвиг
            int temp = bits.remove(0);
            bits.add(temp);
        }
        return bits;
    }

    // Функция сложения по модулю 2
    public static ArrayList<Integer> xor(ArrayList<Integer> atr1, ArrayList<Integer> atr2) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < atr2.size(); i++)
            // Добавляем в результат XOR каждой пары битов из atr1 и atr2
            result.add(atr1.get(i).equals(atr2.get(i)) ? 0 : 1);
        return result;
    }


    //Блок подстановки
    private static int[][] Sbox = {
            {1, 13, 4, 6, 7, 5, 14, 4},
            {15, 11, 11, 12, 13, 8, 11, 10},
            {13, 4, 10, 7, 10, 1, 4, 9},
            {0, 1, 0, 1, 1, 13, 12, 2},
            {5, 3, 7, 5, 0, 10, 6, 13},
            {7, 15, 2, 15, 8, 3, 13, 8},
            {10, 5, 1, 13, 9, 4, 15, 0},
            {4, 9, 13, 8, 15, 2, 10, 14},
            {9, 0, 3, 4, 14, 14, 2, 6},
            {2, 10, 6, 10, 4, 15, 3, 11},
            {3, 14, 8, 9, 6, 12, 8, 1},
            {14, 7, 5, 14, 12, 7, 1, 12},
            {6, 6, 9, 0, 11, 6, 0, 7},
            {11, 8, 12, 3, 2, 0, 7, 15},
            {8, 2, 15, 11, 5, 9, 5, 5},
            {12, 12, 14, 2, 3, 11, 9, 3}
    };
}