package tirando.onda.jee;

import java.util.Scanner;

import org.scribe.model.Token;
import org.scribe.model.Verifier;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.auth.Permission;


public class RequestToken {

	public static void main(String[] args) throws Exception {
		Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
		AuthInterface authInterface = flickr.getAuthInterface();
		
		Token token = authInterface.getRequestToken();
		String url = authInterface.getAuthorizationUrl(token, Permission.DELETE);
		
		System.out.println("Entre na url e autorize o token");
		System.out.println(url);
		System.out.println("Cole o código da página:");
		System.out.print(">>");		
		
		Scanner scanner = new Scanner(System.in);
		String tokenKey = scanner.nextLine();
		scanner.close();
		
		Token requestToken = authInterface.getAccessToken(token, new Verifier(tokenKey));
		System.out.println("Token Gerado...");

		Auth auth = authInterface.checkToken(requestToken);
		System.out.println("Token: " + requestToken.getToken());
		System.out.println("Secret: " + requestToken.getSecret());
		System.out.println("nsid: " + auth.getUser().getId());
		System.out.println("Realname: " + auth.getUser().getRealName());
		System.out.println("Username: " + auth.getUser().getUsername());
		System.out.println("Permission: " + auth.getPermission().getType());		
	}

}
