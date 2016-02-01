
String in_lines[] = loadStrings("integ.txt");

double out_lines[] = new double[48];
double out_nums48[] = new double[48];

double in_nums[]= new double[in_lines.length+1];

for( int i=0; i<in_lines.length; ++i){
in_nums[i]= Double.parseDouble(in_lines[i].split(",")[1]);
}

in_nums[in_lines.length]=0;

double in_nums48[]=new double[(in_nums.length)/48];


for( int j=0,s=0; j<48; ++j ,s=s+(in_nums.length)/48){

for( int i=0; i<in_nums48.length; ++i){
in_nums48[i]= in_nums[i+s];
}
for( int k=0; k<in_nums48.length; ++k){
out_nums48[j]+= in_nums48[k];
}

}

for( int k=0; k<47; ++k){
out_lines[k]= out_nums48[k]/in_nums48.length;
}

out_lines[47]=out_nums48[47]/(in_nums48.length-1);

String out_lines2[]=new String[48];

for(int i=0;i<48;++i){
out_lines2[i]=""+out_lines[i];
}





saveStrings("ResultAve.txt",out_lines2);







// Added by Sony CSL
exit();
