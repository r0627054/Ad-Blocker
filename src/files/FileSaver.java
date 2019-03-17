package files;

import java.io.File;
import java.io.FileOutputStream;

/**
 * The FileSaver class is a class making it easy to save files.
 * It is used to safe HTML files, image files,...
 *
 */
public class FileSaver {

	/**
	 * The variable storing the content.
	 */
	private byte[] content;
	
	/**
	 * The variable storing the hostname
	 */
	private String hostname;
	
	/**
	 * The variable storing the filename
	 */
	private String filename;
	
	/**
	 * The variable storing the fileType.
	 */
	private String filetype;
	
	/**
	 * The variable storing the path of the current directory to the workspace.
	 */
	private final String CLIENT_PATH;
	
	/**
	 * Initialise a fileSaver with the given variables, at the current directory.
	 * @param content
	 *        | the content which needs to be saved to the file.
	 * @param host
	 *        | the host of where the resource is downloaded from.
	 * @param filename
	 *        | the name of the file.
	 * @param filetype
	 *        | the type of the file.
	 *       
	 */
	public FileSaver(byte[] content, String host, String filename, String filetype){
		CLIENT_PATH = System.getProperty("user.dir");
		this.setContent(content);
		this.setHostname(host);
		this.setFilename(filename);
		this.setFiletype(filetype);
		this.createFolderForHost();
		this.saveContent();
	}
	
	/**
	 * Creates a directory if it does not already exists.
	 */
	private void createFolderForHost() {
			File directory = new File(this.getDirectory());
			//create directory of the host if it does not exist
		    if (! directory.exists()){
		        directory.mkdir();
		    }
	}
	
	/**
	 * Saves the file the file with the content.
	 */
	public void saveContent() {
		String filename = this.getDirectory() + File.separator + this.getFilename() + "." + this.getFiletype();
			try (FileOutputStream fos = new FileOutputStream(filename)) {
				   fos.write(this.getContent());
			}catch (Exception e) {
				throw new IllegalArgumentException("Couldn't save the content");
			}
	}
	
	/**
	 * Returns the directory in which the file needs to be saved.
	 * In the format: /resources/client/hostname
	 */
	public String getDirectory() {
		return CLIENT_PATH + File.separator + "resources" + File.separator + "client" + File.separator + this.getHostname();
	}
	
	/**
	 * Returns the content which needs to be saved.
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Sets the content, which needs to be saved.
	 * @param content
	 *        | the content which needs to be saved.
	 * @throws IllegalArgumentException if the content equals null
	 */
	public void setContent(byte[] content) {
		if(content == null) {
			throw new IllegalArgumentException("Content cannot be null.");
		}
		this.content = content;
	}

	/**
	 * Returns the hostname of which the content is downloaded.
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the hostname of which the content is downloaded.
	 * @param hostname
	 *        | the hostname
	 *  @throws IllegalArgumentException if the hostname equals null.
	 */
	private void setHostname(String hostname) {
		if(hostname == null) {
			throw new IllegalArgumentException("Hostname cannot be null.");
		}
		this.hostname = hostname;
	}

	/**
	 * Returns the name of the file.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the file name to the given name.
	 * @param filename
	 *        | the name of the file
	 * @throws IllegalArgumentException if the filename equals null
	 */
	private void setFilename(String filename) {
		if(filename == null) {
			throw new IllegalArgumentException("Filename cannot be null.");
		}
		this.filename = filename;
	}

	/**
	 * Returns the type of the file.
	 */
	public String getFiletype() {
		return filetype;
	}

	/**
	 * Sets the type of the file.
	 * @param filetype
	 *        | the type of the file.
	 * @throws IllegalArgumentException if the filetype equals null.
	 */
	private void setFiletype(String filetype) {
		if(filetype == null) {
			throw new IllegalArgumentException("Hostname cannot be null.");
		}
		this.filetype = filetype;
	}
	
	
	
}
