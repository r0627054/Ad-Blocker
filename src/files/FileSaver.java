package files;

import java.io.File;
import java.io.FileOutputStream;

public class FileSaver {

	private byte[] content;
	private String hostname;
	private String filename;
	private String filetype;
	private final String CLIENT_PATH;
	
	public FileSaver(byte[] content, String host, String filename, String filetype){
		CLIENT_PATH = System.getProperty("user.dir");
		this.setContent(content);
		this.setHostname(host);
		this.setFilename(filename);
		this.setFiletype(filetype);
		this.createFolderForHost();
		this.saveContent();
	}
	
	
	private void createFolderForHost() {
			File directory = new File(this.getDirectory());
			//create directory of the host if it does not exist
		    if (! directory.exists()){
		        directory.mkdir();
		    }
	}
	
	
	public void saveContent() {
		String filename = this.getDirectory() + File.separator + this.getFilename() + "." + this.getFiletype();
			try (FileOutputStream fos = new FileOutputStream(filename)) {
				   fos.write(this.getContent());
			}catch (Exception e) {
				throw new IllegalArgumentException("Couldn't save the content");
			}
	}
	
	public String getDirectory() {
		return CLIENT_PATH + File.separator + "resources" + File.separator + "client" + File.separator + this.getHostname();
	}
	
	
	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		if(content == null) {
			throw new IllegalArgumentException("Content cannot be null.");
		}
		this.content = content;
	}


	public String getHostname() {
		return hostname;
	}


	private void setHostname(String hostname) {
		if(hostname == null) {
			throw new IllegalArgumentException("Hostname cannot be null.");
		}
		this.hostname = hostname;
	}


	public String getFilename() {
		return filename;
	}


	private void setFilename(String filename) {
		if(filename == null) {
			throw new IllegalArgumentException("Filename cannot be null.");
		}
		this.filename = filename;
	}


	public String getFiletype() {
		return filetype;
	}


	private void setFiletype(String filetype) {
		if(filetype == null) {
			throw new IllegalArgumentException("Hostname cannot be null.");
		}
		this.filetype = filetype;
	}
	
	
	
}
