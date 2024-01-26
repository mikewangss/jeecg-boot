-- 注意：该页面对应的前台目录为views/settlement文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2024010305349380390', NULL, '项目', '/settlement/applyProjectList', 'settlement/ApplyProjectList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380391', '2024010305349380390', '添加项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380392', '2024010305349380390', '编辑项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380393', '2024010305349380390', '删除项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380394', '2024010305349380390', '批量删除项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380395', '2024010305349380390', '导出excel_项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2024010305349380396', '2024010305349380390', '导入excel_项目', NULL, NULL, 0, NULL, NULL, 2, 'settlement:apply_project:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2024-01-03 17:34:39', NULL, NULL, 0, 0, '1', 0);