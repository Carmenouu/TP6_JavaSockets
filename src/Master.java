import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Master {
	//Nombre de requêtes = nb de workers
	static int p = 2 ;
	
	
	public static void main(String[] args){
		int nThrows = 160000000;
		Integer serveurs[] = new Integer[p];
		Socket[] sockets = new Socket[p];
		BufferedOutputStream bos[] = new BufferedOutputStream[p] ;
		BufferedInputStream bis[] = new BufferedInputStream[p] ;
		
		long startTime = System.currentTimeMillis();
		try {
			     //On se connecte 
			for (int i=0; i<p; i++) {
				sockets[i] = new Socket("localhost", 1024+i);
			}
	    
	    	 String request = "PI";
	    	 
	    	 for(int i=0; i<p; i++) {
		    	 //nous créons donc un flux en écriture
			     bos[i] = new BufferedOutputStream(sockets[i].getOutputStream());
			     
			     //nous écrivons notre requête
			     bos[i].write(request.getBytes());

			     bos[i].flush();
	    	 }
	    	 
		     //On récupère maintenant la réponse du serveur 
		     //dans un flux, comme pour les fichiers...
	    	 for(int i=0; i<p; i++) {
			     bis[i] = new BufferedInputStream(sockets[i].getInputStream());
			     
			     
			     //Il ne nous reste plus qu'à le lire
			     String response = "";
			      int stream;
			      byte[] b = new byte[4096];
			      stream = bis[i].read(b);
			      response = new String(b, 0, stream);      
			      
				 
				 serveurs[i] = Integer.parseInt(response) ;
	  
	    	 }
		     
		     for (int i=0; i<p; i++) {
		    	 sockets[i].close();
		     }

		}
		
		 catch (IOException e){
			 e.printStackTrace();
		 }
		
		double pi = 0 ;
		for (int res=0; res<p; res++) {
			System.out.println("Worker "+res+" : "+serveurs[res]);
			pi+=serveurs[res] ;
		}
		pi = 4.0 * pi/nThrows;
		long stopTime = System.currentTimeMillis();
		
		System.out.println("L'approximation de pi obtenue est : "+pi);
		System.out.println("Erreur : "+Math.abs((pi - Math.PI) / Math.PI));
		System.out.println("Time Duration: " + (stopTime - startTime) + "ms");
	}
	          
}
