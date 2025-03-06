# practice-spring-boot-feb-2025

## 🔧 Fix for "null value in column 'id' violates not-null constraint" Error

If you encounter the following error while running your Spring Boot application:  


It means that the `id` column in your PostgreSQL `users` table is not set to auto-generate values.

### 🛠 Solution
Run the following SQL command in your PostgreSQL database to make the `id` column auto-increment:

```sql
ALTER TABLE users ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;
