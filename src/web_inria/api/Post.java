package web_inria.api;

import java.util.List;

/**
 * Post object to store the post data
 * 
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */
public class Post {

	private String layout;
	private String title;
	private String date;
	private String category;
	private String content;
	private List<String> linkList;
	private List<String> imageList;

	/**
	 * Constructor of the post object
	 * 
	 * @param layout    : String - layout of the post
	 * @param title     : String - title of the post
	 * @param date      : String - date of the day
	 * @param category  : String - category of the post
	 * @param author    : String - author of the post
	 * @param content   : String - main content of the post
	 * @param linkList  : List&lt;String&gt; - list of the links of the post
	 * @param imageList : List&lt;String&gt; - list of the images of the post
	 */
	public Post(String layout, String title, String date, String category, String content, List<String> linkList,
			List<String> imageList) {
		this.layout = layout;
		this.title = title;
		this.date = date;
		this.category = category;
		this.content = content;
		this.linkList = linkList;
		this.imageList = imageList;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}
