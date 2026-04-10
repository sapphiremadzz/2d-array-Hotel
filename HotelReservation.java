package hotelreserve;
import java.util.*;
public class HotelReservation {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int hotel[][] = new int[7][5];

		int sel;
		do {
			System.out.print("\nPlease choose the following: "+"\n1. View Rooms\n"
					+ "2. Check In\n"
					+ "3. Check Out\n"
					+ "4. Exit\n" + "Enter your choice: "); sel = sc.nextInt();

					switch (sel) {

					case 1: viewRooms(hotel);
					break;
					case 2: checkIn(hotel);
					break;
					case 3: checkOut(hotel);
					break;
					case 4: System.out.println("Exiting...");
					break;
					default: System.out.println("Invalid choice, please try again!!");
					break;

					}


		}while (sel!=4); //the loop will stop if the user input 4.
	}

	
	//this method is for viewing availability of the rooms
	public static void viewRooms(int hotel[][]) {
		for (int i = 0; i<hotel.length;i++) {
			System.out.print("Floor" + Math.abs(i-7) + ": ");
			for (int j = 0; j<hotel[i].length;j++) {
				System.out.print(" ["+hotel[i][j] + "] ");
			} System.out.println();
		} 
	}

	
	//this method is for check in
	public static void checkIn(int hotel[][]) {
		Scanner sc = new Scanner (System.in);

		System.out.print("Enter Floor Number?(1-7): ");
		int floor = sc.nextInt();
		System.out.print("Enter Room Number?(1-5): ");
		int room = sc.nextInt() ;
		floor = Math.abs(floor -7); 
		room = room - 1;

		if ((floor >= 0 && floor < 7) && (room >= 0 && room < 5)) {
			if (hotel[floor][room] ==0) {
				hotel[floor][room]= 1; //mark indicates for occupied
				System.out.println("Check In Succesfully");
			} else {
				System.out.println("Room already occupied");
			} 
		}
	}

	
	//this method is for checkout
	public static void checkOut (int hotel[][]) {
		Scanner sc = new Scanner (System.in);
		System.out.print("What Floor Number?(1-7): ");
		int floor = sc.nextInt();
		System.out.print("What Room Number?(1-5): ");
		int room = sc.nextInt() ;

		floor = Math.abs(floor -7); 
		room = room - 1;

		if ((floor >= 0 && floor < 7) && (room >= 0 && room < 5)) {
			if (hotel[floor][room] ==1) {
				System.out.println("Check Out Succesful");
				hotel[floor][room]= 0; //mark available
			} else {
				System.out.println("Room already empty");
			} 
		}


	}

}
