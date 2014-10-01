package tirando.onda.jee;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;

import org.scribe.model.Token;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.AuthInterface;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;

public class DeleteAll {

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
		
		while (true) {
			Flickr flickr = new Flickr("dc197c9a770087f7989fc4f5dbfdef40", "5092e6a6a1c841ec", new REST());
			AuthInterface authInterface = flickr.getAuthInterface();
			
			Auth auth = authInterface.checkToken(requestToken);
	
			RequestContext.getRequestContext().setAuth(auth);
			
			PhotosInterface photosInterface = flickr.getPhotosInterface();
			
			PhotoList<Photo> photoList = photosInterface.getContactsPhotos(10, false, false, true);
			
			if (photoList.getTotal() == 0) {
				break;
			}
			
			for (Iterator<Photo> iterator = photoList.iterator(); iterator.hasNext();) {
				Photo photo = (Photo) iterator.next();
				System.out.println(photo.getId());
				System.out.println(photo.getTitle());
				System.out.println(photo.getOriginalFormat());
				try {
					photosInterface.delete(photo.getId());
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			
		}
		
	}

}
