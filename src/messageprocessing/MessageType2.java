package messageprocessing;

public class MessageType2 extends Message {

	private MessageHelper mh = new MessageHelper();
	
	public MessageType2() {
		buffMessage = new StringBuffer("2-");	//Type
		setType("2");
		
		int tmp = mh.getRandomNumberInRange( 1, 10);		//Qty
		buffMessage.append(Integer.toString(tmp)+"-");
		setQty(Integer.toString(tmp));
		
		tmp = mh.getRandomNumberInRange( 0, Good.values().length -1);
		buffMessage.append(Good.values()[tmp].toString()+"-");		//Good
		setGood(Good.values()[tmp].toString());
		
		tmp = mh.getRandomNumberInRange( 1, 10); 
		buffMessage.append(Integer.toString(tmp)+"p");		//Unit price
		setAmount(Integer.toString(tmp)+"p");

	}

	public MessageType2(String qty, String good, String amount){
		buffMessage = new StringBuffer("2-").append(qty+"-").append(good+"-").append(amount);
		setType("2");
		setQty(qty);
		setGood(good);
		setAmount(amount);
	}

	public String getMessageAsString(){
		return buffMessage.toString();
	}
}
