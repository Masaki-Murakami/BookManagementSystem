#  書籍管理システム
  
図書館のように書籍をレンタルすることができるシステム
  
##  実行方法
  
* [Eclipse での起動方法](docs/eclipse.md )
* [Intellijでの起動方法](docs/intellij.md )
* [SwaggerUIを使用した実行方法](docs/swaggerui.md )
  
##  アーキテクチャ
  
###  レイヤードアーキテクチャを採用している
  
レイヤードアーキテクチャの各レイヤの責務は大きく下記のようになる
* プレゼンテーションレイヤ  
認証・認可  
入力形式の確認、バリデーション  
* アプリケーションレイヤ・ドメインレイヤ  
アプリケーションのユースケースを実装  
業務ロジック  
* インフラストラクチャレイヤ  
データアクセスの抽象化、CRUD処理の提供  
  
  
##  モデル
  
  
###  要件モデル
  
  

![](assets/ba73b9370227353c4ad14de18a3794a50.png?0.28329176354889474){}  
  
###  クラス図
  
  

![](assets/ba73b9370227353c4ad14de18a3794a51.png?0.41425606668691084){}  
  
###  状態遷移図
  
  

![](assets/ba73b9370227353c4ad14de18a3794a52.png?0.2099334399078454){}  
  
  
##  テスト観点
  
###  ドメインレイヤ
  
* 業務ロジックの機能確認
  
  
###  アプリケーションレイヤ
  
* ユースケースの機能確認
  
###  プレゼンテーションレイヤ
  
* 認証機能のテスト
* バリデーション機能のテスト
  
###  インフラストラクチャレイヤ
  
* DBアクセスの確認
* 今回はJPAの確認になるため不要とする（諸説あります）
  
##  テストケース
  
  
###  ドメインレイヤ
  
####  書籍のテストケース(実装済み)
  
テスト対象は`jp.co.saison.training.bookmanagement.domain.model.bookaggregate.Book`  
  
| 状態 | テストケース | 事後確認項目 |
|--|--|--|
| 生成前 | 書籍を生成できること | ・書籍IDが生成で指定した書籍IDであること <br> ・Isbn13が生成で指定したIsbn13であること <br> ・titleが生成で指定したtitleであること <br> ・statusがLendableであること <br> ・BorrowerIdが空であること |
| 貸出可能状態 | 貸出処理が可能であること | ・statusがInLendingであること <br> ・BorrowerIdが操作したユーザーのIDであること |
| 貸出可能状態 | 返却処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be InLending"であること |
| 貸出中状態 | 返却処理が可能であること | ・statusがLendableであること <br> ・BorrowerIdが空であること |
| 貸出中状態 | 貸出処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"bookStatus must be Lendable"であること |
| 貸出中状態 | 貸出利用者以外が返却処理を実行した場合、例外が発生すること | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"borrowerId does not match"であること |
  
###  アプリケーションレイヤ
  
####  利用者を登録のテストケース(実装済み)
  
テスト対象は`jp.co.saison.training.bookmanagement.application.usecases.createuser.CreateUserUsecaseInteractor`
  
| テストケース | 事後確認項目 |
|--|--|
| 利用者を登録できる | ・UserRepository.createが想定のUserを引数に呼び出されていること |
| 同一のユーザー名が存在する場合、例外が発生する | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"name already exists"であること <br> ・UserRepository.createが呼び出されていないこと |
  
####  書籍情報取得のテストケース(実装済み)
  
テスト対象は`jp.co.saison.training.bookmanagement.application.usecases.findbook.FindBookUsecaseInteractor`
  
| テストケース | 事後確認項目 |
|--|--|
| 書籍情報を正常に取得できる | ・戻り値が想定した戻り値であること <br> ・BookRepository.findByIdが想定した引数で呼ばれていること |
| 書籍が存在しない場合、Optinal.emptyが返却される | ・戻り値がOptinal.emptyであること <br> ・BookRepository.findByIdが想定した引数で呼ばれていること |
  
####  書籍登録のテストケース
  
テスト対象は`jp.co.saison.training.bookmanagement.application.usecases.createbook.CreateBookUsecaseInteractor`  
  
| テストケース | 事後確認項目 |
|--|--|
| 書籍を登録できる | ・BookRepository.createが想定した引数で呼ばれていること |
  
####  書籍貸出のテストケース
  
テスト対象は`jp.co.saison.training.bookmanagement.application.usecases.borrowbook.BorrowBookUsecaseInteractor`  
  
| テストケース | 事後確認項目 |
|--|--|
| 書籍を貸出できる | ・BookRepository.updateが想定したBorrowerId、StatusがInLendingに設定された引数で呼ばれていること |
| 書籍が存在しない場合、例外が発生する | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"book not found"であること <br> ・BookRepository.updateが呼び出されていないこと |
| 利用者が存在しない場合、例外が発生する | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"borrower not found"であること <br> ・BookRepository.updateが呼び出されていないこと |
| 利用者が書籍を5冊以上借りていた場合、例外が発生する | ・IllegalStateExceptionが発生すること <br> ・例外のメッセージが"borrowed books must be up to 5"であること <br> ・BookRepository.updateが呼び出されていないこと |
  
####  書籍返却のテストケース
  
テスト対象は`jp.co.saison.training.bookmanagement.application.usecases.borrowbook.GiveBackBookUsecaseInteractor`  
  
| テストケース | 事後確認項目 |
|--|--|
| 書籍を返却できる | ・BookRepository.updateがBorrowerIdがnull、StatusがLendableに設定された引数で呼ばれていること |
| 書籍が存在しない場合、例外が発生する | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"book not found"であること <br> BookRepository.updateが呼び出されていないこと |
| 利用者が書籍のBollowerIdと一致しない場合例外が発生する | ・IllegalArgumentExceptionが発生すること <br> ・例外のメッセージが"borrowerId does not match"であること <br> ・BookRepository.updateが呼び出されていないこと |
  
