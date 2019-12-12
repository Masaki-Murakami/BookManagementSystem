#  書籍管理システム
  
図書館のように書籍をレンタルすることができるシステム
  
##  アーキテクチャ
  
  
###  レイヤードアーキテクチャを採用している
  
* プレゼンテーションレイヤ  
認証・認可  
入力形式の確認、バリデーション  
* アプリケーションレイヤ・ドメインレイヤ  
アプリケーションのユースケースを実装  
業務ロジック  
* インフラストラクチャレイヤ  
データアクセスの抽象化、CRUD処理の提供  
  
##  テスト観点
  
  
###  プレゼンテーションレイヤ
  
* 認証機能のテスト
* バリデーション機能のテスト
  
###  アプリケーションレイヤ
  
* ユースケースの機能確認
  
###  ドメインレイヤ
  
* 業務ロジックの機能確認
  
###  インフラストラクチャレイヤ
  
* DBアクセスの確認
* 今回はJPAの確認になるため不要とする（諸説あります）
  
##  モデル
  
  
###  要件モデル
  
  

![](assets/a8417f8623ab540fd701e7efa67a37780.png?0.18532184414473285)  
  
###  クラス図
  
  

![](assets/a8417f8623ab540fd701e7efa67a37781.png?0.5726405272190389)  
  
###  状態遷移図
  
  

![](assets/a8417f8623ab540fd701e7efa67a37782.png?0.28266326881890924)  
  
##  テストケース
  
  
###  プレゼンテーションレイヤ
  
  
####  利用者登録のテストケース
  
* 管理者は利用者を登録できる
* 一般ユーザーが利用者を登録実施した場合、ステータスコード403が返却される
* ユーザー名が33文字以上の場合、ステータスコード400が返却される
* ユーザー名が空文字の場合、ステータスコード400が返却される
* パスワードが33文字以上の場合、ステータスコード400が返却される
* パスワードが空文字の場合、ステータスコード400が返却される
* ロールが「Administrator」「GeneralUser」以外の場合、ステータスコード400が返却される
* ロールが空文字の場合、ステータスコード400が返却される
  
####  書籍情報取得のテストケース
  
* 管理者は書籍情報を取得できる
* 一般利用者は書籍情報を取得できる
* bookIdが35桁以下の場合、ステータスコード400が返却される
* bookIdが37桁以上の場合、ステータスコード400が返却される
  
####  書籍登録のテストケース
  
* 管理者は書籍を登録できる
* 一般ユーザーが書籍を登録した場合、ステータスコード403が返却される
* isbn13が数字13桁の場合、登録できる
* isbn13がnullの場合、登録できる
* isbn13が空文字の場合、登録できる
* titleが32桁の場合、登録できる
* isbn13が数字以外の13桁の場合、ステータスコード400が返却される
* isbn13が数字の12桁以下の場合、ステータスコード400が返却される
* isbn13が数字の14桁以上の場合、ステータスコード400が返却される
* titleが33桁以上の場合、ステータスコード400が返却される
  
####  書籍貸出のテストケース
  
* 管理者は書籍貸出を実施できる
* 一般利用者は書籍貸出を実施できる
* bookIdが35桁以下の場合、ステータスコード400が返却される
* bookIdが37桁以上の場合、ステータスコード400が返却される
  
####  書籍返却のテストケース
  
* 管理者は書籍返却を実施できる
* 一般利用者は書籍返却を実施できる
* bookIdが35桁以下の場合、ステータスコード400が返却される
* bookIdが37桁以上の場合、ステータスコード400が返却される
  
###  アプリケーションレイヤ
  
####  利用者を登録のテストケース
  
* 正常に登録できる
* 同一のユーザー名が存在する場合、例外が発生する
  
####  書籍情報取得のテストケース
  
* 書籍情報を正常に取得できる
* 書籍が存在しない場合、Optinal.emptyが返却される
  
####  書籍登録のテストケース
  
* 正常に登録できる
  
####  書籍貸出のテストケース
  
* 正常に貸出できる
* 書籍が存在しない場合、例外が発生する
* 利用者が存在しない場合、例外が発生する
* 利用者が書籍を10冊以上借りていた場合、例外が発生する
  
####  書籍返却のテストケース
  
* 正常に返却できる
* 書籍が存在しない場合、例外が発生する
* 利用者が書籍のBollowerIdと一致しない場合、例外が発生する
  
###  ドメインレイヤ
  
####  書籍のテストケース
  
  
| 状態 | テストケース | 事後確認項目 |
|--|--|--|
|生成前| 書籍を生成できること | ・書籍IDが生成で指定した書籍IDであること <br> ・Isbn13が生成で指定したIsbn13であること <br> ・titleが生成で指定したtitleであること <br> ・statusがLendableであること <br> ・BorrowerIdが空であること |
|貸出可能状態| 貸出処理が可能であること | ・statusがInLendingであること <br> ・BorrowerIdが操作したユーザーのIDであること |
|貸出可能状態| 返却処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be InLending"であること |
|貸出中状態| 返却処理が可能であること | ・statusがLendableであること <br> ・BorrowerIdが空であること |
|貸出中状態| 貸出処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be Lendable"であること |
|貸出中状態| 貸出利用者以外が返却処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"borrowerId does not match"であること |
  