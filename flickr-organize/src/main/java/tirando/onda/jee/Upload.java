package tirando.onda.jee;

import java.io.File;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.flickr4java.flickr.uploader.Uploader;

public class Upload {

	public static void main(String[] args) throws Exception {
		Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
		AuthInterface authInterface = flickr.getAuthInterface();
		
		Auth auth = authInterface.checkToken("72157647867938226-635cb20f9a41aeee", "b195148c2db37a45");

		RequestContext.getRequestContext().setAuth(auth);
		
		Uploader uploader = flickr.getUploader();
		
		File file = new File("/home/heitorrapcinski/Imagens/2014/20140525 - Férias em Maragogi/DSC07881.JPG");
		
		UploadMetaData metaData = new UploadMetaData();
		metaData.setTitle(file.getName());
		
		String photoId = uploader.upload(file, metaData);
		
		System.out.println(photoId);

	}

}
