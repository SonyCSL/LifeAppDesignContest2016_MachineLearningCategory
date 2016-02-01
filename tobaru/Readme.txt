
説明資料

　プログラムを実行する方法は、 生活デザインアプリコンテスト応募の手引とほどんど同じです。 
まず、 応募の手引の前準備2と同じように、 Simplify_Integral.class を用いて、　
累積値の差分を取ります。 このプログラムを使って、 電力データのJSONファイルを処理し、 
integ.txt という名前のtxtファイルを作成します。
この integ.txt を、 入力として life_design_t.pde の最初の行に格納し、 実行すると、 
結果の予測データ（48サンプル）は、 result.txt に格納されます。
この result.txt を Unisimplify_Integral.class を用いて、 元のJSON形式に変換することができます。