###  プレゼンテーションレイヤ
  
  
####  UserController
  
テスト対象は`jp.co.saison.training.bookmanagement.presentation.controller.UserController`  
#####  利用者登録のテストケース(実装済み)
  
| テストケース | 事後確認項目 |
|--|--|
| 管理者は利用者を登録APIを呼び出しできる | ・ステータスコードが200であること <br> ・レスポンスに登録した利用者を示すJSONが入っていること <br> ・ createUserUsecaseInteractorを適切な入力値を設定し、実行していること |
| 一般ユーザーが利用者を登録実施した場合、ステータスコード403が返却される | ・レスポンスのステータスコードが403であること |
| ユーザー名が32文字以下の場合、正常登録できる | ・ステータスコードが200であること <br> ・createUserUsecaseInteractorを呼び出していること |
| パスワードが32文字以下の場合、正常登録できる | ・ステータスコードが200であること <br> ・createUserUsecaseInteractorを呼び出していること |
| ロールが「Administrator」の場合、正常登録できる | ・ステータスコードが200であること <br> ・createUserUsecaseInteractorを呼び出していること |
| ロールが「GeneralUser」の場合、正常登録できる | ・ステータスコードが200であること <br> ・createUserUsecaseInteractorを呼び出していること |
| ユーザー名が33文字以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
| ユーザー名が空文字の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
| パスワードが33文字以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること |
| パスワードが空文字の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
| ロールが「Administrator」「GeneralUser」以外の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
| ロールが「Administrator」「GeneralUser」以外の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
| ロールが空文字の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・createUserUsecaseInteractorを呼び出されていないこと |
  
####  BookController
  
テスト対象は`jp.co.saison.training.bookmanagement.presentation.controller.BookController`  
#####  書籍情報取得のテストケース(実装済み)
  
| テストケース | 事後確認項目 |
|--|--|
| 管理者は書籍情報を取得できる | ・ステータスコードが200であること <br> ・レスポンスに指定した書籍を表すJSONが入っていること <br> ・ FindBookUsecaseInteractorを適切な入力値を設定し、実行していること |
| 一般利用者は書籍情報を取得できる | ・ステータスコードが200であること <br> ・レスポンスに指定した書籍を表すJSONが入っていること <br> ・ FindBookUsecaseInteractorを適切な入力値を設定し、実行していること |
| bookIdが35桁以下の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・FindBookUsecaseInteractorを呼び出されていないこと |
| bookIdが37桁以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・FindBookUsecaseInteractorを呼び出されていないこと |
  
#####  書籍登録のテストケース
  
| テストケース | 事後確認項目 |
|--|--|
| 管理者は書籍を登録できる | ・ステータスコードが200であること <br> ・レスポンスに登録した書籍を表すJSONが入っていること <br> ・ CreateBookUsecaseInteractorを適切な入力値を設定し、実行していること |
| 一般ユーザーが書籍を登録した場合、ステータスコード403が返却される | ・レスポンスのステータスコードが403であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
| isbn13が数字13桁の場合、登録できる | ・ステータスコードが200であること <br> ・CreateBookUsecaseInteractorが呼び出されていること |
| isbn13がnullの場合、登録できる | ・ステータスコードが200であること <br> ・CreateBookUsecaseInteractorが呼び出されていること |
| titleが32桁の場合、登録できる | ・ステータスコードが200であること <br> ・CreateBookUsecaseInteractorが呼び出されていること |
| isbn13が数字以外の13桁の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
| isbn13が空文字の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
| isbn13が数字の12桁以下の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
| isbn13が数字の14桁以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
| titleが33桁以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・CreateBookUsecaseInteractorが呼び出されていないこと |
  
#####  書籍貸出のテストケース
  
| テストケース | 事後確認項目 |
|--|--|
| 管理者は書籍貸出を実施できる | ・ステータスコードが200であること <br> ・BorrowBookUsecaseInteractorに想定のBorrowBookInputDataが渡されていること <br> ・想定するJSONがレスポンスに格納されていること |
| 一般利用者は書籍貸出を実施できる | ・ステータスコードが200であること <br> ・BorrowBookUsecaseInteractorに想定のBorrowBookInputDataが渡されていること <br> ・想定するJSONがレスポンスに格納されていること |
| bookIdが35桁以下の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・BorrowBookUsecaseInteractorが呼び出されていないこと |
| bookIdが37桁以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・BorrowBookUsecaseInteractorが呼び出されていないこと |
  
#####  書籍返却のテストケース
  
| テストケース | 事後確認項目 |
|--|--|
| 管理者は書籍返却を実施できる | ・ステータスコードが200であること <br> ・GiveBackBookUsecaseInteractorに想定のGiveBackBookInputDataが渡されていること <br> ・想定するJSONがレスポンスに格納されていること |
| 一般利用者は書籍返却を実施できる | ・ステータスコードが200であること <br> ・GiveBackBookUsecaseInteractorに想定のGiveBackBookInputDataが渡されていること <br> ・想定するJSONがレスポンスに格納されていること |
| bookIdが35桁以下の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・GiveBackBookUsecaseInteractorが呼び出されていないこと |
| bookIdが37桁以上の場合、ステータスコード400が返却される | ・レスポンスのステータスコードが400であること <br> ・GiveBackBookUsecaseInteractorが呼び出されていないこと |
  
###  インフラストラクチャレイヤ
  
ORMの機能確認になるため、今回は省略する。  
一部ロジックがあるので、本来ならその部分はテストしたほうが良い。  
  