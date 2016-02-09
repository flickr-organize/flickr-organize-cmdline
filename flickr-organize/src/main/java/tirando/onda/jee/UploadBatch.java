package tirando.onda.jee;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class UploadBatch {
	
	public int count =0;
	public long tagTime = System.currentTimeMillis();
	
	private void upload(File source) throws Exception {
		
		if (source.isDirectory()) {
			String[] list = source.list();

			for (int index = 0; index < list.length; index++) {
				File newSource = new File(source, list[index]);
				upload(newSource);
			}
		} else {
			
			StringTokenizer st = new StringTokenizer(source.getParent(), "/");
			
			Collection<String> tags = new ArrayList<String>();
			while (st.hasMoreElements()) {
				tags.add(st.nextElement().toString());
			}
			tags.add("Importado"+tagTime);
			

			Upload up = new Upload();
			up.execute(source, tags);
			
			count++;
		}	
	}

	public static void main(String[] args) throws Exception {
		File source = new File("/media/heitorrapcinski/RPCNSK/Photo/2003/");
		
		UploadBatch up = new UploadBatch();
		up.upload(source);

		
		System.out.println(up.count);
	}

}
