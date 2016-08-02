package AddressBook;

public class AddressDelete {

	private String deleteName;

	public AddressDelete() {
		deleteName = "";
	}

	public void deleteInfo(AddressBookBiz biz, AddressInput in) {

		System.out.println("������ �̸��� �Է��ϼ���.");
		deleteName = in.inputString();

		if (!biz.isAddressEmpty(deleteName)) {
			biz.removeAddressInfo(biz.returnAddressInfo(deleteName));
			System.out.println(deleteName + "�� ������ �����Ǿ����ϴ�.");
		} else {
			System.out.println(deleteName + "�� ������ �������� �ʽ��ϴ�.");
		}
	}
}
