package SpringBatch;

public class Item {
	
	private String item_name;
	private String item_code;
	
	public Item() {
	}
	
	public Item(String item_name, String item_code) {

		this.item_name = item_name;
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	@Override
	public String toString() {
		return "Item [item_name=" + item_name + ", item_code=" + item_code + "]";
	}
	
	

}
