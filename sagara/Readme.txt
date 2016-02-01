
〜プログラム実行方法〜

Processingを用いて実装を行いました。
応募の手引(http://lifedesign-app.org/Howto.pdf)と同様の手順のところは、省略致します。

1. 応募の手引に記載されている前準備2を行い、過去一ヶ月のデータの累積値の差分を取ります。
   ファイル名はinteg.txtにします。

2. Processingより、ファイル名:Centervalue.pdeを使用します。入力ファイルはinteg.txtで
   あり、出力ファイルはResultCenter.txtとなります。

3. Centervalue.pdeのアルゴリズムとしては、一日の電力使用量は時間毎に特徴があると仮定
   します（例えば、昼の電力使用量は多く、夜の電力使用量は少ない）。 この仮定より、過去一ヶ月
   の30分ごとの電力使用量から中央値を導き出し、30分ごとの電力使用量を予測します。

4. 48行の出力ファイルResultcenter.txtをUnsimplifyを使ってIIJ形式に戻します。
