/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.engine;

import java.util.ArrayList;
import org.h2.command.Parser;
import org.h2.message.DbException;
import org.h2.message.Trace;

/**
 * 所有数据库对象的基类。
 */
public abstract class DbObjectBase implements DbObject {

    /**
     * 数据库。
     */
    protected Database database;

    /**
     * trace模块。
     */
    protected Trace trace;

    /**
     * 注释（如果设置了的话）。
     */
    protected String comment;

    private int id;
    private String objectName;
    private long modificationId;
    private boolean temporary;

    /**
     * 初始化这个对象的一些属性。
     *
     * @param db 数据库
     * @param objectId 对象id
     * @param name 名称
     * @param traceModuleId 跟踪模块id
     */
    protected DbObjectBase(Database db, int objectId, String name,
            int traceModuleId) {
        this.database = db;
        this.trace = db.getTrace(traceModuleId);
        this.id = objectId;
        this.objectName = name;
        this.modificationId = db.getModificationMetaId();
    }

    /**
     * 构建可以重新生成这个对象的SQL语句。
     *
     * @return SQL语句
     */
    @Override
    public abstract String getCreateSQL();

    /**
     * 构建可以删除这个对象的SQL语句。
     *
     * @return SQL语句
     */
    @Override
    public abstract String getDropSQL();

    /**
     * 删除所有依赖这个对象的对象，并且释放这个对象的所有资源（文件、文件中的数据块）。
     *
     * @param session 会话
     */
    @Override
    public abstract void removeChildrenAndResources(Session session);

    /**
     * 检查这个对象是否可以重命名。系统对象不能重命名。
     */
    @Override
    public abstract void checkRename();

    /**
     * 告诉这个对象它被修改了。
     */
    public void setModified() {
        this.modificationId = database == null ?
                -1 : database.getNextModificationMetaId();
    }

    public long getModificationId() {
        return modificationId;
    }

    protected void setObjectName(String name) {
        objectName = name;
    }

    @Override
    public String getSQL(boolean alwaysQuote) {
        return Parser.quoteIdentifier(objectName, alwaysQuote);
    }

    @Override
    public StringBuilder getSQL(StringBuilder builder, boolean alwaysQuote) {
        return Parser.quoteIdentifier(builder, objectName, alwaysQuote);
    }

    @Override
    public ArrayList<DbObject> getChildren() {
        return null;
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return objectName;
    }

    /**
     * 将主要的属性设置为null，以确保这个对象不再使用。
     */
    protected void invalidate() {
        if (id == -1) {
            throw DbException.throwInternalError();
        }
        setModified();
        id = -1;
        database = null;
        trace = null;
        objectName = null;
    }

    public final boolean isValid() {
        return id != -1;
    }

    @Override
    public void rename(String newName) {
        checkRename();
        objectName = newName;
        setModified();
    }

    @Override
    public boolean isTemporary() {
        return temporary;
    }

    @Override
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return objectName + ":" + id + ":" + super.toString();
    }

}
