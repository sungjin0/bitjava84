package AddressBook;

public class AddressCreate {

	public AddressCreate() {

	}

	public void createInfo(AddressBookVO info, AddressBookBiz biz, AddressInput in) {

		System.out.println("�̸�,��ȭ��ȣ,�ּ�,���̼����� �Է�.");

		biz.addAddressInfo(this.inputInfo(info, in));
	}

	private AddressBookVO inputInfo(AddressBookVO info, AddressInput in) {

		info.setName(in.inputString());
		info.setPhoneNumber(in.inputString());
		info.setAddress(in.inputString());
		info.setAge(in.inputInt());

		return info;
	}
}
