

String in_lines[] = loadStrings("integ.txt");

double out_lines[] = new double[49];

double in_nums[]=new double[in_lines.length];

for( int i=1; i<in_lines.length; ++i){
in_nums[i]= Double.parseDouble(in_lines[i].split(",")[1]);
}

double out1_lines[]=new double[31];
double out2_lines[]=new double[31];
double out3_lines[]=new double[31];
double out4_lines[]=new double[31];
double out5_lines[]=new double[31];
double out6_lines[]=new double[31];
double out7_lines[]=new double[31];
double out8_lines[]=new double[31];
double out9_lines[]=new double[31];
double out10_lines[]=new double[31];
double out11_lines[]=new double[31];
double out12_lines[]=new double[31];
double out13_lines[]=new double[31];
double out14_lines[]=new double[31];
double out15_lines[]=new double[31];
double out16_lines[]=new double[31];
double out17_lines[]=new double[31];
double out18_lines[]=new double[31];
double out19_lines[]=new double[31];
double out20_lines[]=new double[31];
double out21_lines[]=new double[31];
double out22_lines[]=new double[31];
double out23_lines[]=new double[31];
double out24_lines[]=new double[31];
double out25_lines[]=new double[31];
double out26_lines[]=new double[31];
double out27_lines[]=new double[31];
double out28_lines[]=new double[31];
double out29_lines[]=new double[31];
double out30_lines[]=new double[31];
double out31_lines[]=new double[31];
double out32_lines[]=new double[31];
double out33_lines[]=new double[31];
double out34_lines[]=new double[31];
double out35_lines[]=new double[31];
double out36_lines[]=new double[31];
double out37_lines[]=new double[31];
double out38_lines[]=new double[31];
double out39_lines[]=new double[31];
double out40_lines[]=new double[31];
double out41_lines[]=new double[31];
double out42_lines[]=new double[31];
double out43_lines[]=new double[31];
double out44_lines[]=new double[31];
double out45_lines[]=new double[31];
double out46_lines[]=new double[31];
double out47_lines[]=new double[31];
double out48_lines[]=new double[31];

double center[]=new double[49];



for (int i=1,d=0 ; i<1487; i=i+48,++d){
out1_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out1_lines.length-1; jn++ ){
            double max = out1_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out1_lines.length; i++ ) {
                if( out1_lines[i] > max ){
                     max = out1_lines[i];
                     sonoi = i;
                }
            }
            out1_lines[sonoi] = out1_lines[jn];
            out1_lines[jn] = max;
        }
      center[1]= out1_lines[15]; 
for (int i=2,d=0; i<1487; i=i+48,++d){
out2_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out2_lines.length-1; jn++ ){
            double max = out2_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out2_lines.length; i++ ) {
                if( out2_lines[i] > max ){
                     max = out2_lines[i];
                     sonoi = i;
                }
            }
            out2_lines[sonoi] = out2_lines[jn];
            out2_lines[jn] = max;
        }
      center[2]= out2_lines[15];         
for (int i=3,d=0; i<1487; i=i+48,++d){
out3_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out3_lines.length-1; jn++ ){
            double max = out3_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out3_lines.length; i++ ) {
                if( out3_lines[i] > max ){
                     max = out3_lines[i];
                     sonoi = i;
                }
            }
            out3_lines[sonoi] = out3_lines[jn];
            out3_lines[jn] = max;
        }
       center[3]= out3_lines[15];        
for (int i=4,d=0; i<1487; i=i+48,++d){
out4_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out4_lines.length-1; jn++ ){
            double max = out4_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out4_lines.length; i++ ) {
                if( out4_lines[i] > max ){
                     max = out4_lines[i];
                     sonoi = i;
                }
            }
            out4_lines[sonoi] = out4_lines[jn];
            out4_lines[jn] = max;
        }
      center[4]= out4_lines[15];         
