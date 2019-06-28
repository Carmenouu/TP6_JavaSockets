import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Master {
	//Nombre de requ�tes = nb de workers
	static int p = 1 ;
	
	
	public static void main(String[] args){
		Float serveurs[] = new Float[p];
		try {
	
			     //On se connecte � Wikipedia
		     Socket sock = new Socket("fr.wikipedia.org", 80);
		    
		     for (int i=0; i<p; i++) {
		    	 String request = "Calcul Pi";
		    	 
		    	 //nous cr�ons donc un flux en �criture
			     BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
			     
			     //nous �crivons notre requ�te
			     bos.write(request.getBytes());
			     //Vu que nous utilisons un buffer, nous devons utiliser la m�thode flush
			     //afin que les donn�es soient bien �crite et envoy�es au serveur
			     bos.flush();
			     
			     //On r�cup�re maintenant la r�ponse du serveur 
			     //dans un flux, comme pour les fichiers...
			     BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
			     
			     //Il ne nous reste plus qu'� le lire
			     String content = "";
				 int stream;
				 byte[] b = new byte[1024];
				 while((stream = bis.read(b)) != -1){
					 content += new String(b, 0, stream);
				 }
				 
				 serveurs[i] = Float.valueOf(content) ;
		     }
		     
		}
		
		 catch (IOException e){
			 e.printStackTrace();
		 }
		
		for (int res=0; res<p; res++) {
			System.out.println("Worker "+res+" : "+serveurs[res]);
		}
	}
	          
}
