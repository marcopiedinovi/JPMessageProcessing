package messageprocessing;

public abstract class Message {

	//In my opinion messages are sent as immutable Strings, in a real environment
	StringBuffer buffMessage;
		
	private String type;
	private String good;
	private int qty;
	private int amount;
	private String operation;

	protected void setType(String type) {
		this.type = type;
	}

	protected void setGood(String good) {
		this.good = good;
	}

	protected void setQty(String qty) {
		this.qty = Integer.parseInt(qty);
	}

	protected void setAmount(String amount) {
		this.amount = Integer.parseInt(amount.substring(0, amount.length()-1));
	}

	protected void setOperation(String operation) {
		this.operation = operation;
	}

	public String getType() {
		return type;
	}

	public String getGood() {
		return good;
	}

	public int getQty() {
		return qty;
	}

	public int getAmount() {
		return amount;
	}

	public int getOperationAmount() {
		if(type == "2"){
		return amount * qty;
		} else {
			return amount;
		}
	}

	public String getOperation() {
		return operation;
	}

	abstract String getMessageAsString();
}
