import java.util.*;
import java.io.*;

public class lab6 {
	public static void main(String[] args) throws IOException{ 
		Scanner in=new Scanner(System.in);
		ArrayList<knight> list = new ArrayList<knight>();
		int number=in.nextInt();
		int iteration =in.nextInt();
		int qx=in.nextInt();
		int qy=in.nextInt();
		////////////////////////////////////////////////////	
		//System.out.println("start");
		PrintWriter writer=new PrintWriter("./src/output.txt","UTF-8");
		String filename_pre="D:/Desktop/Lab 6/Test Case/Input/";
		BufferedReader br=null;
		FileReader fr=null;
		for(int j=1;j<number+1;j++){

			InputStream input = new FileInputStream(filename_pre + j + ".txt");
			in = new Scanner(input);
			//ye file vala kaam hai


			String naam =in.next();
			int X=in.nextInt();
			int Y=in.nextInt();
			int m=in.nextInt();
			//br.readLine();
			
			in.nextLine();

			Stack<String> tempstack=new Stack<>();
			for(int i=1;i<m+1;i++){
				String inputs=in.nextLine();
				//System.out.println(inputs);
				tempstack.push(inputs);
			}
			//yaha tak

			knight tempknight=new knight(naam,X,Y,m,tempstack);
			list.add(tempknight);
		}
		Collections.sort( list , new Comparator<knight>(){
			public int compare(knight o1, knight o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		boolean queen=false;
		for(int i=0;i<iteration;i++){
			for(int j=0;j<list.size();j++){
				knight one=list.get(j);
				Stack<String> box =one.magicbox;
				int ll=i+1;
				System.out.println(ll+" "+one.name+" "+one.x+" "+one.y);
				writer.println(ll+" "+one.name+" "+one.x+" "+one.y);
				try{
					if(box.size()!=0){
						String test=(String) box.pop();
						//System.out.println(test+" "+i+" ");
						//writer.println(test+" "+i+" ");
						String[] arr=test.split(" ");
						//System.out.println("chakjaij" + arr[0] + " " + arr[1]);
						if(arr[0].equals("Coordinate")){
							int xcoor=Integer.valueOf(arr[1]);
							int ycoor=Integer.valueOf(arr[2]);
							one.x=xcoor;
							one.y=ycoor;
							if(xcoor==qx && ycoor==qy){
								queen=true;
								throw	new	QueenFoundException("QueenFoundException: Queen has been Found. Abort!");


							}
							else{
								int flag=move(list,xcoor,ycoor,one.name);
								if(flag!=-100){
									knight two=list.get(flag);
									String lol=two.name;
									list.remove(list.get(flag));
									//knight overlap exception
									//System.out.println(two.name);
									throw	new	OverlapException("OverlapException: Knights Overlap Exception", lol);
								}
								else{
									//no exception
									System.out.println("no exception"+" "+xcoor+" "+ycoor);
									writer.println("no exception"+" "+xcoor+" "+ycoor);


								} 
							}


						}
						else{
							//no coordinate  exception
							throw	new	NonCoordinateException("NonCoordinateException: Not a coordinate Exception", arr[1]);

						}
					}
					else{
						//empty stack
						throw	new	StackEmptyException("StackEmptyException: Stack Empty exception");

					}

				}
				catch(NonCoordinateException e){
					System.out.println(e.mymessage());
					writer.println(e.mymessage());

				}
				catch(StackEmptyException e){
					System.out.println(e.getMessage());	
					writer.println(e.getMessage());


				}
				catch(OverlapException e){
					System.out.println(e.mymessage());	
					writer.println(e.mymessage());


				}
				catch(QueenFoundException e){
					System.out.println(e.getMessage());
					writer.println(e.getMessage());

					if(queen==true){
						break;
					}
				}
			}

			if(queen==true){
				break;
			}

		}
		//////////////////////////////////////////////////////

		writer.close();







	}
	public static int move(ArrayList<knight> list,int xcor,int ycor,String naam){
		int flag=-100;
		for(int i=0;i<list.size();i++){
			if(xcor==list.get(i).x && ycor==list.get(i).y){
				if(naam.equals(list.get(i).name)==false){
					return i;
				}
			}
		}
		return flag;

	}

}

class knight{
	String name;
	int x;
	int y;
	int size;
	Stack magicbox;
	knight(String n,int a,int b,int s,Stack m ){
		name=n;
		x=a;
		y=b;
		size=s;
		magicbox=m; 
	}
}

class NonCoordinateException extends Exception{  

	String s;
	String b;
	NonCoordinateException(String s1, String s2){  
		super(s1); 
		s=s1;
		b=s2;
	} 

	public String mymessage(){
		return(s+" "+b);
	}
}

class StackEmptyException extends Exception{  
	StackEmptyException(String s){  
		super(s);  
	}  
}

class OverlapException extends Exception{
	String s;
	String b;
	OverlapException(String s1, String s2){  
		super(s1);
		s=s1;
		b=s2;
	}
	public String mymessage(){
		return(s+" "+b);
	}
}

class QueenFoundException extends Exception{  
	QueenFoundException(String s){  
		super(s);  
	}  
}

