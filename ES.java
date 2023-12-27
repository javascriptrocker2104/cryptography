import java.util.Scanner;

class ES {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Ввод исходных данных
        System.out.print("Введите p: ");
        int p = scanner.nextInt();
        System.out.print("Введите q: ");
        int q = scanner.nextInt();
        System.out.print("Введите d: ");
        int d = scanner.nextInt();
        System.out.print("Введите h: ");
        int h = scanner.nextInt();
        int n = p * q;

        //Функция Эйлера
        int f = ((p - 1) * (q - 1));

        //Находим открытый ключ
        float k = 1;
        float e = (f * k + 1)/d;
        for(;e % 1 != 0;k++) {
            e = (f * k + 1) / d;
        }

        //Вычисляем ЭЦП сообщения
        int s = power(h, d, n);
        System.out.println("\ns = " + h + " ^ " + d + " mod " + n + " = " + s);

        //Проверяем подлинность
        int H = power(s, (int)e, n);
        if (H==h)
            System.out.println("Подпись подлинная");
        else System.out.println("Подпись не подлинная");
        System.out.println("H = " + s + " ^ " + (int)e + " mod " + n + " = " + H);
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