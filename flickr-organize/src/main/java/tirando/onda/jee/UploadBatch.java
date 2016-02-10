package tirando.onda.jee;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.flickr4java.flickr.Flickr;

public class UploadBatch {
	
	private Map<String, Integer> count = new HashMap<String, Integer>();
	public long tagTime = System.currentTimeMillis();
	
	private void upload(File source) throws Exception {
		
		if (source.isDirectory()) {
			String[] list = source.list();

			for (int index = 0; index < list.length; index++) {
				File newSource = new File(source, list[index]);
				upload(newSource);
			}
			if (count.containsKey("Directory")) {
				count.put("Directory", Integer.sum(count.get("Directory").intValue(),1));
			} else {
				count.put("Directory", 1);
			}
			System.out.println(count);	
		} else {
			
			StringTokenizer st = new StringTokenizer(source.getParent(), " /-");
			
			Collection<String> tags = new ArrayList<String>();
			while (st.hasMoreElements()) {
				tags.add(st.nextElement().toString());
			}
			tags.add("Importado"+tagTime);
			
			String mimeType = Files.probeContentType(Paths.get(source.getPath()));
			
			
			if (count.containsKey(mimeType)) {
				count.put(mimeType, Integer.sum(count.get(mimeType).intValue(),1));
			} else {
				count.put(mimeType, 1);
			}
			System.out.println(count);
			
			String contentType;
			if (mimeType.equals("image/jpeg")) {
				contentType = Flickr.CONTENTTYPE_PHOTO;
			} else {
				contentType = Flickr.CONTENTTYPE_OTHER;
			}

			if (mimeType.equals("image/jpeg") || mimeType.equals("video/mpeg") || mimeType.equals("video/quicktime")) {
				Upload up = new Upload();
				up.execute(source, tags, contentType, mimeType);
			}
			
			System.out.println(count);
		}	
	}

	public static void main(String[] args) throws Exception {
		File source = new File("/media/heitorrapcinski/RPCNSK/Photo/2005/");
		
		UploadBatch up = new UploadBatch();
		up.upload(source);
	}

}
