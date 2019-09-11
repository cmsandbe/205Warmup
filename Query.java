import java.util.Scanner;

public class Query {
   public static void main(String[] args) {
      // Display rules to user
      System.out.println("RULES:");
      System.out.println("Enter the Pokemon name followed by the attribute you would like to look up.");
      System.out.println("Attributes include Type, SecondaryType, MaxCP, MaxHP, PictureOf");
      System.out.println("Example: Bulbasaur MaxCP");
      System.out.println("Enter END to terminate the program.");
      System.out.println();
      
      // Take in user input
      Scanner keyboard = new Scanner(System.in);
      String input="";
      while (!(input.equals("END"))) {
         System.out.print("> ");
         input = keyboard.nextLine();
      }
   }
}