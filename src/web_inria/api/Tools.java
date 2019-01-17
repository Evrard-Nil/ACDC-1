package web_inria.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import web_inria.ihm.FXMLController;;
/**
 * Class Tools that contains different methods
 * 
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */
public class Tools {
	public static Process process;

	/**
	 * Method that get the input of the user
	 * 
	 * @return userInput : String - user's input
	 */
	public static String getStringUserInput() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String userInput = null;
		try {
			userInput = input.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInput;
	}

	/**
	 * Method that execute a command depending of the user's OS
	 * 
	 * @param command : String - the command to execute
	 * @param path    : String - path to execute the command
	 * @param isDemo  : boolean - to know if the command is to show the demo or not
	 */
	public static void executeCommand(String command, String path, boolean isDemo) {
		final boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
			builder.command("cmd.exe", "/c", command);
		} else {
			builder.command("sh", "-c", command);
		}
		try {
			builder.directory(new File(path));
			process = builder.start();
			TimeUnit.SECONDS.sleep(3);
			if (!isDemo) {
				int exitCode = process.waitFor();
				assert exitCode == 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void executeCmd(String cmd, String path) {
		executeCommand(cmd, path, false);
	}

	/**
	 * Method that runs the commit and push commands of Git
	 * 
	 * @param localRepository : String - link to the local website
	 */
	public static void gitCommitAndPush(String localRepository) {
		Tools.executeCmd("git add .", localRepository);
		Tools.executeCmd("git commit -m \"Add markdown file \"", localRepository);
		Tools.executeCmd("git push", localRepository);
	}
}
