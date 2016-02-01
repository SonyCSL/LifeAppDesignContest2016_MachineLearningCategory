
# プログラムの説明
実装した予測プログラムは predict.py になります．

## プログラム実行環境
実行環境は，python3になります．
いくつかのライブラリに依存していますが，python3のAnaconda(https://www.continuum.io/downloads)ならば動作するはずです．

## プログラム実行方法
predict.pyは，Simplify_Integralで出力したinteg.txtを読み込み，result.txtを出力します．

実行例は以下のようになります．
`
% java Simplify_Integral sample_forward_integral.json > integ.txt
% python predict.py
% cat result.txt | java Unsimplify_Integral 2147.59 1446303600000 > result.json
`