for (int i=5,d=0; i<1487; i=i+48,++d){
out5_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out5_lines.length-1; jn++ ){
            double max = out5_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out5_lines.length; i++ ) {
                if( out5_lines[i] > max ){
                     max = out5_lines[i];
                     sonoi = i;
                }
            }
            out5_lines[sonoi] = out5_lines[jn];
            out5_lines[jn] = max;
        }
      center[5]= out5_lines[15];         
for (int i=6,d=0; i<1487; i=i+48,++d){
out6_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out6_lines.length-1; jn++ ){
            double max = out6_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out6_lines.length; i++ ) {
                if( out6_lines[i] > max ){
                     max = out6_lines[i];
                     sonoi = i;
                }
            }
            out6_lines[sonoi] = out6_lines[jn];
            out6_lines[jn] = max;
        }
      center[6]= out6_lines[15];         
for (int i=7,d=0; i<1487; i=i+48,++d){
out7_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out7_lines.length-1; jn++ ){
            double max = out7_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out7_lines.length; i++ ) {
                if( out7_lines[i] > max ){
                     max = out7_lines[i];
                     sonoi = i;
                }
            }
            out7_lines[sonoi] = out7_lines[jn];
            out7_lines[jn] = max;
        }
      center[7]= out7_lines[15];         
for (int i=8,d=0; i<1487; i=i+48,++d){
out8_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out8_lines.length-1; jn++ ){
            double max = out8_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out8_lines.length; i++ ) {
                if( out8_lines[i] > max ){
                     max = out8_lines[i];
                     sonoi = i;
                }
            }
            out8_lines[sonoi] = out8_lines[jn];
            out8_lines[jn] = max;
        }
      center[8]= out8_lines[15];         
for (int i=9,d=0; i<1487; i=i+48,++d){
out9_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out9_lines.length-1; jn++ ){
            double max = out9_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out9_lines.length; i++ ) {
                if( out9_lines[i] > max ){
                     max = out9_lines[i];
                     sonoi = i;
                }
            }
            out9_lines[sonoi] = out9_lines[jn];
            out9_lines[jn] = max;
        }
       center[9]= out9_lines[15];        
for (int i=10,d=0; i<1487; i=i+48,++d){
out10_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out10_lines.length-1; jn++ ){
            double max = out10_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out10_lines.length; i++ ) {
                if( out10_lines[i] > max ){
                     max = out10_lines[i];
                     sonoi = i;
                }
            }
            out10_lines[sonoi] = out10_lines[jn];
            out10_lines[jn] = max;
        }
       center[10]= out10_lines[15];        
for (int i=11,d=0; i<1487; i=i+48,++d){
out11_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out11_lines.length-1; jn++ ){
            double max = out11_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out11_lines.length; i++ ) {
                if( out11_lines[i] > max ){
                     max = out11_lines[i];
                     sonoi = i;
                }
            }
            out11_lines[sonoi] = out11_lines[jn];
            out11_lines[jn] = max;
        }
       center[11]= out11_lines[15];        
for (int i=12,d=0; i<1487; i=i+48,++d){
out12_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out12_lines.length-1; jn++ ){
            double max = out12_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out12_lines.length; i++ ) {
                if( out12_lines[i] > max ){
                     max = out12_lines[i];
                     sonoi = i;
                }
            }
            out12_lines[sonoi] = out12_lines[jn];
            out12_lines[jn] = max;
        }
       center[12]= out12_lines[15];        
for (int i=13,d=0; i<1487; i=i+48,++d){
out13_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out13_lines.length-1; jn++ ){
            double max = out13_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out13_lines.length; i++ ) {
                if( out13_lines[i] > max ){
                     max = out13_lines[i];
                     sonoi = i;
                }
            }
            out13_lines[sonoi] = out13_lines[jn];
            out13_lines[jn] = max;
        }
       center[13]= out13_lines[15];        
