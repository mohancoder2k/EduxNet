### 1. 📤 Send a Connection Request

- **URL:** `POST /api/chat/connection/send`
- **Headers:**
  - `Authorization: Bearer <JWT Token>`
  - `Content-Type: application/json`
- **Request Body:**
```json
{
  "receiverUsername": "chandrajcs@gmail.com"
}

---

### ✅ Result in GitHub Markdown Preview:

It will render like this in GitHub:

---

#### Response:
- ✅ `"Request sent successfully"`  
- ⚠️ `"Request already exists or already connected"`  
- ❌ `"Cannot send request to yourself"`

---

So you're using:
- Emoji ✅ ❌ ⚠️ (plain text)
- Response strings inside backticks: `` `"response"` ``

This is **fully GitHub-compatible** Markdown and gives you exactly the style you showed in your screenshot.

Let me know if you'd like a full polished `README.md` with all sections included.
2. 📥 View Pending Connection Requests
URL: GET /api/chat/connection/pending

Headers:

Authorization: Bearer <JWT Token>

Response Example:

json
Copy
Edit
[
  {
    "id": 2,
    "senderUsername": "mohansarady@gmail.com",
    "receiverUsername": "your_email@example.com",
    "status": "PENDING"
  }
]
Description:
Shows all incoming connection requests received by the current user (pending approval).

3. ✅ Respond to a Connection Request
URL:
POST /api/chat/connection/respond?fromUsername=mohansarady@gmail.com&response=ACCEPTED

Headers:

Authorization: Bearer <JWT Token>

Query Parameters:

fromUsername: Email of the sender

response: Either ACCEPTED or REJECTED

Response:

✅ "Request accepted"

❌ "Request rejected"

⚠️ "Request not found"

⚠️ "Invalid response"

4. 🔍 Check Connection Status Between Users
URL:
GET /api/chat/connection/status?viewedUserEmail=mohansarady@gmail.com

Headers:

Authorization: Bearer <JWT Token>

Query Parameters:

viewedUserEmail: Email of the user whose profile is being viewed

Response:

✅ "CONNECTED"

❌ "NOT_CONNECTED"

Description:
Returns whether the current logged-in user is connected with the profile being viewed.

📌 Connection Status Types
PENDING: Request sent but not yet accepted

ACCEPTED: Connection established

REJECTED: Request was declined
