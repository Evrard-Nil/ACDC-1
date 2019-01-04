package web_inria.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Class Markdown that contains the methods relatives to the markdown
 * 
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */
public class Markdown {

	/**
	 * Method that transform the input post data into a markdown string
	 * 
	 * @param post : Post - post data
	 * @return markdownString : String - mardown string
	 */
	public static String toMarkdown(Post post) {
		StringBuilder sb = new StringBuilder();

		createMarkdownStructure(sb, post);
		addImagesToMarkdown(sb, post);
		addLinksToMarkdown(post, sb);

		return sb.toString();
	}

	/**
	 * Method that add the links to the markdown string
	 * 
	 * @param post : Post - post data
	 * @param sb   : StringBuilder - current string builder that contains the
	 *             markdown string
	 */
	private static void addLinksToMarkdown(Post post, StringBuilder sb) {
		if (!post.getLinkList().isEmpty()) {
			for (String link : post.getLinkList()) {
				if (link.length() > 0) {
					sb.append("\n[" + link + "](" + link + ")");
				}
			}
		}
	}

	/**
	 * Method that add the images to the markdown string
	 * 
	 * @param post : Post - post data
	 * @param sb   : StringBuilder - current string builder that contains the
	 *             markdown string
	 */
	private static void addImagesToMarkdown(StringBuilder sb, Post post) {
		if (!post.getImageList().isEmpty()) {
			for (String image : post.getImageList()) {
				if (image.length() > 0) {
					sb.append("\n![" + image + "](" + image + ")");
				}
			}
		}
	}

	/**
	 * Method that create the main strucutre of the markdown string
	 * 
	 * @param post : Post - post data
	 * @param sb   : StringBuilder - current string builder that contains the
	 *             markdown string
	 */
	private static void createMarkdownStructure(StringBuilder sb, Post post) {
		sb.append("---" + "\nlayout: " + post.getLayout() + "\ntitle: \"" + post.getTitle() + "\"" + "\ndate: "
				+ post.getDate() + "\ncategories: " + post.getCategory() + "\n---" + "\n\n*" + post.getAuthor() + "*"
				+ "\n\n" + post.getContent() + "\n");
	}

	/**
	 * Method that creates the markdown file
	 * 
	 * @param markdownString : String - string that contains the markdown
	 * @param post           : Post - post data
	 * @return file : File - file that contains the markdown
	 */
	public static File createMarkdownFile(String markdownString, Post post) {
		String filename = post.getDate() + "-" + post.getTitle().replaceAll(" ", "-") + ".markdown";

		String localRepo = PropertiesAccess.getInstance().getLocalRepository();

		File file = new File(localRepo + File.separator + "_posts" + File.separator + filename);
		try {
			file.createNewFile();
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
			BufferedWriter fout = new BufferedWriter(writer);
			fout.write(markdownString);
			fout.close();
			writer.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