for (int i=14,d=0; i<1487; i=i+48,++d){
out14_lines[d]=in_nums[i];
}
    for( int jn = 0; jn<out14_lines.length-1; jn++ ){
            double max = out14_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out14_lines.length; i++ ) {
                if( out14_lines[i] > max ){
                     max = out14_lines[i];
                     sonoi = i;
                }
            }
            out14_lines[sonoi] = out14_lines[jn];
            out14_lines[jn] = max;
        }
      center[14]= out14_lines[15];         
for (int i=15,d=0; i<1487; i=i+48,++d){
out15_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out15_lines.length-1; jn++ ){
            double max = out15_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out15_lines.length; i++ ) {
                if( out15_lines[i] > max ){
                     max = out15_lines[i];
                     sonoi = i;
                }
            }
            out15_lines[sonoi] = out15_lines[jn];
            out15_lines[jn] = max;
        }
       center[15]= out15_lines[15];        
for (int i=16,d=0; i<1487; i=i+48,++d){
out16_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out16_lines.length-1; jn++ ){
            double max = out16_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out16_lines.length; i++ ) {
                if( out16_lines[i] > max ){
                     max = out16_lines[i];
                     sonoi = i;
                }
            }
            out16_lines[sonoi] = out16_lines[jn];
            out16_lines[jn] = max;
        }
       center[16]= out16_lines[15];        
for (int i=17,d=0; i<1487; i=i+48,++d){
out17_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out17_lines.length-1; jn++ ){
            double max = out17_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out17_lines.length; i++ ) {
                if( out17_lines[i] > max ){
                     max = out17_lines[i];
                     sonoi = i;
                }
            }
            out17_lines[sonoi] = out17_lines[jn];
            out17_lines[jn] = max;
        }
        center[17]= out17_lines[15];       
for (int i=18,d=0; i<1487; i=i+48,++d){
out18_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out18_lines.length-1; jn++ ){
            double max = out18_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out18_lines.length; i++ ) {
                if( out18_lines[i] > max ){
                     max = out18_lines[i];
                     sonoi = i;
                }
            }
            out18_lines[sonoi] = out18_lines[jn];
            out18_lines[jn] = max;
        }
       center[18]= out18_lines[15];        
for (int i=19,d=0; i<1487; i=i+48,++d){
out19_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out19_lines.length-1; jn++ ){
            double max = out19_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out19_lines.length; i++ ) {
                if( out19_lines[i] > max ){
                     max = out19_lines[i];
                     sonoi = i;
                }
            }
            out19_lines[sonoi] = out19_lines[jn];
            out19_lines[jn] = max;
        }
       center[19]= out19_lines[15];        
for (int i=20,d=0; i<1487; i=i+48,++d){
out20_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out20_lines.length-1; jn++ ){
            double max = out20_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out20_lines.length; i++ ) {
                if( out20_lines[i] > max ){
                     max = out20_lines[i];
                     sonoi = i;
                }
            }
            out20_lines[sonoi] = out20_lines[jn];
            out20_lines[jn] = max;
        }
       center[20]= out20_lines[15];        
for (int i=21,d=0; i<1487; i=i+48,++d){
out21_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out21_lines.length-1; jn++ ){
            double max = out21_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out21_lines.length; i++ ) {
                if( out21_lines[i] > max ){
                     max = out21_lines[i];
                     sonoi = i;
                }
            }
            out21_lines[sonoi] = out21_lines[jn];
            out21_lines[jn] = max;
        }
        center[21]= out21_lines[15];       
for (int i=22,d=0; i<1487; i=i+48,++d){
out22_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out22_lines.length-1; jn++ ){
            double max = out22_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out22_lines.length; i++ ) {
                if( out22_lines[i] > max ){
                     max = out22_lines[i];
                     sonoi = i;
                }
            }
            out22_lines[sonoi] = out22_lines[jn];
            out22_lines[jn] = max;
        }
        center[22]= out22_lines[15];       
