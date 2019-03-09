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
     * Get the list of dependent children (for tables, this includes indexes and
     * so on).
     *
     * @return the list of children
     */
    ArrayList<DbObject> getChildren();

    /**
     * Get the database.
     *
     * @return the database
     */
    Database getDatabase();

    /**
     * Get the unique object id.
     *
     * @return the object id
     */
    int getId();

    /**
     * Get the name.
     *
     * @return the name
     */
    String getName();

    /**
     * Build a SQL statement to re-create the object, or to create a copy of the
     * object with a different name or referencing a different table
     *
     * @param table the new table
     * @param quotedName the quoted name
     * @return the SQL statement
     */
    String getCreateSQLForCopy(Table table, String quotedName);

    /**
     * Construct the original CREATE ... SQL statement for this object.
     *
     * @return the SQL statement
     */
    String getCreateSQL();

    /**
     * Construct a DROP ... SQL statement for this object.
     *
     * @return the SQL statement
     */
    String getDropSQL();

    /**
     * Get the object type.
     *
     * @return the object type
     */
    int getType();

    /**
     * Delete all dependent children objects and resources of this object.
     *
     * @param session the session
     */
    void removeChildrenAndResources(Session session);

    /**
     * Check if renaming is allowed. Does nothing when allowed.
     */
    void checkRename();

    /**
     * Rename the object.
     *
     * @param newName the new name
     */
    void rename(String newName);

    /**
     * Check if this object is temporary (for example, a temporary table).
     *
     * @return true if is temporary
     */
    boolean isTemporary();

    /**
     * Tell this object that it is temporary or not.
     *
     * @param temporary the new value
     */
    void setTemporary(boolean temporary);

    /**
     * Change the comment of this object.
     *
     * @param comment the new comment, or null for no comment
     */
    void setComment(String comment);

    /**
     * Get the current comment of this object.
     *
     * @return the comment, or null if not set
     */
    String getComment();

}
