import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Worker1026 {
		
	 public static void main(String[] args) {

	         try {
	            ServerSocket serveur = new ServerSocket(1026);
	            Socket s = serveur.accept() ;
	            
	            try {
	                
	                //Ici, nous n'utilisons pas les m�mes objets que pr�c�demment
	                //Je vous expliquerai pourquoi ensuite
	                PrintWriter writer = new PrintWriter(s.getOutputStream());
	                BufferedInputStream reader = new BufferedInputStream(s.getInputStream());
	                
	                //On attend la demande du client            
	                String response = read(reader);
	                InetSocketAddress remote = (InetSocketAddress)s.getRemoteSocketAddress();
	                
	                //On traite la demande du client en fonction de la commande envoy�e
	                String toSend = "";
	                
	                switch(response.toUpperCase()){
	                   case "PI":
	                      toSend = String.valueOf(calculPi());
	                      break;
	                   default : 
	                      toSend = "Commande inconnu !";                     
	                      break;
	                }
	                
	                
	                //On envoie la r�ponse au client
	                writer.write(toSend);
	                //Il FAUT IMPERATIVEMENT UTILISER flush()
	                //Sinon les donn�es ne seront pas transmises au client
	                //et il attendra ind�finiment
	                writer.flush();
	         } catch(SocketException e){
	             System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
	          } catch (IOException e) {
	             e.printStackTrace();
	          }        
	         
	      }
	      
	      catch (IOException e) {
	          System.err.println("Le port " + 1024 + " est d�j� utilis� ! ");
	       }
	        
		}

		//M�thode permettant d'estimer pi
		private static int calculPi() {
			int nThrows = 53000000;
			double x = 0, y = 0;
			int nSuccess = 0;
			for (int i = 1; i <= nThrows; i++) {
				x = Math.random();
				y = Math.random();
				if (x * x + y * y <= 1)
					nSuccess++;
			}
			return nSuccess;
		}
		
		//La m�thode que nous utilisons pour lire les r�ponses
		   private static String read(BufferedInputStream reader) throws IOException{      
		      String response = "";
		      int stream;
		      byte[] b = new byte[4096];
		      stream = reader.read(b);
		      response = new String(b, 0, stream);
		      return response;
		   }
		      
	}
