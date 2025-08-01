# 일정 관리 API

일정을 등록하고, 각 일정마다 댓글을 작성할 수 있는 API 입니다.

## 📅 개발 기간

2025.07.30 ~ 2025.08.01

## 🔑 ERD 다이어그램

<img width="724" height="180" alt="sparta-schedule-management-ERD" src="https://github.com/user-attachments/assets/555779e0-2865-4980-8249-0c1f71484223" />

## 📜 API 명세서

### ✅ 일정 생성

#### 🔹 Request

##### JSON

| 이름 | 타입 | 설명 | 조건 | 필수 |
| :-- | :-- | :-- | :-- | :-: |
| title | String | 일정 제목 | 최대 30자 | ⭕ |
| contents | String | 일정 내용 | 최대 200자 | ⭕ |
| name | String | 작성자명 |  | ⭕ |
| password | String | 비밀번호 |  | ⭕ |

```http
POST /schedules HTTP/1.1
Host: localhost:8080
Content-Length: 107

{
    "title": "홍길동의 일정 제목",
    "contents": "홍길동의 일정 내용",
    "name": "홍길동",
    "password": "1234"
}
```

#### 🔸 Response

`201 CREATED`
```http
{
    "id": 1,
    "title": "홍길동의 일정 제목",
    "contents": "홍길동의 일정 내용",
    "name": "홍길동",
    "createdAt": "2025-08-01T21:05:32.8382114",
    "modifiedAt": "2025-08-01T21:05:32.8382114"
}
```

### ✅ 일정 목록 조회

#### 🔹 Request

```http
GET /schedules HTTP/1.1
Host: localhost:8080
```

#### 🔸 Response

`200 OK`
```http
[
    {
        "id": 3,
        "title": "장길산의 일정 제목",
        "contents": "장길산의 일정 내용",
        "name": "장길산",
        "createdAt": "2025-08-01T21:05:32.838211",
        "modifiedAt": "2025-08-01T21:05:32.838211"
    },
    {
        "id": 2,
        "title": "임꺽정의 일정 제목",
        "contents": "임꺽정의 일정 내용",
        "name": "임꺽정",
        "createdAt": "2025-08-01T21:05:25.565379",
        "modifiedAt": "2025-08-01T21:05:25.565379"
    },
    {
        "id": 1,
        "title": "홍길동의 일정 제목",
        "contents": "홍길동의 일정 내용",
        "name": "홍길동",
        "createdAt": "2025-08-01T21:05:16.787493",
        "modifiedAt": "2025-08-01T21:05:16.787493"
    }
]
```

#### 🔹 Request

##### Query Parameter

| 이름 | 타입 | 설명 | 조건 | 필수 |
| :-- | :-- | :-- | :-- | :-: |
| name | String | 해당 일정의 작성자명으로 검색 |  | ❌ |

```http
GET /schedules?name=홍길 HTTP/1.1
Host: localhost:8080
```

#### 🔸 Response

`200 OK`
```http
[
    {
        "id": 1,
        "title": "홍길동의 일정 제목",
        "contents": "홍길동의 일정 내용",
        "name": "홍길동",
        "createdAt": "2025-08-01T21:05:16.787493",
        "modifiedAt": "2025-08-01T21:05:16.787493"
    }
]
```

### ✅ 일정 단건 조회

#### 🔹 Request

```http
GET /schedules/1 HTTP/1.1
Host: localhost:8080
```

#### 🔸 Response

`200 OK`
```http
{
    "id": 1,
    "title": "홍길동의 일정 제목",
    "contents": "홍길동의 일정 내용",
    "name": "홍길동",
    "comments": [
        {
            "id": 1,
            "scheduleId": 1,
            "contents": "댓글 내용입니다.",
            "name": "임꺽정",
            "createdAt": "2025-08-01T21:45:58.515344",
            "modifiedAt": "2025-08-01T21:45:58.515344"
        },
        {
            "id": 2,
            "scheduleId": 1,
            "contents": "안녕하세요!",
            "name": "장길산",
            "createdAt": "2025-08-01T21:46:14.547383",
            "modifiedAt": "2025-08-01T21:46:14.547383"
        }
    ],
    "createdAt": "2025-08-01T21:05:16.787493",
    "modifiedAt": "2025-08-01T21:05:16.787493"
}
```

### ✅ 일정 수정

#### 🔹 Request

##### JSON

| 이름 | 타입 | 설명 | 조건 | 필수 |
| :-- | :-- | :-- | :-- | :-: |
| title | String | 일정 제목 | 최대 30자 | ❌ |
| name | String | 작성자명 |  | ❌ |
| password | String | 비밀번호 |  | ⭕ |

```http
PATCH /schedules/1 HTTP/1.1
Host: localhost:8080
Content-Length: 73

{
    "title": "제목 수정",
    "name": "이름 수정",
    "password": "1234"
}
```

#### 🔸 Response

`200 OK`
```http
{
    "id": 1,
    "title": "제목 수정",
    "contents": "홍길동의 일정 내용",
    "name": "이름 수정",
    "createdAt": "2025-08-01T21:05:16.787493",
    "modifiedAt": "2025-08-01T21:05:16.787493"
}
```

### ✅ 일정 삭제

#### 🔹 Request

##### JSON

| 이름 | 타입 | 설명 | 조건 |  필수 |
| :-- | :-- | :-- | :-- | :-: |
| password | String | 비밀번호 |  | ⭕ |

```http
DELETE /schedules/3 HTTP/1.1
Host: localhost:8080
Content-Length: 28

{
    "password": "1234"
}
```

#### 🔸 Response

`200 OK`
```plaintext
No response body
This request doesn't return any response body.
```

### ✅ 댓글 생성

#### 🔹 Request

##### JSON

| 이름 | 타입 | 설명 | 조건 | 필수 |
| :-- | :-- | :-- | :-- | :-: |
| contents | String | 댓글 내용 | 최대 100자 | ⭕ |
| name | String | 작성자명 |  | ⭕ |
| password | String | 비밀번호 |  | ⭕ |

```http
POST /schedules/1/comments HTTP/1.1
Host: localhost:8080
Content-Length: 75

{
    "contents": "안녕하세요!",
    "name": "장길산",
    "password": "zxcv"
}
```

#### 🔸 Response

`201 CREATED`
```http
{
    "id": 2,
    "scheduleId": 1,
    "contents": "안녕하세요!",
    "name": "장길산",
    "createdAt": "2025-08-01T21:46:14.5473827",
    "modifiedAt": "2025-08-01T21:46:14.5473827"
}
```












