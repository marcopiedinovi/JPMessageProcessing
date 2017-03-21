package messageprocessing;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessing {

	List<Message> msgList = new ArrayList<Message>();
	
	public static void main(String[] args){
		MessageProcessing mp = new MessageProcessing();
		mp.startProcess();
	}
	
	private void startProcess(){
		
		MessageSupplier mSup = new MessageSupplier();
		//MessageProcessor mProc = new MessageProcessor();
		int msgNumber = 0;
		while(msgNumber < 50) {
			Message msg = mSup.get();
			System.out.println(msg.getMessageAsString());
			msgList.add(msg);
			if( messageIsType3(msg) == true){
				processType3(msg);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(msgNumber > 0 && msgNumber % 10 == 0) {
				printReport10();
			}
			
			msgNumber++;
		}
		printReport50();
			
	}
	
	void printReport10(){
		
		System.out.println("---------------------------");
		System.out.println("   Good       Qty    amount");
		for(Good good : Good.values()){
			//System.out.print(String.format("%s",good.toString()));
			
			long qty = msgList.stream()
					.filter(msg -> msg.getType() != "3" && msg.getGood().equals(good.toString()))
					.mapToInt(msg -> msg.getQty()).sum();
			
			//System.out.print(" " + qty + " ");
			
			long totAmount = msgList.stream()
					.filter(msg -> msg.getType() != "3" && msg.getGood().equals(good.toString()))
					.mapToInt(msg -> msg.getOperationAmount()).sum();
			
			System.out.println(String.format("%12s %12s %12s", good, qty, totAmount));
		}
		System.out.println("---------------------------");
	}
	
	void printReport50(){

		System.out.println("---------------------------");
		System.out.println("Good    Operation    amount");
		for(Good good : Good.values()){
			System.out.println("["+good.toString()+"]");
			msgList.stream()
			.filter(msg -> msg.getType() == "3" && msg.getGood().equals(good.toString()))
			.forEach(msg -> System.out.println( msg.getOperation() + " " + msg.getAmount()));
		}
		System.out.println("---------------------------");
		
		System.out.println("maximum Op number reached, pausing.");
	}

	boolean messageIsType3(Message msg) {
		
		boolean type3 = false;
		String strMsg = msg.getMessageAsString();
		if( strMsg.startsWith("3")){
			type3 = true;
		}
		return type3;
	}
	
	void processType3(Message msg){
		
		String[] parts = msg.getMessageAsString().split("-");
		
		String currentOp = "";
		int opIndx;
		for( opIndx = 0; opIndx< Operation.values().length; opIndx++) {
			if(parts[1].toUpperCase().equals(Operation.values()[opIndx].toString())){
				currentOp = Operation.values()[opIndx].toString();
				break;
			}
		}

		int currentAmount = 0;
		try {
			currentAmount = Integer.parseInt(parts[3].substring(0, parts[3].length()-1)); //amount == 20p should get only '20'
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String currentGood = "";
		int goodIndx;
		for( goodIndx = 0; goodIndx < Good.values().length; goodIndx++) {
			if(parts[2].equals(Good.values()[goodIndx].toString())) {
				currentGood = Good.values()[goodIndx].toString();
				break;
			}
		}
		
		adjustMessages(currentOp, currentAmount, currentGood);
	}
	
	Message processType1and2(Message msg, String op, int amount, String good) throws NumberFormatException{
		String[] arryMsg = msg.getMessageAsString().split("-");
//		for(String s : arryMsg){
//			System.out.println("["+s+"]");
//		}
		String posPenny = "p";
		String negPenny = "n";
		String penny = posPenny;
		
		int currentQty = 0;
		int currentAmount = 0;
		String strAmount = "";
		String strQty = "";
		if(arryMsg[2].equals(good)){
			currentQty = Integer.parseInt(arryMsg[1]);
			currentAmount = Integer.parseInt(arryMsg[3].substring(0, arryMsg[3].length()-1));
			if(op == Operation.ADD.toString()){
				currentAmount += amount;
			} else if(op == Operation.SUBTRACT.toString()){
				currentAmount -= amount;
				if(currentAmount < 0){
					currentAmount = -currentAmount;
					penny = negPenny;
				}
			} else if(op == Operation.MULTIPLY.toString()){
				currentAmount *= amount;
			}
			strQty = Integer.toString(currentQty);
			strAmount = Integer.toString(currentAmount)+penny;
			arryMsg[3] = strAmount;
		}
		
		Message outMessage;
		if(arryMsg[0].equals("1")){
			outMessage = new MessageType1(good, strAmount);
		} else {
			outMessage = new MessageType2(strQty, good, strAmount);
		}
		System.out.println("Reprocessed: "+outMessage.getMessageAsString());
		return outMessage;
	}

	void adjustMessages(String op, int amount, String good) {

		int indx;
		for( indx = 0; indx < msgList.size(); indx++) {
			Message msg = msgList.get(indx);
			if( (msg.getType() == "1" || msg.getType() == "2") && msg.getGood() == good) {
				
				Message adjustedMsg = processType1and2(msg, op, amount, good);
				msgList.set(indx, adjustedMsg);
			}
		}
	}
}
