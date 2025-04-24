# user_ws

基本的使用者帳號管理功能，包括註冊、登入、查詢、更新與刪除。

---
- Java 17
- Spring Boot
- Maven
- RESTful API 設計
- AWS EC2（部署環境）

API 介面說明：
伺服器網址（EC2）：
http://ec2-56-155-36-104.ap-northeast-3.compute.amazonaws.com:8080

建立User帳號 - Register
POST /user-ws/users

Request Body（JSON）
{
  "firstName": "Yolanda",
  "lastName": "Lin",
  "email": "yolanda@example.com",
  "password": "12345678"
}

User登入 - Login
POST /user-app-ws/users/login

Request Body（JSON）
{
  "email": "yolanda@example.com",
  "password": "12345678"
}

刪除使用者 - Delete
DELETE /user-ws/users/{userId}

Header
Authorization: {token}

更新User資訊 - Update
PUT /user-ws/users/{userId}

Header
Authorization: {token}

Request Body（範例）
{
  "firstName": "NewName",
  "lastName": "NewLastName"
}

查詢使用者列表 - Get Users
GET /user-ws/users?page=1&limit=3
頁數及筆數自訂

Header
Authorization: {token}
