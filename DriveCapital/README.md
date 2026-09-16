# Drive Capital Code Challenge

## 📋 Prerequisites
* **Java 8** or higher

## 🚀 How to Run
1. **Navigate to the source directory:**
   ```bash
   cd src/main/java
   ```
2. **Prepare your test data:** Paste your test data into a file named `input.txt` inside this directory.
3. **Compile the code:**
   ```bash
   javac org/example/Main.java
   ```
4. **Execute the application:**
   ```bash
   java org.example.Main input.txt
   ```

---

## 🛠️ Solution Explained
This solution mimics a relational database using Java classes as "models," closely following an **MVC architecture**.

The included `database` package handles building and seeding the data:
* Tables are exposed as arrays of model objects (`Partner`, `Company`, `Employee`, and `Contact`).
* Each index in the array (one Java object) represents a single row from that table.

### Simulated SQL Logic
The `Main` class runs methods that essentially mimic the following SQL command:

```sql
WITH RankedPartnerStrengths AS (
    SELECT
        e.company_name,
        co.partner_name,
        COUNT(*) AS strength,
        ROW_NUMBER() OVER (
            PARTITION BY e.company_name
            ORDER BY COUNT(*) DESC, co.partner_name ASC
        ) AS rn
    FROM
    Contacts co
    JOIN
    Employees e ON co.employee_name = e.name
    GROUP BY
    e.company_name, co.partner_name
)
SELECT
    c.name AS company_name,
    COALESCE(ps.partner_name, 'No current relationship') AS partner_name,
    ps.strength
FROM
    Companies c
LEFT JOIN
    RankedPartnerStrengths ps ON c.name = ps.company_name AND ps.rn = 1
ORDER BY
    c.name ASC;
```

---

## 🧠 Reflection & Process
At first, I tried to approach this as a typical Data Structures and Algorithms (DSA) problem out of sheer familiarity with technical interview questions. Upon diving deeper, I realized a traditional DSA approach wouldn't fit cleanly. Instead, I shifted to architecting a mock database system with model classes. Once that foundation was in place, building out the data relationships was highly intuitive.

### AI Assistance
I leveraged AI (Gemini) during development to help with:
* Learning how to handle and read command-line arguments in the `main` method.
* Translating my Java solution logic into the corresponding SQL query listed above.
* Formatting and beautifying this `README.md` file.

---

## ⏱️ Complexity Analysis
* **Time Complexity:** **$O(N \log N)$** — Driven by iterating through the input elements linearly ($N$) and inserting company names into a sorted `TreeMap`, which carries an $O(\log N)$ overhead.
* **Space Complexity:** **$O(N)$** — Tracking data structures scales linearly relative to the total volume of processed input records.

---

## ⚠️ Assumptions and Edge Cases

* **Tie-Breaker Limitation:** This program assumes there is a single strongest partner connection for each company. If two or more partners tie for the highest number of total connections, only one partner will be returned in the final output.
* **Proposed Solution:** You can remedy this behavior by refactoring the variable tracking the single strongest connection index from a primitive `int` to a collection type, such as a `List<Integer>` or an `int[]`.
