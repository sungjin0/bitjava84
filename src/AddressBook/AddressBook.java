package AddressBook;

public class AddressBook {

	public void start() {

		AddressBookBiz func = new AddressBookBiz();

		func.Program();
	}

	public static void main(String[] args) {
		AddressBook test = new AddressBook();
		test.start();
	}
}
