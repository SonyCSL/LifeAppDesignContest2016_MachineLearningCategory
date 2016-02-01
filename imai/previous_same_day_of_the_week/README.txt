アルゴリズム: 7日前 (一番最近の同じ曜日) の変化を採用

実行方法

1. sbt をインストールする http://www.scala-sbt.org/
2. 以下をコンソールから実行 (linux/macの場合．Windowsは未確認ですが，同じようにできると思います)

$ cd {提出フォルダを解凍したディレクトリ}
$ ls
build.sbt  main.scala README.txt
// 結構時間がかかります．ライブラリをダウンロードするためネットワークが必須です．
$ sbt compile 
$ sbt --error 'set showSuccess := false' "run-main ForSubmit forward_integral.json instantauneous.json" > output.json
$ cat output.json
[{"measured_at":"2015-10-01T00:00+09:00","missing":false,"value":1249.43}....

