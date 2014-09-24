package tirando.onda.jee;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;

public class CheckToken {

	public static void main(String[] args) throws Exception {
		Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
		AuthInterface authInterface = flickr.getAuthInterface();
		
		Auth auth = authInterface.checkToken("72157647867938226-635cb20f9a41aeee", "b195148c2db37a45");
		
		System.out.println("nsid: " + auth.getUser().getId());
		System.out.println("Realname: " + auth.getUser().getRealName());
		System.out.println("Username: " + auth.getUser().getUsername());
		System.out.println("Permission: " + auth.getPermission().getType());		
	}

}
