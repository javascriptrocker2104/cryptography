import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Hash {
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
        String message = scanner.next();   //String message = "БЕСПАЛОВА";
        System.out.print("Введите p: ");
        int p = scanner.nextInt();
        System.out.print("Введите q: ");
        int q = scanner.nextInt();
        System.out.print("Введите начальный хеш H0: ");
        int H = scanner.nextInt();

        int n = p * q;


        // Преобразование символов сообщения в числа с помощью алфавитного словаря
        List<Integer> messageNumbers = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            int number = alphabet.get(letter);
            messageNumbers.add(number);
        }

        // Вывод последовательности чисел для данного сообщения
        System.out.println("Числа сообщения: " + messageNumbers);


        // Вычисление хеш-образа для каждого числа в последовательности
        for (int i = 0; i < messageNumbers.toArray().length; i++) {
            int M = messageNumbers.get(i);
            H = (int) Math.pow((H + M), 2) % n;
            System.out.println("H" + (i+1) + "=" + H);
        }
        //Хеш-образ сообщения
        System.out.println("Хеш-образ сообщения "+message+" равен "+H);
    }
}