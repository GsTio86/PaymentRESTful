# PaymentRESTful

## 概述
PaymentRESTful 是一個基於 Spring Boot 的專案，用於處理遊戲中的繳費流程。該專案使用 Java 17 並集成了多個庫，包括 Lombok、spring-dotenv 和 Discord Webhook。前端驗證使用 Google reCAPTCHA。

## 使用的技術與庫
- **[Spring Boot](https://spring.io/projects/spring-boot)**：作為主要框架，用於構建 RESTful API。
- **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)**：專案使用的 Java 版本。
- **[Lombok](https://projectlombok.org/)**：用於簡化 Java 代碼，特別是消除樣板代碼。
- **[spring-dotenv](https://github.com/paulschwarz/spring-dotenv)**：用於管理Spring Boot環境變量。
- **[Discord Webhook](https://discord.com/developers/docs/resources/webhook)**：用於將訊息發送到 Discord 頻道。
- **[Google reCAPTCHA](https://www.google.com/recaptcha/about/)**：用於前端驗證，防止機器人操作。

## API 端點
### 1. 檢查遊戲ID
- **端點**：`/player/check`
- **方法**：POST
- **參數**：`username` (String) - 用於檢查是否有此遊戲ID
- **描述**：此端點用於檢查給定的 `username` 是否存在，如果存在，將回傳 `uuid`、`rawid` 和 `username`。

### 2. 建立訂單
- **端點**：`/payment/createOrder`
- **方法**：POST
- - **參數**：
  - `type` (String) - 付款方式
  - `username` (String) - 遊戲id
  - `rawid` (String) - 遊戲uuid(無-版)
  - `amount` (Integer) - 付款金額
  - `token` (String) - 驗證碼金鑰
- **描述**：此端點用於建立一個新的訂單，並回傳一個 HTML 頁面，該頁面會自動跳轉至金流付款頁面。

### 3. 金流付款完成回調
- **歐買尬 FunPoint**
  - **端點**：`/fallback/payment_funpoint`
  - **方法**：POST
  - **參數**：請詳看歐買尬API文件
  - **描述**：此端點接收金流方付款完成後的回調，並處理付款結果，將結果保存到資料庫並通過 Discord Webhook 發送通知。
- **速買配 Smse**
  - **端點**：`/fallback/payment_smse`
  - **方法**：POST
  - **參數**：請詳看速買配API文件
  - **描述**：此端點接收金流方付款完成後的回調，並處理付款結果，將結果保存到資料庫並通過 Discord Webhook 發送通知。

## 環境配置
使用 `spring-dotenv` 庫來管理環境變量。請在專案根目錄下創建 `.env` 文件，並在其中配置必要的環境變量。

## 設置與運行
1. 克隆此倉庫到本地：
   ```sh
   git clone https://github.com/GsTio86/PaymentRESTful.git
