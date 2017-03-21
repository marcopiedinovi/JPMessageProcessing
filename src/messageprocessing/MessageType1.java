package messageprocessing;

public class MessageType1 extends Message {

	private MessageHelper mh = new MessageHelper();
	
	public MessageType1() {
		buffMessage = new StringBuffer("1-");	//Type
		buffMessage.append("1-");		//Qty
		setType("1");
		setQty("1");
		
		int tmp = mh.getRandomNumberInRange( 0, Good.values().length -1);
		buffMessage.append(Good.values()[tmp].toString()+"-");		//Good
		setGood(Good.values()[tmp].toString());
		
		tmp = mh.getRandomNumberInRange( 1, 20); 
		buffMessage.append(Integer.toString(tmp)+"p");	//Unit price
		setAmount(Integer.toString(tmp)+"p");

	}
	
	public MessageType1(String good, String amount){
		buffMessage = new StringBuffer("1-").append("1-").append(good+"-").append(amount);
		setType("1");
		setQty("1");
		setGood(good);
		setAmount(amount);
	}

	public String getMessageAsString(){
		return buffMessage.toString();
	}
}
