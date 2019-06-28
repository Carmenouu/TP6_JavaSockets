import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Master {
	//Nombre de requêtes = nb de workers
	static int p = 1 ;
	
	
	public static void main(String[] args){
		Float serveurs[] = new Float[p];
		try {
	
			     //On se connecte à Wikipedia
		     Socket sock = new Socket("fr.wikipedia.org", 80);
		    
		     for (int i=0; i<p; i++) {
		    	 String request = "Calcul Pi";
		    	 
		    	 //nous créons donc un flux en écriture
			     BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
			     
			     //nous écrivons notre requête
			     bos.write(request.getBytes());
			     //Vu que nous utilisons un buffer, nous devons utiliser la méthode flush
			     //afin que les données soient bien écrite et envoyées au serveur
			     bos.flush();
			     
			     //On récupère maintenant la réponse du serveur 
			     //dans un flux, comme pour les fichiers...
			     BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
			     
			     //Il ne nous reste plus qu'à le lire
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
