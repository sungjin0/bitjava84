package AddressBook;

import java.util.ArrayList;
import java.util.List;

public class AddressBookBiz {

   private List<AddressBookVO> addressBook; //VO[0],VO[1],VO[2],
   private int choice;
   private AddressBookVO info;
   private AddressCreate create;
   private AddressDelete delete;
   private AddressDisplay display;
   private AddressInput in;

   public AddressBookBiz() {

      addressBook = new ArrayList<AddressBookVO>();
      choice = 0;
      info = new AddressBookVO();
      create = new AddressCreate();
      delete = new AddressDelete();
      display = new AddressDisplay();
      in = new AddressInput();
   }

   public void Program() {

      while (true) {

         System.out.println("��� 1��, ���� 2��, ���� 3��  ���� 0��");

         this.setChoice();//keyboard.nextInt();
         choice = this.getChoice(); //getter();// int choice;

         if (choice == 1) {
            info = new AddressBookVO();
            create.createInfo(info, this, in);
         } else if (choice == 2) {
            delete.deleteInfo(this, in);
         } else if (choice == 3) {
            display.display(addressBook);//VO[0],VO[1]~~~~
         } else {
            System.out.println("���α׷��� �����մϴ�.");
            System.exit(0);
         }

      }
   }

   public int getChoice() {
      return this.choice;
   }

   public void setChoice() {
      this.choice = in.inputInt();
   }

   public List<AddressBookVO> getAddressBook() {
      return addressBook;
   }

   public void setAddressBook(List<AddressBookVO> addressInfoList) {
      this.addressBook = addressInfoList;
   }

   public void addAddressInfo(AddressBookVO info) {
      this.addressBook.add(info);
   }

   public void removeAddressInfo(AddressBookVO info) {
      this.addressBook.remove(info);
   }

   public AddressBookVO returnAddressInfo(String name) {

      for (AddressBookVO vo : getAddressBook()) {
         if (vo.getName().equalsIgnoreCase(name)) {
            return vo;
         }
      }
      return new AddressBookVO();
   }

   public boolean isAddressEmpty(String name) {

      for (AddressBookVO vo : getAddressBook()) {

         if (vo.getName().equalsIgnoreCase(name)) {
            return false;
         }
      }
      return true;
   }

}