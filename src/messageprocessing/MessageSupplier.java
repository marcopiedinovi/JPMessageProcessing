package messageprocessing;

public class MessageSupplier {
	
	private MessageHelper mh = new MessageHelper();
	
	public Message get(){
		
		Message message;
		
		int type = mh.getRandomNumberInRange(1,3);
		switch(type){
		case 1:
			message = new MessageType1();
			break;
		case 2:
			message = new MessageType2();
			break;
		case 3:
			message = new MessageType3();
			break;
		default:
			message = new MessageType1();
			break;
		}
		
		return message;
	}

}
