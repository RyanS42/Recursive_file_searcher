import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RecursiveFileSearch 
{
	public static void main(String[] args)
	{
		File mainDir = null;
		ArrayList<String> results = new ArrayList<String>();
		String[] options = new String[5];
		System.out.println("Welcome to the recursive file searcher!");
		int choice = displayMenu();
		while( choice != 2 )
		{
			options = getOptions();
			mainDir = new File(options[0]);
			results = search(mainDir, options[1], options[2], options[3], options[4], results);
			System.out.println("Results:");
			if ( results.size() == 0 )
			{
				System.out.println("\nNothing was found within directory " + mainDir + " that matches the search critera entered.\n");
			}
			else
			{
				for ( int i = 0; i < results.size(); i++ )
				{
					String resultFile = results.get(i);
					System.out.println(resultFile);
			
				}
			}
			results.clear();
			choice = displayMenu();
		}
		System.out.println("Thanks for using the recursive file searcher!");
	}
	
	private static ArrayList<String> search( File currentFile, String extToFind, String nameToFind, String content, String modDate, ArrayList<String> results )
	{
		if ( !(currentFile.exists()) )
		{
			System.out.println("\nThe specified directory does not exist. Try again.\n");
			results.add("None");
			return results;
		}
		if ( currentFile.isDirectory() )
		{
			File[] searchMe = currentFile.listFiles();
			for ( File file : searchMe )
			{
				search(file, extToFind, nameToFind, content, modDate, results);
			}
		}
		else
		{
			Scanner searcher = null;
			try {
				searcher = new Scanner(currentFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String extension = "";
			String fileName = currentFile.getName();
			int i = fileName.lastIndexOf('.');
			if (i >= 0) 
			{
			    extension = fileName.substring(i+1);
			}
			fileName = fileName.replaceFirst("[.][^.]+$", "");
			String contentInFile = searcher.findWithinHorizon(content, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateToCompare = sdf.format(currentFile.lastModified());
			if ( extToFind.equals("") || extToFind.equalsIgnoreCase(extension) )
			{
				if ( nameToFind.equals("") || nameToFind.equalsIgnoreCase(fileName) )
				{
					if ( content.equals("") || contentInFile != null )
					{
						if ( modDate.equals("") || modDate.equals(dateToCompare) )
						{
							results.add(currentFile.getAbsolutePath());
						}
					}
				}
			}
		}
		return results;
	}
	
	private static String[] getOptions()
	{
		Scanner kb = new Scanner(System.in);
		String directory, extension, name, content, modDate;
		String[] options = new String[5];
		System.out.println("Directory search by path, name, extension, content and date.");
		System.out.print("Enter starting directory for the search (like c:\\temp): ");
		directory = kb.nextLine();
		System.out.print("Enter the file name (like myFile or enter for all): ");
		name = kb.nextLine();
		System.out.print("Enter the file extension (like txt or enter for all): ");
		extension = kb.nextLine();
		System.out.print("Enter content to search for (like cscd211 or enter for any): ");
		content = kb.nextLine();
		System.out.print("Enter last modified date (like DD/MM/YYYY or enter for any): ");
		modDate = kb.nextLine();
		options[0] = directory;
		options[1] = extension;
		options[2] = name;
		options[3] = content;
		options[4] = modDate;
		return options;
	}
	
	public static int displayMenu()
	{
		  int choice = 0;
	      Scanner kb = new Scanner(System.in);
	      while (choice != 1 && choice != 2)
	      {
	         try
	         {  //Displayed Menu Text Below
	            System.out.println("\n1.\tSearch\n2.\tQuit");
	            System.out.print("\nEnter a menu option: ");
	            choice = kb.nextInt();
	            if ( choice != 1 && choice != 2)
	            {
	               throw new InputMismatchException("Error. That is not a valid menu option.");
	            }            
	         }
	         catch (Exception e)
	         {
	            System.out.println("Error. That is not valid menu option.");
	            kb.nextLine();  //must flush buffer to avoid infinite error messages
	         }
	      } 
	      return choice;
	 }//End displayMenu Method

	
}