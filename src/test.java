import java.util.Vector;

class obj{
	int x = 0;
	int y = 0;
	
	obj(){
		
	}
	
	obj(int x){
		x = 3;
		this.x = 1;
		x = 4;
		this.y = 2;
	}
	
	obj(String x){
		this.x = 3;
		this.y = 4;
	}
	
	public void print() {
		System.out.println(x);
		System.out.println(y);
	}
}

public class test {
	
	public void print() {
		System.out.println("HI");
	}
	
	public static void main (String args[]){
		obj o = new obj(3);
		o.print();
	}
}