for (int i=23,d=0; i<1487; i=i+48,++d){
out23_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out23_lines.length-1; jn++ ){
            double max = out23_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out23_lines.length; i++ ) {
                if( out23_lines[i] > max ){
                     max = out23_lines[i];
                     sonoi = i;
                }
            }
            out23_lines[sonoi] = out23_lines[jn];
            out23_lines[jn] = max;
        }
       center[23]= out23_lines[15];          
for (int i=24,d=0; i<1487; i=i+48,++d){
out24_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out24_lines.length-1; jn++ ){
            double max = out24_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out24_lines.length; i++ ) {
                if( out24_lines[i] > max ){
                     max = out24_lines[i];
                     sonoi = i;
                }
            }
            out24_lines[sonoi] = out24_lines[jn];
            out24_lines[jn] = max;
        }
       center[24]= out24_lines[15];         
for (int i=25,d=0; i<1487; i=i+48,++d){
out25_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out25_lines.length-1; jn++ ){
            double max = out25_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out25_lines.length; i++ ) {
                if( out25_lines[i] > max ){
                     max = out25_lines[i];
                     sonoi = i;
                }
            }
            out25_lines[sonoi] = out25_lines[jn];
            out25_lines[jn] = max;
        }
       center[25]= out25_lines[15];         
for (int i=26,d=0; i<1487; i=i+48,++d){
out26_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out26_lines.length-1; jn++ ){
            double max = out26_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out26_lines.length; i++ ) {
                if( out26_lines[i] > max ){
                     max = out26_lines[i];
                     sonoi = i;
                }
            }
            out26_lines[sonoi] = out26_lines[jn];
            out26_lines[jn] = max;
        }
       center[26]= out26_lines[15];         
for (int i=27,d=0; i<1487; i=i+48,++d){
out27_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out27_lines.length-1; jn++ ){
            double max = out27_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out27_lines.length; i++ ) {
                if( out27_lines[i] > max ){
                     max = out27_lines[i];
                     sonoi = i;
                }
            }
            out27_lines[sonoi] = out27_lines[jn];
            out27_lines[jn] = max;
        }
       center[27]= out27_lines[15];         
for (int i=28,d=0; i<1487; i=i+48,++d){
out28_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out28_lines.length-1; jn++ ){
            double max = out28_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out28_lines.length; i++ ) {
                if( out28_lines[i] > max ){
                     max = out28_lines[i];
                     sonoi = i;
                }
            }
            out28_lines[sonoi] = out28_lines[jn];
            out28_lines[jn] = max;
        }
       center[28]= out28_lines[15];         
for (int i=29,d=0; i<1487; i=i+48,++d){
out29_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out29_lines.length-1; jn++ ){
            double max = out29_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out29_lines.length; i++ ) {
                if( out29_lines[i] > max ){
                     max = out29_lines[i];
                     sonoi = i;
                }
            }
            out29_lines[sonoi] = out29_lines[jn];
            out29_lines[jn] = max;
        }
        center[29]= out29_lines[15];        
for (int i=30,d=0; i<1487; i=i+48,++d){
out30_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out30_lines.length-1; jn++ ){
            double max = out30_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out30_lines.length; i++ ) {
                if( out30_lines[i] > max ){
                     max = out30_lines[i];
                     sonoi = i;
                }
            }
            out30_lines[sonoi] = out30_lines[jn];
            out30_lines[jn] = max;
        }
       center[30]= out30_lines[15];         
for (int i=31,d=0; i<1487; i=i+48,++d){
out31_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out31_lines.length-1; jn++ ){
            double max = out31_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out31_lines.length; i++ ) {
                if( out31_lines[i] > max ){
                     max = out31_lines[i];
                     sonoi = i;
                }
            }
            out31_lines[sonoi] = out31_lines[jn];
            out31_lines[jn] = max;
        }
       center[31]= out31_lines[15];         
