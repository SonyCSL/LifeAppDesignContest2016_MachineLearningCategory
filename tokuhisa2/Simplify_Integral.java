import java.io.*;
import java.util.Date;
import java.util.Calendar;

public class Simplify_Integral {
  public static void main(String[] args) throws IOException {
    String wholeData = "" ;

    if( args.length > 0 ){
      for( int fi=0;fi<args.length-1;++fi ){
        String inStr = readFileAll(args[fi]) ;
        wholeData += inStr.substring( inStr.indexOf('[')+1,inStr.indexOf(']') ) +",";
      }
      {
        String inStr = readFileAll(args[args.length-1]) ;
        wholeData += inStr.substring( inStr.indexOf('[')+1,inStr.indexOf(']') ) ;
      }
      wholeData = "[" + wholeData + "]" ;
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(
      args.length==0 ? System.in : new ByteArrayInputStream(wholeData.getBytes())));

    String inStr = "" ;
    {
      String s;
      while ((s = in.readLine()) != null && s.length() != 0)
        inStr += s ;
    }
    
    String[] terms = inStr.split("[\"}{,]") ;
    String expecting = "measured_at" ;

    long unixTime = 0;
    double prevValue = -1 ;

    boolean bMissing = false ;
    for( int ti=0;ti<terms.length;++ti ){
      if( terms[ti].length()==0 ) continue ;
      if( !terms[ti].equals( expecting ) ) continue ;
      while( terms[++ti].length()==0 || terms[ti].equals(":")) ;
      if( terms[ti].charAt(0) == ':' ) terms[ti] = terms[ti].substring(1) ;

      if( expecting.equals("measured_at") ){
        String[] terms2 = terms[ti].split("[-T:+.]") ;
        Calendar c = Calendar.getInstance() ;
        c.setTimeInMillis(0);
        c.set( Integer.parseInt(terms2[0])
               , Integer.parseInt(terms2[1])-1
               , Integer.parseInt(terms2[2])
               , Integer.parseInt(terms2[3])
               , Integer.parseInt(terms2[4])
               , Integer.parseInt(terms2[5]) ) ;
        Date d = c.getTime() ;

        unixTime = d.getTime() ;
        expecting="missing" ;
      } else if( expecting.equals("missing") ){
        bMissing = terms[ti].equals("true") ;
        if( bMissing )
        System.err.println("Missing data found."+ti) ;
        expecting="value" ;
      }else if( expecting.equals("value") ){
        if( prevValue == -1 ){
          prevValue = Double.parseDouble(terms[ti]) ;
        } else {
          double value = Double.parseDouble(terms[ti]) ;
          if( bMissing ) value = prevValue ;
          System.out.println(unixTime+","+(value-prevValue)) ;
          prevValue = value ;
        }
        expecting="measured_at" ;
      }

    }
    System.err.println("Unsimplify : java Unsimplify_Integral "+prevValue+" "+(unixTime+30*60*1000)) ;
  }

  private static String readFileAll(String fname){
    String allStr = "" ;
    try{
      File file = new File(fname);
      BufferedReader br = new BufferedReader(new FileReader(file));

      String str;
      while( (str=br.readLine()) != null){
        allStr += str ;
      }
      br.close();
    }catch(FileNotFoundException e){
      return null ;
    }catch(IOException e){
      return null ;
    }
    return allStr ;
  }
}
