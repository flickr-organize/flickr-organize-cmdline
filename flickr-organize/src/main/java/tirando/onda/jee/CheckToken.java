package tirando.onda.jee;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.scribe.model.Token;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;

public class CheckToken {

	public static void main(String[] args) throws Exception {
		String userHome = System.getProperty("user.home");
		String fileSparator = System.getProperty("file.separator");
		String fileName = ".flickr-token";
		
		Token requestToken = null;
		try {
			File file = new File(userHome+fileSparator+fileName);
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream oi = new ObjectInputStream(fin);
			requestToken = (Token) oi.readObject();
			oi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
		AuthInterface authInterface = flickr.getAuthInterface();
		
		Auth auth = authInterface.checkToken(requestToken);
		
		System.out.println("nsid: " + auth.getUser().getId());
		System.out.println("Realname: " + auth.getUser().getRealName());
		System.out.println("Username: " + auth.getUser().getUsername());
		System.out.println("Permission: " + auth.getPermission().getType());		
	}

}
