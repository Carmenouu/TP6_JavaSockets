import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Master {
	//Nombre de requêtes = nb de workers
	static int p = 2 ;
	
	
	public static void main(String[] args){
		Float serveurs[] = new Float[p];
		try {
	
			     //On se connecte 
		     Socket sock = new Socket("localhost", 1024);
		     Socket sock2 = new Socket("localhost", 1025);
	    
	    	 String request = "PI";
	    	 
	    	 //nous créons donc un flux en écriture
		     BufferedOutputStream bos = new BufferedOutputStream(sock.getOutputStream());
		     BufferedOutputStream bos2 = new BufferedOutputStream(sock2.getOutputStream());
		     
		     //nous écrivons notre requête
		     bos.write(request.getBytes());
		     bos2.write(request.getBytes());
		     //Vu que nous utilisons un buffer, nous devons utiliser la méthode flush
		     //afin que les données soient bien écrite et envoyées au serveur
		     bos.flush();
		     bos2.flush();
		     
		     //On récupère maintenant la réponse du serveur 
		     //dans un flux, comme pour les fichiers...
		     BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
		     
		     
		     //Il ne nous reste plus qu'à le lire
		     String response = "";
		      int stream;
		      byte[] b = new byte[4096];
		      stream = bis.read(b);
		      response = new String(b, 0, stream);      
		      
			 
			 serveurs[0] = Float.valueOf(response) ;
	  
		     
		     
		     //On récupère maintenant la réponse du serveur 
		     //dans un flux, comme pour les fichiers...
		     BufferedInputStream bis2 = new BufferedInputStream(sock2.getInputStream());
		     
		     
		     //Il ne nous reste plus qu'à le lire
		     String response2 = "";
		      int stream2;
		      byte[] b2 = new byte[4096];
		      stream2 = bis2.read(b2);
		      response2 = new String(b2, 0, stream2);      
		      
			 
			 serveurs[1] = Float.valueOf(response2) ;
	  
			 sock.close();
		     sock2.close();
		}
		
		 catch (IOException e){
			 e.printStackTrace();
		 }
		
		double moyenne = 0 ;
		for (int res=0; res<p; res++) {
			System.out.println("Worker "+res+" : "+serveurs[res]);
			moyenne+=serveurs[res] ;
		}
		moyenne = moyenne/p ;
		System.out.println("L'approximation de pi obtenue est : "+moyenne);
	}
	          
}
