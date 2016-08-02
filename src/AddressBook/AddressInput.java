package AddressBook;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AddressInput {

	private Scanner scanner;

	public AddressInput() {
		scanner = new Scanner(System.in);
	}

	public int inputInt() {

		scanner = new Scanner(System.in);

		while (true) {

			try {

				return scanner.nextInt();
			} catch (InputMismatchException ime) {

				scanner = new Scanner(System.in);
				System.out.println("������ �ٽ� �Է� �ϼ���.");
			}
		}
	}

	public String inputString() {

		scanner = new Scanner(System.in);

		return scanner.nextLine();
	}
}
