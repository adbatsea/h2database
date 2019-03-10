/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.engine;

import java.util.ArrayList;
import org.h2.table.Table;

/**
 * 数据库对象，比如表、索引或者用户。
 */
public interface DbObject {

    /**
     * 这个对象是一个表或者视图。
     */
    int TABLE_OR_VIEW = 0;

    /**
     * 这个对象是一个索引。
     */
    int INDEX = 1;

    /**
     * 这个对象是一个用户。
     */
    int USER = 2;

    /**
     * 这个对象是一个序列。
     */
    int SEQUENCE = 3;

    /**
     * 这个对象是一个触发器。
     */
    int TRIGGER = 4;

    /**
     * 这个对象是一个约束（check约束、唯一性约束或者
     * 引用约束）。
     */
    int CONSTRAINT = 5;

    /**
     * 这个对象是一个设置。
     */
    int SETTING = 6;

    /**
     * 这个对象是一个角色。
     */
    int ROLE = 7;

    /**
     * 这个对象是一个权限。
     */
    int RIGHT = 8;

    /**
     * 这个对象是一个函数的别名。
     */
    int FUNCTION_ALIAS = 9;

    /**
     * 这个对象是一个模式。
     */
    int SCHEMA = 10;

    /**
     * 这个对象是一个常量。
     */
    int CONSTANT = 11;

    /**
     * 这个对象是一个域。
     */
    int DOMAIN = 12;

    /**
     * 这个对象是一个注释。
     */
    int COMMENT = 13;

    /**
     * 这个对象是一个用户定义的聚集函数。
     */
    int AGGREGATE = 14;

    /**
     * 这个对象是一个同义词。
     */
    int SYNONYM = 15;

    /**
     * 获取这个对象的SQL名称（可能会加上引号）。
     *
     * @param alwaysQuote 所有标识符加上引号
     * @return SQL名称
     */
    String getSQL(boolean alwaysQuote);

    /**
     * 将这个对象的SQL名称（可能会加上引号）添加到指定的构建器后面。
     *
     * @param builder
     *            字符串构建器
     * @param alwaysQuote 所有标识符加上引号
     * @return 指定的字符串构建器
     */
    StringBuilder getSQL(StringBuilder builder, boolean alwaysQuote);

    /**
     * 获取依赖此对象的子对象的列表（对于表来说，它的子对象包括索引等）。
     *
     * @return 子对象的列表
     */
    ArrayList<DbObject> getChildren();

    /**
     * 获取数据库。
     *
     * @return 数据库
     */
    Database getDatabase();

    /**
     * 获取唯一的对象id。
     *
     * @return 对象id
     */
    int getId();

    /**
     * 获取名称。
     *
     * @return 名称
     */
    String getName();

    /**
     * 构建一条SQL语句来重建这个对象，或者用一个不同的名称或引用一个不同的表来创建这个对象
     *
     * @param table 新表
     * @param quotedName 带引号的名称
     * @return SQL语句
     */
    String getCreateSQLForCopy(Table table, String quotedName);

    /**
     * 构建这个对象最初的CREATE ... SQL语句。
     *
     * @return SQL语句
     */
    String getCreateSQL();

    /**
     * 构建这个对象的一个DROP ... SQL语句。
     *
     * @return SQL语句
     */
    String getDropSQL();

    /**
     * 获取对象类型。
     *
     * @return 对象类型
     */
    int getType();

    /**
     * 删除这个对象所有依赖它的子对象及资源。
     *
     * @param session 会话
     */
    void removeChildrenAndResources(Session session);

    /**
     * 检查是否允许重命名。如果允许则不进行任何操作。
     */
    void checkRename();

    /**
     * 重命名对象。
     *
     * @param newName 新的名称
     */
    void rename(String newName);

    /**
     * 检查这个对象是否是临时对象（比如，一个临时表）。
     *
     * @return 如果是临时对象则返回true
     */
    boolean isTemporary();

    /**
     * 设置这个对象是否为临时对象。
     *
     * @param temporary 新的值
     */
    void setTemporary(boolean temporary);

    /**
     * 修改这个对象的注释。
     *
     * @param comment 新的注释，如果没有注释则为null
     */
    void setComment(String comment);

    /**
     * 获取这个对象当前的注释。
     *
     * @return 注释，如果未设置则为null
     */
    String getComment();

}
