String data[] = loadStrings("integ.txt");  

double input[] = new double[data.length+1];
input[0] = 0;
for ( int a=0; a<data.length; ++a ) {
  input[a+1] = Double.parseDouble(data[a].split(",")[1]) ;
}

double sum[] = new double[48];
for (int day = 6; day < 30; day+=7) {  
  for (int half = 0; half < 48; ++half)
    sum[half] += input[(input.length-48*(day+1)) + half];
}

String[] result = new String[48]; 
for ( int i=0; i<48; ++i ) {
 result[i] = ""+sum[i] / 4;        
}
saveStrings("result.txt", result);







// Added by Sony CSL
exit();
