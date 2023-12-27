import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class RSA {

    public static void main(String[] args) {

        HashMap<Character, Integer> alphabet = new HashMap<>();

        //Алфавит в виде словаря
        alphabet.put('А', 1);
        alphabet.put('Б', 2);
        alphabet.put('В', 3);
        alphabet.put('Г', 4);
        alphabet.put('Д', 5);
        alphabet.put('Е', 6);
        alphabet.put('Ё', 7);
        alphabet.put('Ж', 8);
        alphabet.put('З', 9);
        alphabet.put('И', 10);
        alphabet.put('Й', 11);
        alphabet.put('К', 12);
        alphabet.put('Л', 13);
        alphabet.put('М', 14);
        alphabet.put('Н', 15);
        alphabet.put('О', 16);
        alphabet.put('П', 17);
        alphabet.put('Р', 18);
        alphabet.put('С', 19);
        alphabet.put('Т', 20);
        alphabet.put('У', 21);
        alphabet.put('Ф', 22);
        alphabet.put('Х', 23);
        alphabet.put('Ц', 24);
        alphabet.put('Ч', 25);
        alphabet.put('Ш', 26);
        alphabet.put('Щ', 27);
        alphabet.put('Ъ', 28);
        alphabet.put('Ы', 29);
        alphabet.put('Ь', 30);
        alphabet.put('Э', 31);
        alphabet.put('Ю', 32);
        alphabet.put('Я', 33);

        //Ввод исходных данных
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите сообщение: ");
        String message = scanner.next();   //String message = "БАВ";
        System.out.print("Введите p: ");   //11
        int p = scanner.nextInt();
        System.out.print("Введите q: ");   //3
        int q = scanner.nextInt();
        System.out.print("Введите d: ");
        int d = scanner.nextInt();

        int n = p * q;

        //Функция Эйлера
        int f = ((p - 1) * (q - 1));

        //Находим открытый ключ
        float k = 1;
        float e = (f * k + 1) / d;
        for (; e % 1 != 0; k++) {
            e = (f * k + 1) / d;
        }

        //Ключи
        System.out.println("Открытый ключ: (" + (int)e + ", " + n + ")");
        System.out.println("Закрытый ключ: (" + d + ", " + n + ")");
        System.out.println();

        // Преобразование символов сообщения в числа с помощью алфавитного словаря
        List<Integer> messageNumbers = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            int number = alphabet.get(letter);
            messageNumbers.add(number);
        }

        // Вывод последовательности чисел для данного сообщения
        System.out.println("Числа сообщения "+ message+": " + messageNumbers);
        System.out.println();


        // Шифрование
        int[] encryptedMessage = encryptMessage(messageNumbers, (int)e, n); // зашифрованное сообщение

        //Вывод зашифрованного сообщения
        System.out.print("Криптограмма: ( ");
        for (int num : encryptedMessage) {
            System.out.print(num + " ");
        }
        System.out.print(")");
        System.out.println();


        // Расшифрование
        List<Character> decryptedMessage = decryptMessage(encryptedMessage, d, n, alphabet); // расшифрованное сообщение

        //Вывод расшифрованного сообщения
        System.out.print("Расшифрованное сообщение: ");
        for (char ch : decryptedMessage) {
            System.out.print(ch);
        }
        System.out.println();
    }



    // Метод для шифрования сообщения
    private static int[] encryptMessage(List<Integer> message, int e, int n) {
        int[] encryptedMessage = new int[message.size()];
        //Шифруем каждую букву согласно формуле С=M^e mod n
        for (int i = 0; i < message.size(); i++) {
            int С = power(message.get(i), e, n);
            encryptedMessage[i] = С;
        }
        return encryptedMessage;
    }

    // Метод для расшифрования криптограммы
    private static List<Character> decryptMessage(int[] encryptedMessage, int d, int n, HashMap<Character, Integer> alphabet) {
        List<Character> decryptedMessage = new ArrayList<>();
        //Расшифровываем каждую цифру криптограммы согласно формуле M=С^d mod n
        for (int i = 0; i < encryptedMessage.length; i++) {
            int decryptedNum = power(encryptedMessage[i], d, n);
            //Находим по номеру букву в алфавите
            char decryptedChar = getLetterByValue(alphabet, decryptedNum);
            decryptedMessage.add(decryptedChar);
        }
        return decryptedMessage;
    }

    // Метод для поиска буквы по значению в HashMap
    private static char getLetterByValue(HashMap<Character, Integer> map, int value) {
        for (char letter : map.keySet()) {
            if (map.get(letter) == value) {
                return letter;
            }
        }
        return '\0'; // Возвращаем нулевой символ в случае отсутствия соответствия
    }


    //Метод возведения числа в степень
    public static int power(int a, int b, int n) {
        if (b == 0) {
            return 1;
        } else {
            long result = 1;
            for (int i = 0; i < b; i++) {
                result *= a;
                result %= n;
            }
            return (int)result;
        }
    }

}
