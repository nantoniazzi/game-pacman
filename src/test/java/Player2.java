import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int heigth = scanner.nextInt();
        int myId = scanner.nextInt();
        scanner.nextLine();

        List<String> level = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
//            for(int i = 0; i < heigth; i++) {
//                level.add(scanner.nextLine());
//            }
            System.out.println("RIGHT");
        }
    }
}
