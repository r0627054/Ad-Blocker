package adblocker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The AdBlocker class contains a string of html.
 *  and a list of keywords which are blocked.
 *
 */
public class AdBlocker {
	
	/**
	 * Variable storing the String of html.
	 */
	private String html;
	
	/**
	 * Variable storing all the keywords of the ads
	 */
	private List<String> adKeyWords;
	
	/**
	 * Initialises the AdBlocker with a raw html file and a list of key words which are blocked.
	 * @param html
	 *        | the html files
	 * @param adKeyWords
	 *        | the list of keywords which are blocked
	 */
	public AdBlocker(String html,List<String> adKeyWords) {
		this.setHtml(html);
		this.setAdKeyWords(adKeyWords);
	}

	/**
	 * Returns the html variable
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * Sets the html variable
	 * @param html
	 *        | the string containing html
	 * @throws IllegalArgumentException when the html String equals null
	 */
	private void setHtml(String html) {
		if(html == null) {
			throw new IllegalArgumentException("The html of the AdBlocker cannot be null.");
		}
		this.html = html;
	}

	/**
	 * Returns the list of keywords.
	 */
	public List<String> getAdKeyWords() {
		return adKeyWords;
	}

	/**
	 * The list of keywords added to the adBlocker
	 * @param adKeyWords
	 *        | the list of keywords 
	 * @throws IllegalArgumentException when the list of keywords equals null.
	 */
	private void setAdKeyWords(List<String> adKeyWords) {
		if(adKeyWords == null) {
			throw new IllegalArgumentException("The adKeyWords list cannot be null.");
		}
		this.adKeyWords = adKeyWords;
	}
	
	/**
	 * Removes the images tags which contains a word of the list of adds.
	 */
	public void removeAdImages() {
		String editedHtml = this.getHtml();
		Pattern p = Pattern.compile("<img[^>]*src=[\"']([^\"^']*)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(this.getHtml());
		while(m.find()) {
			String newPath = m.group(1);
			for(String blackListed : this.getAdKeyWords()) {
				if(newPath.contains(blackListed)) {
					//removes the tag
					editedHtml = editedHtml.replaceAll("[^<img].*src=\"" + newPath +  "\".*[$>]", "");
					break;
				}
			}
		}
		this.setHtml(editedHtml);
	}
	

}
