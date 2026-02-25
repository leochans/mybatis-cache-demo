-- 建表脚本，启动时自动执行
CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100),
    category_id BIGINT
);
