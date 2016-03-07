package tirando.onda.jee;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;

import org.scribe.model.Token;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;

public class Upload {
	
	private Token requestToken;
	
	public Upload() {
		String userHome = System.getProperty("user.home");
		String fileSparator = System.getProperty("file.separator");
		String fileName = ".flickr-token";
		
		try {
			File file = new File(userHome+fileSparator+fileName);
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fin);
			requestToken = (Token) oi.readObject();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(File file, Collection<String> tags, String contentType, String fileMimeType) throws Exception {
		Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
		AuthInterface authInterface = flickr.getAuthInterface();
		
		Auth auth = authInterface.checkToken(requestToken);
		
		RequestContext.getRequestContext().setAuth(auth);
		
		Uploader uploader = flickr.getUploader();
		
		UploadMetaData metaData = new UploadMetaData();
		metaData.setTitle(file.getName());
		metaData.setTags(tags);
		
		metaData.setFilename(file.getName());
		
		metaData.setContentType(contentType);
		metaData.setFilemimetype(fileMimeType);
		
		System.out.print(file.getName() + " " + tags + " ");
		
		String photoId = "";
		try {
			photoId = uploader.upload(file, metaData);
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("new try");
				Thread.sleep(60000);
				photoId = uploader.upload(file, metaData);
			} catch (Exception e1) {
				throw e1;
			}
		}

		System.out.println(photoId);
	}

	public static void main(String[] args) throws Exception {
		Upload upload = new Upload();
		upload.execute(
				new File("/media/heitorrapcinski/RPCNSK/Photo/2005/20050703 - Foto de Formatura da Juliana/MOV01827.MPG"),
				null, Flickr.CONTENTTYPE_OTHER, "video/mpeg");
	}

}