for (int i=32,d=0; i<1487; i=i+48,++d){
out32_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out32_lines.length-1; jn++ ){
            double max = out32_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out32_lines.length; i++ ) {
                if( out32_lines[i] > max ){
                     max = out32_lines[i];
                     sonoi = i;
                }
            }
            out32_lines[sonoi] = out32_lines[jn];
            out32_lines[jn] = max;
        }
       center[32]= out32_lines[15];         
for (int i=33,d=0; i<1487; i=i+48,++d){
out33_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out33_lines.length-1; jn++ ){
            double max = out33_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out33_lines.length; i++ ) {
                if( out33_lines[i] > max ){
                     max = out33_lines[i];
                     sonoi = i;
                }
            }
            out33_lines[sonoi] = out33_lines[jn];
            out33_lines[jn] = max;
        }
        center[33]= out33_lines[15];        
for (int i=34,d=0; i<1487; i=i+48,++d){
out34_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out34_lines.length-1; jn++ ){
            double max = out34_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out34_lines.length; i++ ) {
                if( out34_lines[i] > max ){
                     max = out34_lines[i];
                     sonoi = i;
                }
            }
            out34_lines[sonoi] = out34_lines[jn];
            out34_lines[jn] = max;
        }
       center[34]= out34_lines[15];         
for (int i=35,d=0; i<1487; i=i+48,++d){
out35_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out35_lines.length-1; jn++ ){
            double max = out35_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out35_lines.length; i++ ) {
                if( out35_lines[i] > max ){
                     max = out35_lines[i];
                     sonoi = i;
                }
            }
            out35_lines[sonoi] = out35_lines[jn];
            out35_lines[jn] = max;
        }
       center[35]= out35_lines[15];         
for (int i=36,d=0; i<1487; i=i+48,++d){
out36_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out36_lines.length-1; jn++ ){
            double max = out36_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out36_lines.length; i++ ) {
                if( out36_lines[i] > max ){
                     max = out36_lines[i];
                     sonoi = i;
                }
            }
            out36_lines[sonoi] = out36_lines[jn];
            out36_lines[jn] = max;
        }
       center[36]= out36_lines[15];         
for (int i=37,d=0; i<1487; i=i+48,++d){
out37_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out37_lines.length-1; jn++ ){
            double max = out37_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out37_lines.length; i++ ) {
                if( out37_lines[i] > max ){
                     max = out37_lines[i];
                     sonoi = i;
                }
            }
            out37_lines[sonoi] = out37_lines[jn];
            out37_lines[jn] = max;
        }
        center[37]= out37_lines[15];        
for (int i=38,d=0; i<1487; i=i+48,++d){
out38_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out38_lines.length-1; jn++ ){
            double max = out38_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out38_lines.length; i++ ) {
                if( out38_lines[i] > max ){
                     max = out38_lines[i];
                     sonoi = i;
                }
            }
            out38_lines[sonoi] = out38_lines[jn];
            out38_lines[jn] = max;
        }
       center[38]= out38_lines[15];         
for (int i=39,d=0; i<1487; i=i+48,++d){
out39_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out39_lines.length-1; jn++ ){
            double max = out39_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out39_lines.length; i++ ) {
                if( out39_lines[i] > max ){
                     max = out39_lines[i];
                     sonoi = i;
                }
            }
            out39_lines[sonoi] = out39_lines[jn];
            out39_lines[jn] = max;
        }
       center[39]= out39_lines[15];         
for (int i=40,d=0; i<1487; i=i+48,++d){
out40_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out40_lines.length-1; jn++ ){
            double max = out40_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out40_lines.length; i++ ) {
                if( out40_lines[i] > max ){
                     max = out40_lines[i];
                     sonoi = i;
                }
            }
            out40_lines[sonoi] = out40_lines[jn];
            out40_lines[jn] = max;
        }
       center[40]= out40_lines[15];         
