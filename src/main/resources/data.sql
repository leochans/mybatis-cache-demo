-- 初始化一条测试数据（H2 内存库重启会清空，无需判重）
INSERT INTO product (id, name, category_id) VALUES (1, 'iPhone 15', 100);
