//import org.json.*;
import java.io.*;
import java.util.Date;
import java.util.Calendar;

public class Unsimplify_Integral {
  public static void main(String[] args) throws IOException {
    if( args.length < 2 ){
      System.err.println("Usage: java Unsimplify_Integral [Value offset] [time offset in unixtime]") ;
      System.exit(-1) ;
    }
    
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String inStr = "" ;

    System.out.print("[") ;
    double value = Double.parseDouble(args[0]) ;
    long unixtime = Long.parseLong(args[1]) ;
    String l;
    boolean bFirst = true ;
    while ((l = in.readLine()) != null && l.length() != 0){
      if( bFirst ){ bFirst = false ; }
      else { System.out.print(",") ; }

      value += Double.parseDouble(l) ;
      Date d = new Date(unixtime) ;
      unixtime += 30*60*1000 ;

      Calendar c = Calendar.getInstance() ;
      c.setTime(d) ;
      System.out.print("{\"measured_at\":\"") ;
      System.out.print(c.get(Calendar.YEAR)+"-"+(d2(c.get(Calendar.MONTH)+1))+"-"+d2(c.get(Calendar.DAY_OF_MONTH))+"T") ;
      System.out.print(d2(c.get(Calendar.HOUR_OF_DAY))+":"+d2(c.get(Calendar.MINUTE))+":"+d2(c.get(Calendar.SECOND))) ;
      System.out.print(".000+09:00\",\"missing\":false,\"value\":") ;
      System.out.print(value+"}") ;
    }
    System.out.print("]") ;
  }
  private static String d2(int n){
    return ( n<10 ? "0"+n : ""+n ) ;
  }
}
