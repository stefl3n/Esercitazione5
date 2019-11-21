//LENTINI STEFANO 0000826388
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int N = 5;
	
	static Cantante c[] = null;
	
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		int port = -1;
		
		//Controllo argomenti
		try{
			if(args.length==1){
				port = Integer.parseInt(args[0]);
			}else{
				System.out.println("Usage: java Server port");
				System.exit(1);
			}
		}
		 catch (Exception e) {
		      System.out.println("Problemi, i seguenti: ");
		      e.printStackTrace();
		      System.out.println("Usage: java Server port");
		      System.exit(2);
		}
		
		c = new Cantante[N];
		for(int i=0; i<N; i++)
			c[i]= new Cantante();
		
		//CANTANTI DA INIZIALIZZARE A PIACERE
		c[0].setNome("Ciccio");
		c[0].setCategoria("Campioni");
		c[0].setnVoti(10);
		
		try{
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			System.out.println("Server: avviato ");
			System.out.println("Server: creata la server socket: " + serverSocket);
		}
		catch (Exception e ){
			 System.err.println("Server: problemi nella creazione della server socket: " + e.getMessage());
	      e.printStackTrace();
	      System.exit(1);
	    }
		
		try{
			while(true){
				System.out.println("Server: in attesa di richieste...\n");
				
				try{
					clientSocket = serverSocket.accept();
					clientSocket.setSoTimeout(60000);
					System.out.println("Server: connessione accettata: " + clientSocket);
					new Server_thread(clientSocket, c).start();
				}
				catch (Exception e) {
			          System.err
			              .println("Server: problemi nella accettazione della connessione: "
			                  + e.getMessage());
			          e.printStackTrace();
			          continue;
			          // il server continua a fornire il servizio ricominciando dall'inizio
			          // del ciclo
			     }
			}
		}
		// qui catturo le eccezioni non catturate all'interno del while
	    // in seguito alle quali il server termina l'esecuzione
	    catch (Exception e) {
	      e.printStackTrace();
	      // chiusura di stream e socket
	      System.out.println("PutFileServerCon: termino...");
	      System.exit(2);
	    }
		
	}
}
