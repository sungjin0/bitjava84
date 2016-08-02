package AddressBook;

import java.util.List;

public class AddressDisplay {
   // addressBook//VO[0],VO[1],VO[2]~~~~
   void display(List<AddressBookVO> addressBook) { // ��ü ��� ���÷���
      System.out.println("��ü �ο� ��� ���÷����Դϴ�.");

      for (AddressBookVO addressBookVO : addressBook) {

         System.out.println("�̸�: " + addressBookVO.getName() + "\n����: " + addressBookVO.getAge() + "\n�ּ�: "
               + addressBookVO.getAddress() + "\n�ڵ��� ��ȣ: " + addressBookVO.getPhoneNumber()+"\n");

      }
      System.out.println();

   }
}