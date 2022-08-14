package Controllers;

import Models.Vehicles.*;
import Util.DateTime;

import java.awt.*;
import java.util.Scanner;


/**
 * Some codes of this class were taken from the base code
 */

public class ObjectCreator {

	public Vehicle createVehicle(VehicleType type) {

		Vehicle obj=null;
		Scanner sc=new Scanner(System.in); // Question 
		System.out.println("Enter Plate ID :");
		String plateID = sc.next();
		System.out.println("Enter the Brand :");
		String brand= sc.next();
		System.out.println("Enter the model :");
		String model=sc.next();

		System.out.println("Enter the date and time (DD/MM/YYYY-HH:mm:ss)");
		String dateTime = sc.next();
		//String dateTime = "22/12/2021-12:12:12";
		//adding the data in to a string array
		String[] arr= dateTime.split("-");
		String[] dateString= arr[0].split("/");
		String[] timeString=arr[1].split(":");

		DateTime entryTime=new DateTime(Integer.parseInt(dateString[0]),
				Integer.parseInt(dateString[1]),
				Integer.parseInt(dateString[2]),
				Integer.parseInt(timeString[0]),
				Integer.parseInt(timeString[1]),
				Integer.parseInt(timeString[2]));

		switch(type) {
			case Car:
//			System.out.println("Enter number of Doors : ");
//			int numDoors=sc.nextInt();
//
//			System.out.println("Enter the color of the Car (R/G/B) : ");
//			String colorString=sc.next();
//			String[] colorArr =colorString.split("/");
//			Color carColor=new Color(Integer.parseInt(colorArr[0]),
//					Integer.parseInt(colorArr[1]),Integer.parseInt(colorArr[2]));

				obj=new Car(plateID,brand,model,entryTime, 1);
				break;
			//22/12/2021-12:12:12
			case Van:
				System.out.println("Enter the Cargo Capacity : ");
				double cargoCapacity=sc.nextDouble();

				System.out.println("Enter the number of seats : ");
				int seats = sc.nextInt();

				obj=new Van(plateID,brand,model,entryTime,cargoCapacity,2);
				break;

			case MotorBike:
				System.out.println("Enter the Engine Size : ");
				String engineSize=sc.next();

				obj=new MotorBike(plateID,brand,model,entryTime,engineSize,2);
				break;

			case Bus:
				obj=new Bus(plateID,brand,model,entryTime,2);
				break;

			case Lorry:
				obj=new MiniBus(plateID,brand,model,entryTime,2);
				break;

			case MiniBus:
				obj=new MiniLorry(plateID,brand,model,entryTime,2);
				break;

			case MiniLorry:
				obj=new Lorry(plateID,brand,model,entryTime,2);
				break;

			default :
				System.out.println("Invalid Choice");

		}
		return obj;
	}

}


