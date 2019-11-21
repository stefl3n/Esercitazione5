//LENTINI STEFANO 0000826388
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) throws IOException{
		InetAddress hostServerStream = null;
		int portServerStream = -1;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Socket serverSocket;
		String servizio, categoria;
		DataInputStream inSock;
		DataOutputStream outSock;
		
		try{
			if(args.length==2){
				hostServerStream = InetAddress.getByName(args[0]);
				portServerStream = Integer.parseInt(args[1]);
			}else{
				System.out.println("Usage: java Client hostServerStream portServerStream");
				System.exit(1);
			}
		}
		catch (Exception e) {
		      System.out.println("Problemi, i seguenti: ");
		      e.printStackTrace();
		      System.out.println("Usage: java Client hostServerStream portServerStream");
		      System.exit(2);
		}
		
		System.out.println("Inserisci il servizio richiesto (VC VP Vo D)");
		while((servizio = br.readLine())!= null){
			
			if(!servizio.equals("VC") && !servizio.equals("VP") && !servizio.equals("Vo") && !servizio.equals("D")){
				System.out.println("Funzionalità richiesta inesistente");
				System.out.println("Funziolità presenti: VC VP Vo D");
				continue;
			}
			
			//crezione socket
			try{
				serverSocket = new Socket(hostServerStream, portServerStream);
				serverSocket.setSoTimeout(30000);
				System.out.println("Creata la socket: " + serverSocket);
				inSock = new DataInputStream(serverSocket.getInputStream());
				outSock = new DataOutputStream(serverSocket.getOutputStream());
			}
			catch (IOException ioe) {
		        System.out.println("Problemi nella creazione degli stream su socket: ");
		        ioe.printStackTrace();
		        System.out.print("\n^D(Unix)/^Z(Win)+invio per uscire,"
		            + " solo invio per continuare: ");
		        // il client continua l'esecuzione riprendendo dall'inizio del ciclo
		        continue;
		    }
			
			if(servizio.equals("VC")){
				System.out.print("Inserisci il nome della categoria richiesta: ");
				categoria = br.readLine();
				int res, nVoti;
				String nome;
				//utilizzo socket
				outSock.writeUTF(servizio);
				
				outSock.writeUTF(categoria);
				
				res = inSock.readInt();
				
				if(res>-1){
					while(!(nome=inSock.readUTF()).equals("F")){
						nVoti = inSock.readInt();
						System.out.println(nome+": "+nVoti+"\n");
					}
				}else{
					System.out.println("Categoria non presente. Richiedi nuovamente il servizio");
					continue;
				}
				
			}
			else if(servizio.equals("VP")){
				String cantante1, cantante2, cantante3;
				String categoria1, categoria2, categoria3;
				int nV1, nV2, nV3;
				
				cantante1 = inSock.readUTF();
				categoria1 = inSock.readUTF();
				nV1 = inSock.readInt();
				
				cantante2 = inSock.readUTF();
				categoria2 = inSock.readUTF();
				nV2 = inSock.readInt();
				
				cantante3 = inSock.readUTF();
				categoria3 = inSock.readUTF();
				nV3 = inSock.readInt();
				
				System.out.println("PRIMO POSTO: "+cantante1+"categoria: "+categoria1+ " voto: " +nV1);
				System.out.println("PRIMO POSTO: "+cantante2+"categoria: "+categoria2 +" voto: " +nV2);
				System.out.println("PRIMO POSTO: "+cantante3+"categoria: "+categoria3 +" voto: " +nV3);

			}
			else if(servizio.equals("Vo")){
				System.out.print("Inserisci il nome del cantante a cui aggiungere 1 voto: ");
				String cantante = br.readLine();
				int nVoti;
				
				outSock.writeUTF(cantante);
				nVoti = inSock.readInt();
				
				if(nVoti>-1){
					System.out.println(cantante+ " ha ottenuto: " +nVoti+ " voti");
				}
				else{
					System.out.println("Il cantante non esiste");
					continue;
				}
			}
			
			System.out.println("Inserisci il servizio richiesto (VC VP Vo)");
		}
	}
	
}