for (int i=41,d=0; i<1487; i=i+48,++d){
out41_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out41_lines.length-1; jn++ ){
            double max = out41_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out41_lines.length; i++ ) {
                if( out41_lines[i] > max ){
                     max = out41_lines[i];
                     sonoi = i;
                }
            }
            out41_lines[sonoi] = out41_lines[jn];
            out41_lines[jn] = max;
        }
       center[41]= out41_lines[15];         
for (int i=42,d=0; i<1487; i=i+48,++d){
out42_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out42_lines.length-1; jn++ ){
            double max = out42_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out42_lines.length; i++ ) {
                if( out42_lines[i] > max ){
                     max = out42_lines[i];
                     sonoi = i;
                }
            }
            out42_lines[sonoi] = out42_lines[jn];
            out42_lines[jn] = max;
        }
       center[42]= out42_lines[15];         
for (int i=43,d=0; i<1487; i=i+48,++d){
out43_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out43_lines.length-1; jn++ ){
            double max = out43_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out43_lines.length; i++ ) {
                if( out43_lines[i] > max ){
                     max = out43_lines[i];
                     sonoi = i;
                }
            }
            out43_lines[sonoi] = out43_lines[jn];
            out43_lines[jn] = max;
        }
       center[43]= out43_lines[15];         
for (int i=44,d=0; i<1487; i=i+48,++d){
out44_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out44_lines.length-1; jn++ ){
            double max = out44_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out44_lines.length; i++ ) {
                if( out44_lines[i] > max ){
                     max = out44_lines[i];
                     sonoi = i;
                }
            }
            out44_lines[sonoi] = out44_lines[jn];
            out44_lines[jn] = max;
        }
       center[44]= out44_lines[15];         
for (int i=45,d=0; i<1487; i=i+48,++d){
out45_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out45_lines.length-1; jn++ ){
            double max = out45_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out45_lines.length; i++ ) {
                if( out45_lines[i] > max ){
                     max = out45_lines[i];
                     sonoi = i;
                }
            }
            out45_lines[sonoi] = out45_lines[jn];
            out45_lines[jn] = max;
        }
       center[45]= out45_lines[15];         
for (int i=46,d=0; i<1487; i=i+48,++d){
out46_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out46_lines.length-1; jn++ ){
            double max = out46_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out46_lines.length; i++ ) {
                if( out46_lines[i] > max ){
                     max = out46_lines[i];
                     sonoi = i;
                }
            }
            out46_lines[sonoi] = out46_lines[jn];
            out46_lines[jn] = max;
        }
       center[46]= out46_lines[15];         
for (int i=47,d=0; i<1487; i=i+48,++d){
out47_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out47_lines.length-1; jn++ ){
            double max = out47_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out47_lines.length; i++ ) {
                if( out47_lines[i] > max ){
                     max = out47_lines[i];
                     sonoi = i;
                }
            }
            out47_lines[sonoi] = out47_lines[jn];
            out47_lines[jn] = max;
        }
       center[47]= out47_lines[15];         
for (int i=48,d=0; i<1487; i=i+48,++d){
out48_lines[d]=in_nums[i];
}
        for( int jn = 0; jn<out48_lines.length-1; jn++ ){
            double max = out48_lines[jn];
            int sonoi = jn;
            for( int i=jn+1; i<out48_lines.length; i++ ) {
                if( out48_lines[i] > max ){
                     max = out48_lines[i];
                     sonoi = i;
                }
            }
            out48_lines[sonoi] = out48_lines[jn];
            out48_lines[jn] = max;
        }
               center[48]= (out48_lines[15]+out48_lines[15])/2; 

String out_lines2[]=new String[48];

for(int i=0;i<48;++i){
out_lines2[i]=""+center[i+1];
}

saveStrings("Resultcenter.txt",out_lines2);







// Added by Sony CSL
exit();
