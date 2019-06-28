import java.io.IOException;
import java.net.ServerSocket;


public class Worker {
	//Nombre de requêtes = nb de workers
	static int p = 1 ;
	static ServerSocket serveurs[] = new ServerSocket[p];
	
	 public static void main(String[] args) {

	      for(int i=0; i<p; i++){
	         try {
	            ServerSocket serveur = new ServerSocket(1024+i);
	            serveurs[i] = serveur ;
	         } catch (IOException e) {
	            System.err.println("Le port " + 1024+i + " est déjà utilisé ! ");
	         }
	      }
	      
	      
	   }
	

}
