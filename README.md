## なにこれ？  
Discordサーバーで使用できる、一般ユーザーが個人のVCを作成できるBotプログラムです。  

## 必要なもの  
Java 17 以上  

## インストール  
このリポジトリをクローンしてApache Mavenでビルトでしてください。  
例:
```bash
yuu@samplePC:~$ mvn install
yuu@samplePC:~$ java -jar target/hogehoge~~~.jar
```

## 設定方法  
DiscordのBotプログラムと同じ階層に`config.txt`を作成し、`token=ここにDiscordBotトークン`を入力してください。  
例: config.txt
```
token=ABCDEXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

## 使い方  
サーバーにBotを招待したら`/setup-private-vc`を入力してサーバーにプライベート用のカテゴリを作成して下さい。  

↑でちゃんとカテゴリが作れてたら`/private-vc`でプライベートのVCが多分作成できます。  
エラーはいたらどんまいです。  

作成が正常にできたら、Botからメッセージが送信され、招待リンクが送られます。  
VCに関しては誰もいなくなって1分くらいで削除されます。  
（1分周期で全チャンネルを監視してるのでタイミングによっては1分よりもめちゃくちゃ短いときがあります。許してください。気が向いたら直します。）  

活用してくれたらうれしいです  
