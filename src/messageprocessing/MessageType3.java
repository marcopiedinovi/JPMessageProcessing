package messageprocessing;

public class MessageType3 extends Message {

	private MessageHelper mh = new MessageHelper();
	
	public MessageType3() {
		buffMessage = new StringBuffer("3-");	//Type
		setType("3");
		
		int tmp = mh.getRandomNumberInRange( 0, Operation.values().length -1);
		buffMessage.append(Operation.values()[tmp].toString()+"-");		//Operation
		setOperation(Operation.values()[tmp].toString());
		
//		tmp = mh.getRandomNumberInRange( 1, 100);		//Qty
//		buffMessage.append(Integer.toString(tmp)+"-");
		
		tmp = mh.getRandomNumberInRange( 0, Good.values().length -1);
		buffMessage.append(Good.values()[tmp].toString()+"-");		//Good
		setGood(Good.values()[tmp].toString());
		
		tmp = mh.getRandomNumberInRange( 1, 100); 
		buffMessage.append(Integer.toString(tmp)+"p");		//Unit price
		setAmount(Integer.toString(tmp)+"p");
	}
	
	public String getMessageAsString(){
		return buffMessage.toString();
	}

}
