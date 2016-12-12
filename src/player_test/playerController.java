package player_test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.google.gson.Gson;

import views.BattleShip;

public class playerController {
	BattleShip gameView;
	String action = "Nothing";
	String actionValue = "";
	int[][] Map ;
	ExecutorService clientReceive = Executors.newSingleThreadExecutor();
	
	public playerController(BattleShip test) {
		gameView = test;
		ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
		threadExecutor.execute(new UpdateData()); //Update client
	}
	
	public void readyForStart(int[][] player2Map) {
		action = "ready";
		actionValue = "1";
		Map = player2Map.clone();
	}
	
	public void attack(int x, int y) {
		// TODO Auto-generated method stub
		action = "attack";
		actionValue = x+","+y;
	}
	
	class UpdateData implements Runnable {
		Gson gson = new Gson();

		@Override
		public void run() {
			JSONObject ClientData = new JSONObject();
			while(true){
				
				try {Thread.sleep(1000);} catch (InterruptedException e) {} 
				
				ClientData.put("action", action);
				ClientData.put("actionValue", actionValue);
				if(action.equals("ready")){
					ClientData.put("Map", gson.toJson(Map));
				}
				TCP.clientModule.sendMessage(ClientData.toString());
				
				String ServerData = TCP.clientModule.readServerMessage();
				JSONObject messageJSON = new JSONObject(ServerData); // �নJSON
				
				String action = messageJSON.get("action").toString();
				String actionValue = messageJSON.get("actionValue").toString();
				switch(action) {
		           case "Nothing":
		        	   break;
		           case "ready":
		        	   if(actionValue.equals("1")){
		        		   
		        		   gameView.gameStart = true;
		        		   if(messageJSON.get("yourTurn").toString().equals("1")){
		        			   
		        			   gameView.yourTurn = true;
		        			   JOptionPane.showMessageDialog(gameView.mainWindow, "�A������");
		        		   }
		        		   gameView.mainWindow.setTitle((Integer.parseInt(messageJSON.get("yourTurn").toString())^1)+"");
						 }
		                break; 
		            case "attack":
		            	 if(messageJSON.get("yourTurn").toString().equals("1")){
							 gameView.yourTurn = true;
							 JOptionPane.showMessageDialog(gameView.mainWindow, "���A�F");
						 }else{
							 gameView.yourTurn = false;
							 JOptionPane.showMessageDialog(gameView.mainWindow, "�S��");
						 }
		            	break;
		            case "finish":
		            	if(actionValue.equals(gameView.mainWindow.getTitle())){
							 JOptionPane.showMessageDialog(gameView.mainWindow, "�AĹ�F�j����");
						 }else{
							 JOptionPane.showMessageDialog(gameView.mainWindow, "�A��F�o��");
						 }
						 TCP.clientModule.disconnect();
		            	break;
		        }
				playerController.this.action = "Nothing";
			}
		}
		
	}
}
