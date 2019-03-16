package adblocker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdBlocker {
	
	private String html;
	private List<String> adKeyWords;
	
	public AdBlocker(String html,List<String> adKeyWords) {
		this.setHtml(html);
		this.setAdKeyWords(adKeyWords);
	}

	public String getHtml() {
		return html;
	}

	private void setHtml(String html) {
		if(html == null) {
			throw new IllegalArgumentException("The html of the AdBlocker cannot be null.");
		}
		this.html = html;
	}

	public List<String> getAdKeyWords() {
		return adKeyWords;
	}

	private void setAdKeyWords(List<String> adKeyWords) {
		if(adKeyWords == null) {
			throw new IllegalArgumentException("The adKeyWords list cannot be null.");
		}
		this.adKeyWords = adKeyWords;
	}
	
	public void removeAdImages() {
		String editedHtml = this.getHtml();
		Pattern p = Pattern.compile("<img[^>]*src=[\"']([^\"^']*)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(this.getHtml());
		while(m.find()) {
			String newPath = m.group(1);
			for(String blackListed : this.getAdKeyWords()) {
				if(newPath.contains(blackListed)) {
					//removes the tag
					editedHtml = editedHtml.replaceAll("<img[^>]*src=[\"']"+ newPath+  "([^\"^']*)", "");
					break;
				}
			}
		}
		this.setHtml(editedHtml);
	}
	

}
