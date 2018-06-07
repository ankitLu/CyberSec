package preProcess;

public class Clean {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String data = "this is. a is,land";
		data = data.replaceAll("\\bis\\b", "");
		System.out.println(data);

	}

}
