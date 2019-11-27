#  書籍管理システム
  
図書館のように書籍をレンタルすることができるシステム
  
##  モデル
  
  
###  要件モデル
  
  

![](assets/03ba452885c3cf97674e8f6cc37f8c4f0.png?0.6071366656971957)  
  
###  クラス図
  
  

![](assets/03ba452885c3cf97674e8f6cc37f8c4f1.png?0.3836333090669357)  
  
###  状態遷移図
  
  

![](assets/03ba452885c3cf97674e8f6cc37f8c4f2.png?0.803704026035506)  
  
##  テストケース
  
  
###  書籍のテストケース
  
  
| 状態 | テストケース | 事後確認項目 |
|--|--|--|
|生成前| 書籍を生成できること | ・書籍IDが生成で指定した書籍IDであること <br> ・Isbn13が生成で指定したIsbn13であること <br> ・titleが生成で指定したtitleであること <br> ・statusがLendableであること <br> ・BorrowerIdが空であること |
|貸出可能状態| 貸出処理が可能であること | ・statusがInLendingであること <br> ・BorrowerIdが操作したユーザーのIDであること |
|貸出可能状態| 返却処理を実行した場合例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be InLending"であること |
|貸出中状態| 返却処理が可能であること | ・statusがLendableであること <br> ・BorrowerIdが空であること |
|貸出中状態| 貸出処理を実行した場合例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be Lendable"であること |
|貸出中状態| 貸出利用者以外が返却処理を実行した場合例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"borrowerId does not match"であること |
  