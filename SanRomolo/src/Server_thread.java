//LENTINI STEFANO 0000826388
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Server_thread extends Thread {
	private Socket clientSocket = null;
	private Cantante[] c;

	public Server_thread(Socket clientSocket, Cantante c[]){
		this.clientSocket = clientSocket;
		this.c = c;
	}
	
	public void run(){
		DataInputStream inSock;
		DataOutputStream outSock;
		String servizio, categoria;
		int res = -1;
		
		try{
			//creazione stream di input/output da socket
			inSock = new DataInputStream(this.clientSocket.getInputStream());
			outSock = new DataOutputStream(this.clientSocket.getOutputStream());
			
				
				while(true){
					//lettura tipo di servizio richiesto
					try{
					servizio = inSock.readUTF();
					}
					catch(EOFException e){
						clientSocket.close();
						return;
					}
					
					if(!servizio.equals("VC") && !servizio.equals("VP") && !servizio.equals("Vo") && !servizio.equals("D")){
						System.out.println("Funzionalità richiesta inesistente");
						continue;
					}
				
					if(servizio.equals("VC")){
						System.out.println("Ricevuta richiesta di visualizzazione cantanti per categoria");
					
						//ricevo categoria
						categoria = inSock.readUTF();
					
						if(!categoria.equals("Campioni") && !categoria.equals("NuoveProposte")){
							outSock.writeInt(res);
							System.out.println("Categoria non presente");
							continue;
						}
						res = 0;
						outSock.writeInt(res);
						
						for(int i=0; i<c.length; i++){
							if(c[i].getCategoria().equals(categoria)){
								outSock.writeUTF(c[i].getNome());
								outSock.writeInt(c[i].nVoti);
							}
						}
						outSock.writeUTF("F");
						
					}
					else if(servizio.equals("VP")){
						System.out.println("Ricevuta richiesta di visualizzazione del podio attuale");
						int i1 = 0, i2 = 1, i3 = 2;
						int max1=0, max2=0, max3=0;
						
						for(int i=0; i<c.length; i++){
							if(c[i].nVoti>max1){
								i1=i;
							}
							else if(c[i].nVoti>max2){
								i2=i;
							}
							else if(c[i].nVoti>max3){
								i3=i;
							}
						}
						
						outSock.writeUTF(c[i1].nome);
						outSock.writeUTF(c[i1].Categoria);
						outSock.writeInt(max1);
						
						outSock.writeUTF(c[i2].nome);
						outSock.writeUTF(c[i2].Categoria);
						outSock.writeInt(max2);
						
						outSock.writeUTF(c[i3].nome);
						outSock.writeUTF(c[i3].Categoria);
						outSock.writeInt(max3);
					}
					else if(servizio.equals("Vo")){
						String cantante = inSock.readUTF();
						boolean trovato = false;
						
						for(int i=0; i<c.length && !trovato; i++){
							if(c[i].getNome().equals(cantante)){
								trovato = true;
								outSock.writeInt(c[i].addVote());
							}
						}
						if(!trovato){
							outSock.writeInt(res);
						}
					}
					else if(servizio.equals("D")){
						
					}
				}
			
		}
		catch (IOException ioe) {
		      System.out
		          .println("Problemi nella creazione degli stream di input/output "
		              + "su socket: ");
		      ioe.printStackTrace();
		      // il server continua l'esecuzione riprendendo dall'inizio del ciclo
		      return;
		    }
		    catch (Exception e) {
		      System.out
		          .println("Problemi nella creazione degli stream di input/output "
		              + "su socket: ");
		      e.printStackTrace();
		      // il server continua l'esecuzione riprendendo dall'inizio del ciclo
		      return;
		}
	}
}
